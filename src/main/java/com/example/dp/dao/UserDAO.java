package com.example.dp.dao;

import com.example.dp.database.DatabaseConnection;
import com.example.dp.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private Connection connection;

    public UserDAO() {
        connection = DatabaseConnection
                .getInstance()
                .getConnection();
    }

    public User login(String email, String password) {

        String query =
                "SELECT * FROM users WHERE email = ? AND password = ?";

        try {

            PreparedStatement statement =
                    connection.prepareStatement(query);

            statement.setString(1, email);

            statement.setString(2, password);

            ResultSet resultSet =
                    statement.executeQuery();

            if(resultSet.next()) {

                User user = new User();

                user.setId(resultSet.getInt("id"));

                user.setFullName(
                        resultSet.getString("full_name")
                );

                user.setEmail(
                        resultSet.getString("email")
                );

                user.setPassword(
                        resultSet.getString("password")
                );

                user.setPhone(
                        resultSet.getString("phone")
                );

                user.setRole(
                        resultSet.getString("role")
                );

                user.setCustomerType(
                        resultSet.getString("customer_type")
                );

                user.setCreatedAt(
                        resultSet.getTimestamp("created_at")
                );

                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
