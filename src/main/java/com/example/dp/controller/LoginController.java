package com.example.dp.controller;

import com.example.dp.dao.UserDAO;
import com.example.dp.model.User;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private UserDAO userDAO = new UserDAO();

    @FXML
    public void handleLogin() {

        String email =
                emailField.getText().trim();

        String password =
                passwordField.getText().trim();


        User user =
                userDAO.login(email, password);

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