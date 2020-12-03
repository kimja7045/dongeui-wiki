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

public class AdminDAO {
    // --------------------------------------------------------
    // �̱��� ������ ������ ������ �ν��Ͻ� ����
    private static AdminDAO instance = new AdminDAO();

    private AdminDAO() {}

    public static AdminDAO getInstance() {
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
			System.out.println("����̹� �ε� ����!");

            con = DriverManager.getConnection(url, user, password);
			System.out.println("DB ���� ����!");
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

    public int input_nick(AdminDTO dto) {
    	connectDb();

        int result = 0; // �Խù� �߰� ���� ����(0 : ����, 1 : ����)

        try {
            String sql = "Update users set nickname=? where id=? and password=?";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, dto.getNickname());
            pstmt.setString(2, dto.getId());
            pstmt.setString(3, dto.getPassword());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL ���� ����! - " + e.getMessage());
        } finally {
            closeDb();
        }

        return result;
    }
    public String login(AdminDTO dto) {
        // ���̵�, �н����� ��ġ ���ο� ���� �ٸ� ������ ����
        connectDb();

        String result = ""; // �⺻��. ���̵� ���� ��� �⺻�� ���ϵ�

        try {
            // 1. ���̵� �˻�
//            String sql = "SELECT * FROM posts WHERE id=?";
//            pstmt = con.prepareStatement(sql);
//            System.out.println(dto.getId());
//            pstmt.setString(1, dto.getId());
//            rs = pstmt.executeQuery();
//
//            if(rs.next()) { // ���̵� ���� ���
//            	System.out.println('a');
//                // 2. ���̵�, �н����� ���� �˻�
//                sql = "SELECT * FROM posts WHERE id=? AND password=?";
//                pstmt = con.prepareStatement(sql);
//                pstmt.setString(1, dto.getId());
//                pstmt.setString(2, dto.getPassword());
//                rs = pstmt.executeQuery();
//
//                if(rs.next()) { // ���̵� �ְ�, �н����尡 ��ġ�� ���
//                    result = 1;
//                } else { // ���̵� �ְ�, �н����尡 ��ġ���� ���� ���
//                    result = -1;
//                }
//            }
        	CallableStatement cstmt = con.prepareCall( "{call return_login(?,?,?,?)}" );
        	cstmt.setString(1, dto.getId());
        	cstmt.setString(2, dto.getPassword());
        	cstmt.registerOutParameter(3, Types.VARCHAR);
        	cstmt.registerOutParameter(4, Types.VARCHAR);
        	cstmt.execute();
        	result=cstmt.getString(3);
        	dto.setType(cstmt.getString(4));
            // ���̵� ���� ��� result �������� �⺻�� 0 �״�� ���
//			System.out.println(result);
        } catch (SQLException e) {
            System.out.println("SQL ���� ����! - " + e.getMessage());
        } finally {
            closeDb();
        }

        return result; // �α��� ��� ����
    }

    // �Խù� �߰�
    public int insert(AdminDTO dto) {
        connectDb();

        int result = 0; // �Խù� �߰� ���� ����(0 : ����, 1 : ����)

        try {
            // DTO ��ü�� ����� �����͸� DB �� INSERT
            String sql = "INSERT INTO posts VALUES (?,?,?)";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, dto.getNickname());
            pstmt.setString(2, dto.getId());
            pstmt.setString(3, dto.getPassword());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL ���� ����! - " + e.getMessage());
        } finally {
            closeDb();
        }

        return result;
    }

    // �Խù� ����
    public int update(AdminDTO dto) {
        connectDb();

        int result = 0; // �Խù� ���� ���� ����(0 : ����, 1 : ����)

        try {
            // ���ڵ� ����
            String sql = "update posts set " + "keyword=?, " + "content=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, dto.getNickname());
            pstmt.setString(2, dto.getId());
//            pstmt.setString(3, dto.getPassword());
//            pstmt.setInt(4, dto.getIdx());
            pstmt.executeUpdate();
            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL ���� ����! - " + e.getMessage());
        } finally {
            closeDb();
        }

        return result;
    }

    // �Խù� ����
    public int delete(int idx) {
        connectDb();

        int result = 0; // �Խù� ���� ���� ����(0 : ����, 1 : ����)

        try {
            // ���޹��� ��ȣ(idx)�� ����Ͽ� ���ڵ� ����
            String sql = "DELETE FROM posts WHERE idx=?";

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

    public Vector<Vector> selectOne(String keyword) {
		connectDb();
		String sql = "SELECT * FROM posts where keyword like '%"+keyword+"%'";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();       
            int count = 1;
            Vector<Vector> data = new Vector<>(); // ��ü ���ڵ带 ������ Vector ��ü
			while(rs.next()) {
                Vector rowData = new Vector<>(); // 1�� ���ڵ带 ������ Vector ��ü
            
                rowData.add(count);
                rowData.add(rs.getString("keyword"));
                String sql2 = "select nickname from users where id = " + rs.getString("user_id");
                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery(sql2);
                if(rs2.next()) {
                	rowData.add(rs2.getString("nickname"));
                }
                else {
                	rowData.add("Ȯ�κҰ�");
                }
                rowData.add(rs.getInt("view_count"));
                rowData.add(rs.getFloat("AVG_SCORE"));
                rowData.add(rs.getInt("review_count"));
                rowData.add(rs.getString("created_at"));
                count+=1;

                data.add(rowData);
            }
            return data;
		} catch (SQLException e) {
			System.out.println("SQL ���� ����! - " + e.getMessage());
		} finally {
			closeDb();
		}
		return null;
	}
    
    // �Խù� ��� ��ȸ
    public Vector<Vector> select(String name) {
        connectDb();

        try {
            String sql = "select * from posts where is_stop='0' Order by "+ name +" desc";
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
       
            int count = 1;
            Vector<Vector> data = new Vector<>(); // ��ü ���ڵ带 ������ Vector ��ü
         
            while(rs.next()) {
                Vector rowData = new Vector<>(); // 1�� ���ڵ带 ������ Vector ��ü
            
                rowData.add(count);
                rowData.add(rs.getString("keyword"));
                String sql2 = "select nickname from users where id = " + rs.getString("user_id");
                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery(sql2);
                if(rs2.next()) {
                	rowData.add(rs2.getString("nickname"));
                }
                else {
                	rowData.add("Ȯ�κҰ�");
                }
                rowData.add(rs.getInt("view_count"));
                rowData.add(rs.getFloat("AVG_SCORE"));
                rowData.add(rs.getInt("review_count"));
                rowData.add(rs.getString("created_at"));
                count+=1;

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