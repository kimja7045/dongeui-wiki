package com.deu.wiki;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class AdminDAO {
    // --------------------------------------------------------
    // 싱글톤 디자인 패턴을 적용한 인스턴스 리턴
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
			System.out.println("드라이버 로드 성공!");

            con = DriverManager.getConnection(url, user, password);
			System.out.println("DB 접속 성공!");
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


    public int login(AdminDTO dto) {
        // 아이디, 패스워드 일치 여부에 따라 다른 정수값 리턴
        connectDb();

        int result = 0; // 기본값. 아이디가 없을 경우 기본값 리턴됨

        try {
            // 1. 아이디 검색
            String sql = "SELECT * FROM users WHERE id=?";
            pstmt = con.prepareStatement(sql);
            System.out.println(dto.getId());
            pstmt.setString(1, dto.getId());
            rs = pstmt.executeQuery();

            if(rs.next()) { // 아이디가 있을 경우
            	System.out.println('a');
                // 2. 아이디, 패스워드 동시 검색
                sql = "SELECT * FROM users WHERE id=? AND password=?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, dto.getId());
                pstmt.setString(2, dto.getPassword());
                rs = pstmt.executeQuery();

                if(rs.next()) { // 아이디가 있고, 패스워드가 일치할 경우
                    result = 1;
                } else { // 아이디가 있고, 패스워드가 일치하지 않을 경우
                    result = -1;
                }
            }

            // 아이디가 없을 경우 result 변수값은 기본값 0 그대로 사용
//			System.out.println(result);
        } catch (SQLException e) {
            System.out.println("SQL 구문 오류! - " + e.getMessage());
        } finally {
            closeDb();
        }

        return result; // 로그인 결과 리턴
    }

    // 관리자 추가
    public int insert(AdminDTO dto) {
        connectDb();

        int result = 0; // 관리자 추가 성공 여부(0 : 실패, 1 : 리턴)

        try {
            // DTO 객체에 저장된 데이터를 DB 에 INSERT
            String sql = "INSERT INTO users VALUES (null,?,?,?)";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, dto.getNickname());
            pstmt.setString(2, dto.getId());
            pstmt.setString(3, dto.getPassword());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL 구문 오류! - " + e.getMessage());
        } finally {
            closeDb();
        }

        return result;
    }

    // 관리자 수정
    public int update(AdminDTO dto) {
        connectDb();

        int result = 0; // 관리자 수정 성공 여부(0 : 실패, 1 : 리턴)

        try {
            // 레코드 수정
            String sql = "update users set " + "nickname=?, " + "id=?," + "password=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, dto.getNickname());
            pstmt.setString(2, dto.getId());
            pstmt.setString(3, dto.getPassword());
            pstmt.setInt(4, dto.getIdx());
            pstmt.executeUpdate();
            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL 구문 오류! - " + e.getMessage());
        } finally {
            closeDb();
        }

        return result;
    }

    // 관리자 삭제
    public int delete(int idx) {
        connectDb();

        int result = 0; // 관리자 삭제 성공 여부(0 : 실패, 1 : 리턴)

        try {
            // 전달받은 번호(idx)를 사용하여 레코드 삭제
            String sql = "DELETE FROM users WHERE idx=?";

            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, idx);

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL 구문 오류! - " + e.getMessage());
        } finally {
            closeDb();
        }

        return result;
    }


    // 게시물 목록 조회
    public Vector<Vector> select() {
        connectDb();

        try {
            String sql = "select * from posts where is_stop='0'";
        
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
       
         
            Vector<Vector> data = new Vector<>(); // 전체 레코드를 저장할 Vector 객체
         
            while(rs.next()) {
                Vector rowData = new Vector<>(); // 1개 레코드를 저장할 Vector 객체
            
         
                rowData.add(rs.getString("keyword"));
////                rowData.add(rs.getString("user_id"));
//                rowData.add(rs.getInt("view_count"));
//                rowData.add(rs.getString("content"));
//             

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