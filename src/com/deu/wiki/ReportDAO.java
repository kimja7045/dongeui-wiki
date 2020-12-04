package com.deu.wiki;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Vector;

public class ReportDAO {
	// --------------------------------------------------------
	// 싱글톤 디자인 패턴을 적용한 인스턴스 리턴
	private static ReportDAO instance = new ReportDAO();
	
	private ReportDAO() {}
	
	public static ReportDAO getInstance() {
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
		if(rs != null) try { rs.close(); } catch(Exception e) {}
		if(pstmt != null) try { pstmt.close(); } catch(Exception e) {}
		if(con != null) try { con.close(); } catch(Exception e) {} 
	}
	
	// 게시글 신고 추가
	public int insert(ReportDTO dto) {
		connectDb();

        int result = 0; // 게시물 추가 성공 여부(0 : 실패, 1 : 리턴)
        if(dto.getUser_id().equals("")) {
        	return -2;
        }
        try {
        	String pk = "";
			CallableStatement cstmt = con.prepareCall( "{call Is_write_report(?,?,?)}" );
        	cstmt.setString(1, dto.getKeyword());
        	cstmt.setString(2, dto.getUser_id());
        	cstmt.registerOutParameter(3, Types.VARCHAR);
        	cstmt.execute();
        	
        	if(cstmt.getString(3).equals("False")) {
	        	String sql_pk = "select MAX(To_NUMBER(id)) as pk from reports";
	            Statement stmt_pk = con.createStatement();
	            ResultSet rs_pk = stmt_pk.executeQuery(sql_pk);
	            if(rs_pk.next()) {
	            	pk = rs_pk.getString("pk");
	            	pk = Integer.toString(Integer.parseInt(pk)+1);
	            }
	            else {
	            	pk = "1";
	            }
	            String sql = "INSERT INTO reports VALUES (?,?,?,null,?)";

    			pstmt = con.prepareStatement(sql);
    			pstmt.setString(1, pk);
    			pstmt.setString(2, dto.getUser_id());
    			pstmt.setString(3, dto.getKeyword());
    			pstmt.setString(4, dto.getComent());
    			
    			result = pstmt.executeUpdate();
        	} else {
        		return -1;
        	}
        } catch (SQLException e) {
            System.out.println("SQL 구문 오류! - " + e.getMessage());
        } finally {
            closeDb();
        }

        return result;
	}
	
	// 댓글 신고 추가
	public int insert_review(ReportDTO dto) {
		connectDb();

        int result = 0; // 게시물 추가 성공 여부(0 : 실패, 1 : 리턴)
        if(dto.getUser_id().equals("")) {
        	return -2;
        }
        try {
        	String pk = "";
			CallableStatement cstmt = con.prepareCall( "{call Is_write_report_review(?,?,?,?)}" );
        	cstmt.setString(1, dto.getKeyword());
        	cstmt.setString(2, dto.getUser_id());
        	cstmt.setString(3, dto.getReview_num());
        	cstmt.registerOutParameter(4, Types.VARCHAR);
        	cstmt.execute();
        	
        	if(cstmt.getString(4).equals("False")) {
	        	String sql_pk = "select MAX(To_NUMBER(id)) as pk from reports";
	            Statement stmt_pk = con.createStatement();
	            ResultSet rs_pk = stmt_pk.executeQuery(sql_pk);
	            if(rs_pk.next()) {
	            	pk = rs_pk.getString("pk");
	            	pk = Integer.toString(Integer.parseInt(pk)+1);
	            }
	            else {
	            	pk = "1";
	            }
	            String sql = "INSERT INTO reports VALUES (?,?,?,?,?)";

    			pstmt = con.prepareStatement(sql);
    			pstmt.setString(1, pk);
    			pstmt.setString(2, dto.getUser_id());
    			pstmt.setString(3, dto.getKeyword());
//    			String sql2 = "select content from reviews where review_num = " + dto.getReview_num();
//                Statement stmt2 = con.createStatement();
//                ResultSet rs2 = stmt2.executeQuery(sql2);
//                if(rs2.next()) {
//        			pstmt.setString(4, rs2.getString("content"));
//                }
//                else {
//        			pstmt.setString(4, "입력 오류");
//                }
    			pstmt.setString(4, dto.getReview_num());
    			pstmt.setString(5, dto.getComent());
    			
    			result = pstmt.executeUpdate();
        	} else {
        		return -1;
        	}
        } catch (SQLException e) {
            System.out.println("SQL 구문 오류! - " + e.getMessage());
        } finally {
            closeDb();
        }

        return result;
	}
	// 매출 수정
//	public int update(ReportDTO dto) {
//		connectDb();
//
//		int result = 0; // 매출 수정 성공 여부(0 : 실패, 1 : 리턴)
//
//		try {
//			// 레코드 수정
//			String sql = "update total set " + "date=?, " + "sum=?" + " where idx=?";
//			pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, dto.getDate());
//			pstmt.setInt(2, dto.getSum());
//			pstmt.setInt(3, dto.getIdx());
//			pstmt.executeUpdate();
//			result = pstmt.executeUpdate();
//
//		} catch (SQLException e) {
//			System.out.println("SQL 구문 오류! - " + e.getMessage());
//		} finally {
//			closeDb();
//		}
//
//		return result;
//	}
	
	// 신고 게시글 삭제
	public int delete(String idx) {
		connectDb();

		int result = 0;

		try {
			String sql = "DELETE FROM reports WHERE id = ?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, idx);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류" + e.getMessage());
		} finally {
			closeDb();
		}
		return result;
	}
	
	// 신고 게시글목록 조회
	public Vector<Vector> select_post(String Post_name) {
		connectDb();

		try {
			String sql = "SELECT * FROM reports where review_num is null and  keyword = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, Post_name);
			rs = pstmt.executeQuery();

			Vector<Vector> data = new Vector<>();

			while (rs.next()) {
				Vector rowData = new Vector<>();

				rowData.add(rs.getString("ID"));
				rowData.add(rs.getString("USER_ID"));
				rowData.add(rs.getString("KEYWORD"));
				String sql2 = "select content from reviews where review_num = " + rs.getString("REVIEW_NUM");
                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery(sql2);
                if(rs2.next()) {
                	rowData.add(rs2.getString("content"));
                }
                else {
                	rowData.add("확인불가");
                }
                rowData.add(rs.getString("CONTENT"));
                
				data.add(rowData);
			}

			return data;

		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 " + e.getMessage());
		} finally {
			closeDb();
		}

		return null;
	}
	
	// 신고 댓글 목록 조회
		public Vector<Vector> select_review(String review_num) {
			connectDb();
			String Post_name = "";
			try {
				String sql_n = "SELECT keyword FROM reviews where review_num = ?";
				pstmt = con.prepareStatement(sql_n);
				pstmt.setString(1, review_num);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					Post_name = rs.getString("keyword");
				}
				
				String sql = "SELECT * FROM reports where keyword = ? and review_num = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, Post_name);
				pstmt.setString(2, review_num);
				rs = pstmt.executeQuery();

				Vector<Vector> data = new Vector<>();

				while (rs.next()) {
					Vector rowData = new Vector<>();

					rowData.add(rs.getString("ID"));
					rowData.add(rs.getString("USER_ID"));
					rowData.add(rs.getString("KEYWORD"));
					String sql2 = "select content from reviews where review_num = " + rs.getString("REVIEW_NUM");
	                Statement stmt2 = con.createStatement();
	                ResultSet rs2 = stmt2.executeQuery(sql2);
	                if(rs2.next()) {
	                	rowData.add(rs2.getString("content"));
	                }
	                else {
	                	rowData.add("확인불가");
	                }
	                rowData.add(rs.getString("CONTENT"));
	                
					data.add(rowData);
				}

				return data;

			} catch (SQLException e) {
				System.out.println("SQL 구문 오류 " + e.getMessage());
			} finally {
				closeDb();
			}

			return null;
		}
}