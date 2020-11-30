package com.deu.wiki;
import java.sql.*;


public class DB_Conn_Query {
    Connection con = null;

    public DB_Conn_Query() {

        String url = "jdbc:oracle:thin:@localhost:1521:XE";

        String id = "deu_wiki";

        String password = "1234";

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

            System.out.println("����̹� ���� ����");

            con = DriverManager.getConnection(url, id, password);

            System.out.println("DB ���� ����");

        } catch (ClassNotFoundException e) {

            System.out.println("No Driver.");

        } catch (SQLException e) {

            System.out.println("Connection Fail");

        }

    }



    private void sqlRun() {

        String query = "select �����̵�, ���̸�, ������ from ��";

        try {
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                System.out.print("\t" + rs.getString("�����̵�"));

                System.out.print("\t" + rs.getString("���̸�"));

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
//
//        dbconquery.sqlRun();

    }

}