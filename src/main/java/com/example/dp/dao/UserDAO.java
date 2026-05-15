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
                "SELECT * FROM users WHERE LOWER(email) = LOWER(?) AND password = ?";

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


    public boolean register(User user) {

        String query =
                "INSERT INTO users " +
                        "(full_name, email, password, phone, role, customer_type) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        try {

            PreparedStatement statement =
                    connection.prepareStatement(query);

            statement.setString(
                    1,
                    user.getFullName()
            );

            statement.setString(
                    2,
                    user.getEmail()
            );

            statement.setString(
                    3,
                    user.getPassword()
            );

            statement.setString(
                    4,
                    user.getPhone()
            );

            statement.setString(
                    5,
                    user.getRole()
            );

            statement.setString(
                    6,
                    user.getCustomerType()
            );

            int rows =
                    statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
