package com.deu.wiki;

import java.sql.*;
import java.util.Vector;

public class UserDAO {
	// --------------------------------------------------------
	// �̱��� ������ ������ ������ �ν��Ͻ� ����
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
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://mydb.cihjn38frpag.ap-northeast-2.rds.amazonaws.com:3305/team4";
		String user = "mydb";
		String password = "dkagh1212";

		try {
			Class.forName(driver);
//			System.out.println("����̹� �ε� ����!");

			con = DriverManager.getConnection(url, user, password);
//			System.out.println("DB ���� ����!");
		} catch (ClassNotFoundException e) {
			System.out.println("����̹� �ε� ����! - " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("DB ���� ����! - " + e.getMessage());
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

	// ȸ�� �߰�
	public int insert(UserDTO dto) {
		connectDb();

		int result = 0; // ȸ�� �߰� ���� ����(0 : ����, 1 : ����)

		try {
			// DTO ��ü�� ����� �����͸� DB �� INSERT
			String sql = "INSERT INTO user VALUES (null,?,?)";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getpNum());
			pstmt.setDouble(2, dto.getPoint());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("SQL ���� ����! - " + e.getMessage());
		} finally {
			closeDb();
		}

		return result;
	}

	// ȸ�� ����
	public int update(UserDTO dto) {
		connectDb();

		int result = 0; // ȸ�� ���� ���� ����(0 : ����, 1 : ����)

		try {
			// ���ڵ� ����
			String sql = "update user set " + "pNum=?, " + "point=?" + " where idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getpNum());
			pstmt.setInt(2, dto.getPoint());
			pstmt.setInt(3, dto.getIdx());
			pstmt.executeUpdate();
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("SQL ���� ����! - " + e.getMessage());
		} finally {
			closeDb();
		}

		return result;
	}

	// ȸ�� ����
	public int delete(int idx) {
		connectDb();

		int result = 0; // ȸ�� ���� ���� ����(0 : ����, 1 : ����)

		try {
			// ���޹��� ��ȣ(idx)�� ����Ͽ� ���ڵ� ����
			String sql = "DELETE FROM user WHERE idx=?";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("SQL ���� ����! - " + e.getMessage());
		} finally {
			closeDb();
		}

		return result;
	}

	// ȸ�� ���� �˻�, ����Ʈ ��� �� ����
	public int selectOne(String pNum, int tempSum, int op) { // op�� 0�̸� ����Ʈ ����x,��� x �׳� ������, 1�̸� ����Ʈ ���, 2�̸� ����Ʈ ����
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
							return 1; //����Ʈ �������� ���� ���� ���
						user.setPoint(temp);
						UserDAO dao = UserDAO.getInstance();
						dao.update(user);
						return 2; //����Ʈ ��� �Ϸ�
					}

					else if (op == 2) {
						int temp = user.getPoint();
						temp += (tempSum / 100); //����Ʈ ������ ��갡���� 50% ����
						user.setPoint(temp);
						UserDAO dao = UserDAO.getInstance();
						dao.update(user);
						return 3; //����Ʈ ���� �Ϸ�
					}

				} // ������� ���� �������� ��ȭ��ȣ�� �Է¹��� ��ȭ��ȣ�� ������ üũ
			}
			if (op == 1) // ���⼭���ʹ� DB�� �Է¹��� ��ȣ�� ���� ���
				return 4; // ���ʿ� ���� �����͵� ��� ����Ʈ ��� �������� ���� ���� ���
			if (op == 2) {
				user.setIdx(0);
				user.setpNum(pNum);
				user.setPoint((tempSum /100));
				UserDAO dao = UserDAO.getInstance();
				dao.insert(user);
				return 5;   // ȸ�� ��ϰ� ���ÿ� ����Ʈ ����
			}
		} catch (SQLException e) {
			System.out.println("SQL ���� ����! - " + e.getMessage());
		} finally {
			closeDb();
		}
		return 0;
	}

	// ȸ����� ��ȸ
	public Vector<Vector> select() {
		connectDb();

		try {
			String sql = "SELECT * FROM user";

			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			Vector<Vector> data = new Vector<>(); // ��ü ���ڵ带 ������ Vector ��ü

			while (rs.next()) {
				Vector rowData = new Vector<>(); // 1�� ���ڵ带 ������ Vector ��ü

				rowData.add(rs.getInt("idx"));
				rowData.add(rs.getString("pNum"));
				rowData.add(rs.getString("point"));

				data.add(rowData);
			}

			return data; // ��ȸ ���� �� ����� Vector ��ü ����

		} catch (SQLException e) {
			System.out.println("SQL ���� ����! - " + e.getMessage());
		} finally {
			closeDb();
		}

		return null;
	}
}