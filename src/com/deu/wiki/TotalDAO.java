package com.deu.wiki;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TotalDAO {
	// --------------------------------------------------------
	// �̱��� ������ ������ ������ �ν��Ͻ� ����
	private static TotalDAO instance = new TotalDAO();
	
	private TotalDAO() {}
	
	public static TotalDAO getInstance() {
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
		if(rs != null) try { rs.close(); } catch(Exception e) {}
		if(pstmt != null) try { pstmt.close(); } catch(Exception e) {}
		if(con != null) try { con.close(); } catch(Exception e) {} 
	}
	
	// ���� �߰�
	public int insert(TotalDTO dto) {
		connectDb();
		
		int result = 0; // ���� �߰� ���� ����(0 : ����, 1 : ����)
		
		try {
			// DTO ��ü�� ����� �����͸� DB �� INSERT
			String sql = "INSERT INTO total VALUES (null,?,?)";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getDate());
			pstmt.setDouble(2, dto.getSum());
			
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("SQL ���� ����! - " + e.getMessage());
		} finally {
			closeDb();
		}
		
		return result;
	}

	// ���� ����
	public int update(TotalDTO dto) {
		connectDb();

		int result = 0; // ���� ���� ���� ����(0 : ����, 1 : ����)

		try {
			// ���ڵ� ����
			String sql = "update total set " + "date=?, " + "sum=?" + " where idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getDate());
			pstmt.setInt(2, dto.getSum());
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
	
	// ���� ����
	public int delete(int idx) {
		connectDb();
		
		int result = 0; // ���� ���� ���� ����(0 : ����, 1 : ����)
		
		try {
			// ���޹��� ��ȣ(idx)�� ����Ͽ� ���ڵ� ����
			String sql = "DELETE FROM total WHERE idx=?";
			
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

	
	// ������ ��ȸ
	public Vector<Vector> select() {
		connectDb();
		
		try {
			String sql = "SELECT * FROM total";
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			Vector<Vector> data = new Vector<>(); // ��ü ���ڵ带 ������ Vector ��ü
			
			while(rs.next()) {
				Vector rowData = new Vector<>(); // 1�� ���ڵ带 ������ Vector ��ü
				
				rowData.add(rs.getInt("idx"));
				rowData.add(rs.getString("date"));
				rowData.add(rs.getString("sum"));
				
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