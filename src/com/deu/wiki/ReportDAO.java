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
	// �̱��� ������ ������ ������ �ν��Ͻ� ����
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
		if(rs != null) try { rs.close(); } catch(Exception e) {}
		if(pstmt != null) try { pstmt.close(); } catch(Exception e) {}
		if(con != null) try { con.close(); } catch(Exception e) {} 
	}
	
	// �Խñ� �Ű� �߰�
	public int insert(ReportDTO dto) {
		connectDb();

        int result = 0; // �Խù� �߰� ���� ����(0 : ����, 1 : ����)
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
            System.out.println("SQL ���� ����! - " + e.getMessage());
        } finally {
            closeDb();
        }

        return result;
	}
	
	// ��� �Ű� �߰�
	public int insert_review(ReportDTO dto) {
		connectDb();

        int result = 0; // �Խù� �߰� ���� ����(0 : ����, 1 : ����)
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
//        			pstmt.setString(4, "�Է� ����");
//                }
    			pstmt.setString(4, dto.getReview_num());
    			pstmt.setString(5, dto.getComent());
    			
    			result = pstmt.executeUpdate();
        	} else {
        		return -1;
        	}
        } catch (SQLException e) {
            System.out.println("SQL ���� ����! - " + e.getMessage());
        } finally {
            closeDb();
        }

        return result;
	}
	// ���� ����
//	public int update(ReportDTO dto) {
//		connectDb();
//
//		int result = 0; // ���� ���� ���� ����(0 : ����, 1 : ����)
//
//		try {
//			// ���ڵ� ����
//			String sql = "update total set " + "date=?, " + "sum=?" + " where idx=?";
//			pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, dto.getDate());
//			pstmt.setInt(2, dto.getSum());
//			pstmt.setInt(3, dto.getIdx());
//			pstmt.executeUpdate();
//			result = pstmt.executeUpdate();
//
//		} catch (SQLException e) {
//			System.out.println("SQL ���� ����! - " + e.getMessage());
//		} finally {
//			closeDb();
//		}
//
//		return result;
//	}
	
	// �Ű� �Խñ� ����
	public int delete(String idx) {
		connectDb();

		int result = 0;

		try {
			String sql = "DELETE FROM reports WHERE id = ?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, idx);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL ���� ����" + e.getMessage());
		} finally {
			closeDb();
		}
		return result;
	}
	
	// �Ű� �Խñ۸�� ��ȸ
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
                	rowData.add("Ȯ�κҰ�");
                }
                rowData.add(rs.getString("CONTENT"));
                
				data.add(rowData);
			}

			return data;

		} catch (SQLException e) {
			System.out.println("SQL ���� ���� " + e.getMessage());
		} finally {
			closeDb();
		}

		return null;
	}
	
	// �Ű� ��� ��� ��ȸ
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
	                	rowData.add("Ȯ�κҰ�");
	                }
	                rowData.add(rs.getString("CONTENT"));
	                
					data.add(rowData);
				}

				return data;

			} catch (SQLException e) {
				System.out.println("SQL ���� ���� " + e.getMessage());
			} finally {
				closeDb();
			}

			return null;
		}
}