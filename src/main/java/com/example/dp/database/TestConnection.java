package com.example.dp.database;


public class TestConnection {

    public static void main(String[] args) {

        if(DatabaseConnection
                .getInstance()
                .getConnection() != null) {

            System.out.println(
                    "Database Connected Successfully!"
            );

        } else {

            System.out.println(
                    "Connection Failed!"
            );
        }
    }
}