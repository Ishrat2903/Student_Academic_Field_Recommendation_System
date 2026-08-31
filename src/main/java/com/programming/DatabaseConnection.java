package com.programming;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/student_management";

    private static final String USER = "root";

    private static final Dotenv dotenv = Dotenv.load();

    private static final String PASSWORD =
            dotenv.get("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {

        System.out.println("DB_PASSWORD loaded: " +
                (PASSWORD != null));

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}