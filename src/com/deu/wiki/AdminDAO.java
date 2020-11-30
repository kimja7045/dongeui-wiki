package com.deu.wiki;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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


    public int login(AdminDTO dto) {
        // ���̵�, �н����� ��ġ ���ο� ���� �ٸ� ������ ����
        connectDb();

        int result = 0; // �⺻��. ���̵� ���� ��� �⺻�� ���ϵ�

        try {
            // 1. ���̵� �˻�
            String sql = "SELECT * FROM users WHERE id=?";
            pstmt = con.prepareStatement(sql);
            System.out.println(dto.getId());
            pstmt.setString(1, dto.getId());
            rs = pstmt.executeQuery();

            if(rs.next()) { // ���̵� ���� ���
                // 2. ���̵�, �н����� ���� �˻�
                sql = "SELECT * FROM users WHERE id=? AND password=?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, dto.getId());
                pstmt.setString(2, dto.getPassword());
                rs = pstmt.executeQuery();

                if(rs.next()) { // ���̵� �ְ�, �н����尡 ��ġ�� ���
                    result = 1;
                } else { // ���̵� �ְ�, �н����尡 ��ġ���� ���� ���
                    result = -1;
                }
            }

            // ���̵� ���� ��� result �������� �⺻�� 0 �״�� ���
//			System.out.println(result);
        } catch (SQLException e) {
            System.out.println("SQL ���� ����! - " + e.getMessage());
        } finally {
            closeDb();
        }

        return result; // �α��� ��� ����
    }

    // ������ �߰�
    public int insert(AdminDTO dto) {
        connectDb();

        int result = 0; // ������ �߰� ���� ����(0 : ����, 1 : ����)

        try {
            // DTO ��ü�� ����� �����͸� DB �� INSERT
            String sql = "INSERT INTO users VALUES (null,?,?,?)";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, dto.getName());
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

    // ������ ����
    public int update(AdminDTO dto) {
        connectDb();

        int result = 0; // ������ ���� ���� ����(0 : ����, 1 : ����)

        try {
            // ���ڵ� ����
            String sql = "update users set " + "nickname=?, " + "id=?," + "password=? " + " where idx=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, dto.getName());
            pstmt.setString(2, dto.getId());
            pstmt.setString(3, dto.getPassword());
            pstmt.setInt(4, dto.getIdx());
            pstmt.executeUpdate();
            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL ���� ����! - " + e.getMessage());
        } finally {
            closeDb();
        }

        return result;
    }

    // ������ ����
    public int delete(int idx) {
        connectDb();

        int result = 0; // ������ ���� ���� ����(0 : ����, 1 : ����)

        try {
            // ���޹��� ��ȣ(idx)�� ����Ͽ� ���ڵ� ����
            String sql = "DELETE FROM users WHERE idx=?";

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


    // �����ڸ�� ��ȸ
    public Vector<Vector> select() {
        connectDb();

        try {
            String sql = "SELECT * FROM users";

            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            Vector<Vector> data = new Vector<>(); // ��ü ���ڵ带 ������ Vector ��ü

            while(rs.next()) {
                Vector rowData = new Vector<>(); // 1�� ���ڵ带 ������ Vector ��ü

                rowData.add(rs.getInt("idx"));
                rowData.add(rs.getString("nickname"));
                rowData.add(rs.getString("id"));
                rowData.add(rs.getString("password"));

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