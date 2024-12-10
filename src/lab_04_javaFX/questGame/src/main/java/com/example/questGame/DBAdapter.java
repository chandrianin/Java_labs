package com.example.questGame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBAdapter {
    private Connection con;

    public void connect() {
        try {
            con = DriverManager.getConnection("jdbc:sqlite:quest.sqlite");

            Statement stmt = con.createStatement();
            String sql = "create table if not exists firstTable" +
                    "(" +
                    "    id           integer primary key autoincrement," +
                    "    firstNumber  integer not null," +
                    "    secondNumber integer not null" +
                    ");" +
                    "create table if not exists secondTable" +
                    "(" +
                    "    id          integer primary key autoincrement," +
                    "    checksCount integer not null," +
                    "    value       integer not null" +
                    ");";
            stmt.execute(sql);
            System.out.println("Tables created");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void insertFirst(Integer firstNumber, Integer secondNumber) {
        String sql = "insert into firstTable (firstNumber, secondNumber)" +
                "values (" + firstNumber + ", " + secondNumber + ")";
        try (Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("error in 'insertFirst': " + e);
        }
        System.out.println("Inserted in firstTable");
    }

    public void insertSecond(Integer checksCount, Integer value) {
        String sql = "insert into secondTable (checksCount, value)" +
                "values (" + checksCount + ", " + value + ")";
        try (Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("error in 'insertFirst': " + e);
        }
        System.out.println("Inserted in secondTable");
    }
}
