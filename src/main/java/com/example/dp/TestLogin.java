package com.example.dp;

import com.example.dp.dao.UserDAO;
import com.example.dp.model.User;

public class TestLogin {

    public static void main(String[] args) {

        UserDAO userDAO = new UserDAO();

        User user =
                userDAO.login(
                        "admin@gmail.com",
                        "1234"
                );

        if(user != null) {

            System.out.println(
                    "Login Successful!"
            );

            System.out.println(
                    "Welcome " +
                            user.getFullName()
            );

        } else {

            System.out.println(
                    "Invalid Email or Password"
            );
        }
    }
}