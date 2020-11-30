package com.deu.wiki;
import java.sql.*;


public class DB_Conn_Query {
    Connection con = null;

    public DB_Conn_Query() {

        String url = "jdbc:oracle:thin:@localhost:1521:XE";

        String id = "hmart";

        String password = "1234";

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

            System.out.println("드라이버 적재 성공");

            con = DriverManager.getConnection(url, id, password);

            System.out.println("DB 연결 성공");

        } catch (ClassNotFoundException e) {

            System.out.println("No Driver.");

        } catch (SQLException e) {

            System.out.println("Connection Fail");

        }

    }



    private void sqlRun() {

        String query = "select 고객아이디, 고객이름, 적립금 from 고객";

        try {
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                System.out.print("\t" + rs.getString("고객아이디"));

                System.out.print("\t" + rs.getString("고객이름"));

                System.out.print("\t" + rs.getInt(3) + "\n");

            }

            stmt.close();

            rs.close();

            con.close();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }



    public static void main(String arg[]) throws SQLException {

        DB_Conn_Query dbconquery = new DB_Conn_Query();

        dbconquery.sqlRun();

    }

}