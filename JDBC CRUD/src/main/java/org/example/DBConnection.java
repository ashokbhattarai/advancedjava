package org.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/jdbc_demo";
    private static final String USER = "ashok";
    private static final String PASSWORD = "Password123";

    public static Connection getConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con =
                    DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Connected to MySQL");

            return con;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}