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

public class MainDAO {
    // --------------------------------------------------------
    // 싱글톤 디자인 패턴을 적용한 인스턴스 리턴
    private static MainDAO instance = new MainDAO();

    private MainDAO() {}

    public static MainDAO getInstance() {
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

    public int input_nick(MainDTO dto) {
    	connectDb();

        int result = 0; // 게시물 추가 성공 여부(0 : 실패, 1 : 리턴)

        try {
            String sql = "Update users set nickname=? where id=? and password=?";

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
    public String login(MainDTO dto) {
        // 아이디, 패스워드 일치 여부에 따라 다른 정수값 리턴
        connectDb();

        String result = ""; // 기본값. 아이디가 없을 경우 기본값 리턴됨

        try {
            // 1. 아이디 검색
//            String sql = "SELECT * FROM posts WHERE id=?";
//            pstmt = con.prepareStatement(sql);
//            System.out.println(dto.getId());
//            pstmt.setString(1, dto.getId());
//            rs = pstmt.executeQuery();
//
//            if(rs.next()) { // 아이디가 있을 경우
//            	System.out.println('a');
//                // 2. 아이디, 패스워드 동시 검색
//                sql = "SELECT * FROM posts WHERE id=? AND password=?";
//                pstmt = con.prepareStatement(sql);
//                pstmt.setString(1, dto.getId());
//                pstmt.setString(2, dto.getPassword());
//                rs = pstmt.executeQuery();
//
//                if(rs.next()) { // 아이디가 있고, 패스워드가 일치할 경우
//                    result = 1;
//                } else { // 아이디가 있고, 패스워드가 일치하지 않을 경우
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
            // 아이디가 없을 경우 result 변수값은 기본값 0 그대로 사용
//			System.out.println(result);
        } catch (SQLException e) {
            System.out.println("SQL 구문 오류! - " + e.getMessage());
        } finally {
            closeDb();
        }

        return result; // 로그인 결과 리턴
    }

    // 게시물 추가
    public int insert(MainDTO dto, String ID) {
        connectDb();

        int result = 0; // 게시물 추가 성공 여부(0 : 실패, 1 : 리턴)

        try {
            // DTO 객체에 저장된 데이터를 DB 에 INSERT
            String sql = "INSERT INTO posts VALUES (?,?,default,default,default,default,default,default)";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, dto.getKeyword());
            pstmt.setString(2, ID);

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL 구문 오류! - " + e.getMessage());
        } finally {
            closeDb();
        }

        return result;
    }

    // 게시물 수정
    public int update(MainDTO dto) {
        connectDb();

        int result = 0; // 게시물 수정 성공 여부(0 : 실패, 1 : 리턴)

        try {
            // 레코드 수정
            String sql = "update posts set " + "keyword=?, " + "content=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, dto.getNickname());
            pstmt.setString(2, dto.getId());
//            pstmt.setString(3, dto.getPassword());
//            pstmt.setInt(4, dto.getIdx());
            pstmt.executeUpdate();
            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL 구문 오류! - " + e.getMessage());
        } finally {
            closeDb();
        }

        return result;
    }
    
    // 게시물 삭제
    public int delete(int idx) {
        connectDb();

        int result = 0; // 게시물 삭제 성공 여부(0 : 실패, 1 : 리턴)

        try {
            // 전달받은 번호(idx)를 사용하여 레코드 삭제
            String sql = "DELETE FROM posts WHERE idx=?";

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

    public Vector<Vector> selectOne(String keyword, String standard, String type, int search_mode) {
		connectDb();
		String sql = "SELECT * FROM posts where keyword like '%"+keyword+"%' and Is_stop = '" + Integer.toString(search_mode) + "' Order by "+ standard + " " + type;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();       
            int count = 1;
            Vector<Vector> data = new Vector<>(); // 전체 레코드를 저장할 Vector 객체
			while(rs.next()) {
                Vector rowData = new Vector<>(); // 1개 레코드를 저장할 Vector 객체
            
                rowData.add(count);
                rowData.add(rs.getString("keyword"));
                //닉네임 출력 구문
                String sql2 = "select nickname from users where id = " + rs.getString("user_id");
                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery(sql2);
                if(rs2.next()) {
                	rowData.add(rs2.getString("nickname"));
                }
                else {
                	rowData.add("확인불가");
                }
                rowData.add(rs.getInt("view_count"));
                rowData.add(rs.getFloat("AVG_SCORE"));
                rowData.add(rs.getInt("review_count"));
                //생성일 출력 프로시저
                CallableStatement cstmt = con.prepareCall( "{call post_days_between(?,?)}" );
            	cstmt.setString(1, rs.getString("keyword"));
            	cstmt.registerOutParameter(2, Types.VARCHAR);
            	cstmt.execute();
            	rowData.add(cstmt.getString(2));
            	
                count+=1;
                data.add(rowData);
            }
            return data;
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류! - " + e.getMessage());
		} finally {
			closeDb();
		}
		return null;
	}
    
    // 게시물 목록 조회
    public Vector<Vector> select(String name, int search_mode) {
        connectDb();

        try {
            String sql = "select * from posts where is_stop='"+Integer.toString(search_mode)+"' Order by "+ name +" asc";
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
       
            int count = 1;
            Vector<Vector> data = new Vector<>(); // 전체 레코드를 저장할 Vector 객체
         
            while(rs.next()) {
                Vector rowData = new Vector<>(); // 1개 레코드를 저장할 Vector 객체
            
                rowData.add(count);
                rowData.add(rs.getString("keyword"));
                String sql2 = "select nickname from users where id = " + rs.getString("user_id");
                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery(sql2);
                if(rs2.next()) {
                	rowData.add(rs2.getString("nickname"));
                }
                else {
                	rowData.add("확인불가");
                }
                rowData.add(rs.getInt("view_count"));
                rowData.add(rs.getFloat("AVG_SCORE"));
                rowData.add(rs.getInt("review_count"));
                CallableStatement cstmt = con.prepareCall( "{call post_days_between(?,?)}" );
            	cstmt.setString(1, rs.getString("keyword"));
            	cstmt.registerOutParameter(2, Types.VARCHAR);
            	cstmt.execute();
            	rowData.add(cstmt.getString(2));
                count+=1;

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