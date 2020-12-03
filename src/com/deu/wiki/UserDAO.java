package com.deu.wiki;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class UserDAO {
	// --------------------------------------------------------
	// 싱글톤 디자인 패턴을 적용한 인스턴스 리턴
	private static UserDAO instance = new UserDAO();

	public UserDAO() {
	}

	public static UserDAO getInstance() {
		return instance;
	}
	// --------------------------------------------------------

	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private void connectDb() {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "deu_wiki";
		String password = "1234";

		try {
			Class.forName(driver);
//			System.out.println("드라이버 로드 성공!");

			con = DriverManager.getConnection(url, user, password);
//			System.out.println("DB 접속 성공!");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패! - " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("DB 접속 실패! - " + e.getMessage());
		}
	}

	private void closeDb() {
		if (rs != null)
			try {
				rs.close();
			} catch (Exception e) {
			}
		if (pstmt != null)
			try {
				pstmt.close();
			} catch (Exception e) {
			}
		if (con != null)
			try {
				con.close();
			} catch (Exception e) {
			}
	}

	// 회원 추가
	public int insert(UserDTO dto) {
		connectDb();

		int result = 0; // 회원 추가 성공 여부(0 : 실패, 1 : 리턴)

		try {
			// DTO 객체에 저장된 데이터를 DB 에 INSERT
			String sql = "INSERT INTO user VALUES (null,?,?)";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getpNum());
			pstmt.setDouble(2, dto.getPoint());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("SQL 구문 오류! - " + e.getMessage());
		} finally {
			closeDb();
		}

		return result;
	}

	// 회원 수정
	public int update(UserDTO dto) {
		connectDb();

		int result = 0; // 회원 수정 성공 여부(0 : 실패, 1 : 리턴)

		try {
			// 레코드 수정
			String sql = "update user set " + "pNum=?, " + "point=?" + " where idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getpNum());
			pstmt.setInt(2, dto.getPoint());
			pstmt.setInt(3, dto.getIdx());
			pstmt.executeUpdate();
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("SQL 구문 오류! - " + e.getMessage());
		} finally {
			closeDb();
		}

		return result;
	}

	// 신고 댓글 삭제
	public int delete(int idx) {
		connectDb();

		int result = 0; // 신고 댓글 삭제 성공 여부(0 : 실패, 1 : 리턴)

		try {
			// 전달받은 번호(idx)를 사용하여 레코드 삭제
//			String sql = "DELETE FROM total WHERE idx=?";

			String sql = "update reviews set " + "is_stop=?, " + " where idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "0");
			pstmt.executeUpdate();
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("SQL 구문 오류! - " + e.getMessage());
		} finally {
			closeDb();
		}
		return result;
	}

	// 회원 유무 검색, 포인트 사용 및 적립
	public int selectOne(String pNum, int tempSum, int op) { // op가 0이면 포인트 적립x,사용 x 그냥 결제만, 1이면 포인트 사용, 2이면 포인트 적립
		connectDb();
		String sql = "SELECT * FROM user";
		UserDTO user = new UserDTO();

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (rs.getString("pNum").equals(pNum)) {
					user.setIdx(rs.getInt("idx"));
					user.setpNum(rs.getString("pNum"));
					user.setPoint(rs.getInt("point"));
					if (op == 1) {
						int temp = user.getPoint();
						temp -= tempSum;
						if (temp < 0)
							return 1; //포인트 부족으로 인한 결제 취소
						user.setPoint(temp);
						UserDAO dao = UserDAO.getInstance();
						dao.update(user);
						return 2; //포인트 사용 완료
					}

					else if (op == 2) {
						int temp = user.getPoint();
						temp += (tempSum / 100); //포인트 적립은 계산가격의 50% 적립
						user.setPoint(temp);
						UserDAO dao = UserDAO.getInstance();
						dao.update(user);
						return 3; //포인트 적립 완료
					}

				} // 여기까지 꺼낸 데이터의 전화번호와 입력받은 전화번호가 같으면 체크
			}
			if (op == 1) // 여기서부터는 DB에 입력받은 번호가 없는 경우
				return 4; // 애초에 기존 데이터도 없어서 포인트 사용 부족으로 인한 결제 취소
			if (op == 2) {
				user.setIdx(0);
				user.setpNum(pNum);
				user.setPoint((tempSum /100));
				UserDAO dao = UserDAO.getInstance();
				dao.insert(user);
				return 5;   // 회원 등록과 동시에 포인트 적립
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류! - " + e.getMessage());
		} finally {
			closeDb();
		}
		return 0;
	}

	// 신고 댓글목록 조회
	public Vector<Vector> select() {
		connectDb();

		try {
			String sql = "SELECT * FROM reviews where is_stop='1'";

			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			Vector<Vector> data = new Vector<>(); // 전체 레코드를 저장할 Vector 객체

			while (rs.next()) {
				Vector rowData = new Vector<>(); // 1개 레코드를 저장할 Vector 객체

				rowData.add(rs.getInt("keyword"));
				rowData.add(rs.getString("content"));
//				rowData.add(rs.getString("point"));

				data.add(rowData);
			}

			return data; // 조회 성공 시 저장된 Vector 객체 리턴

		} catch (SQLException e) {
			System.out.println("SQL 구문 오류! - " + e.getMessage());
		} finally {
			closeDb();
		}

		return null;
	}
}