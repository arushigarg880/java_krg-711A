package com.bank.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/bank_db";
        String user = "root";
        String password = "qwerty"; // 👈 put your password

        return DriverManager.getConnection(url, user, password);
    }
}