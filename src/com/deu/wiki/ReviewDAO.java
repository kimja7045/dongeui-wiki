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

public class ReviewDAO {
	// --------------------------------------------------------
	// �떛湲��넠 �뵒�옄�씤 �뙣�꽩�쓣 �쟻�슜�븳 �씤�뒪�꽩�뒪 由ы꽩
	private static ReviewDAO instance = new ReviewDAO();

	public ReviewDAO() {
	}

	public static ReviewDAO getInstance() {
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
//			System.out.println("�뱶�씪�씠踰� 濡쒕뱶 �꽦怨�!");

			con = DriverManager.getConnection(url, user, password);
//			System.out.println("DB �젒�냽 �꽦怨�!");
		} catch (ClassNotFoundException e) {
			System.out.println("�뱶�씪�씠踰� 濡쒕뱶 �떎�뙣! - " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("DB �젒�냽 �떎�뙣! - " + e.getMessage());
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
	
	public int up_count(String Post_name) {
		connectDb();

		int result = 0; 
		
		try {
    		String sql = "Update posts set view_count = view_count + 1 where keyword = '" + Post_name + "'";
            Statement stmt = con.createStatement();
            result = stmt.executeUpdate(sql);
		} 
		catch (SQLException e) {
			System.out.println("SQL 구문 오류" + e.getMessage());
		}
		finally {
			closeDb();
		}

		return result;
	}
	
	public int insert(ReviewDTO dto, String Post_name) {
		connectDb();

		int result = 0; 
		if (dto.getID().equals("")) {
			return -2;
		}
		try {
			String pk = "";
			CallableStatement cstmt = con.prepareCall( "{call Is_write_review(?,?,?)}" );
        	cstmt.setString(1, Post_name);
        	cstmt.setString(2, dto.getID());
        	cstmt.registerOutParameter(3, Types.VARCHAR);
        	cstmt.execute();
        	
        	if(cstmt.getString(3).equals("False")) {
        		String sql_pk = "select MAX(To_NUMBER(review_num)) as pk from reviews";
                Statement stmt_pk = con.createStatement();
                ResultSet rs_pk = stmt_pk.executeQuery(sql_pk);
                if(rs_pk.next()) {
                	pk = rs_pk.getString("pk");
                	pk = Integer.toString(Integer.parseInt(pk)+1);
                }
                else {
                	pk = "1";
                }
    			String sql = "INSERT INTO reviews VALUES (?,?,?,?,?,default,default,default)";

    			pstmt = con.prepareStatement(sql);
    			pstmt.setString(1, pk);
    			pstmt.setString(2, Post_name);
    			pstmt.setString(3, dto.getID());
    			pstmt.setString(4, dto.getpNum());
    			pstmt.setInt(5, dto.getPoint());
    			
    			result = pstmt.executeUpdate();
        	}
        	else {
        		result = -1;
        	}
        	
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류" + e.getMessage());
		} finally {
			closeDb();
		}

		return result;
	}

	
	public int update(ReviewDTO dto, String Post_name) {
		connectDb();
		
		int result = 0; 
		if (dto.getID().equals("")) {
			return -2;
		}
		try {
			String sql2 = "select MAX(To_NUMBER(review_num)) as pk from reviews where keyword ='"+ Post_name +"' and user_id = '"+ dto.getID() +"'";
            Statement stmt2 = con.createStatement();
            ResultSet rs2 = stmt2.executeQuery(sql2);
            if(rs2.next()) {
            	String sql = "update reviews set content=?, score=? where keyword = ? and user_id = ?";
    			pstmt = con.prepareStatement(sql);
    			pstmt.setString(1, dto.getpNum());
    			pstmt.setInt(2, dto.getPoint());
    			pstmt.setString(3, Post_name);
    			pstmt.setString(4, dto.getID());
    			pstmt.executeUpdate();
    			result = pstmt.executeUpdate();
            }
            else {
            	return -1;
            }
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류" + e.getMessage());
		} finally {
			closeDb();
		}

		return result;
	}

	public int delete(String idx, String ID) {
		connectDb();

		int result = 0;

		try {
			String sql = "DELETE FROM reviews WHERE review_num = ? and user_id = ?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, idx);
			pstmt.setString(2, ID);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류" + e.getMessage());
		} finally {
			closeDb();
		}
		return result;
	}

//	public Vector<Vector> selectOne(String keyword, String standard, String type) {
//		connectDb();
//		String sql = "SELECT * FROM reviews where keyword like '%"+keyword+"%'" + "Order by "+ standard + " " + type;
//		try {
//			pstmt = con.prepareStatement(sql);
//			rs = pstmt.executeQuery();       
//            int count = 1;
//            Vector<Vector> data = new Vector<>(); // 전체 레코드를 저장할 Vector 객체
//            while (rs.next()) {
//				Vector rowData = new Vector<>(); 
//
//				rowData.add(rs.getString("REVIEW_NUM"));
//				rowData.add(rs.getString("CONTENT"));
//				rowData.add(rs.getString("USER_ID"));
//				rowData.add(rs.getInt("SCORE"));
//
//				data.add(rowData);
//			}
//            return data;
//		} catch (SQLException e) {
//			System.out.println("SQL 구문 오류! - " + e.getMessage());
//		} finally {
//			closeDb();
//		}
//		return null;
//	}
	public Vector<Vector> select_report() {
		connectDb();

		try {
			String sql = "SELECT * FROM reviews where is_stop= '1'";

			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			Vector<Vector> data = new Vector<>();

			while (rs.next()) {
				Vector rowData = new Vector<>();

				rowData.add(rs.getString("REVIEW_NUM"));
				rowData.add(rs.getString("CONTENT"));
				rowData.add(rs.getString("USER_ID"));
				rowData.add(rs.getInt("SCORE"));

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
	
	public Vector<Vector> select(String post_name) {
		connectDb();

		try {
			String sql = "SELECT * FROM reviews where is_stop='0' and keyword = '" + post_name + "'";

			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			Vector<Vector> data = new Vector<>();

			while (rs.next()) {
				Vector rowData = new Vector<>();

				rowData.add(rs.getString("REVIEW_NUM"));
				rowData.add(rs.getString("CONTENT"));
				rowData.add(rs.getString("USER_ID"));
				rowData.add(rs.getInt("SCORE"));

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