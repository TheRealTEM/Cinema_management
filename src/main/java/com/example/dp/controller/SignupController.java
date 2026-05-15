package com.example.dp.controller;

import com.example.dp.dao.UserDAO;
import com.example.dp.model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignupController {

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private PasswordField passwordField;

    private UserDAO userDAO = new UserDAO();

    @FXML
    public void handleSignup() {

        User user = new User();

        user.setFullName(
                fullNameField.getText().trim()
        );

        user.setEmail(
                emailField.getText().trim()
        );

        user.setPhone(
                phoneField.getText().trim()
        );

        user.setPassword(
                passwordField.getText().trim()
        );

        user.setRole("CUSTOMER");

        user.setCustomerType("NORMAL");

        boolean success =
                userDAO.register(user);

        if(success) {

            System.out.println(
                    "Account Created!"
            );

        } else {

            System.out.println(
                    "Signup Failed!"
            );
        }
    }

    @FXML
    private AnchorPane rootPane;

    @FXML
    public void openLogin() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/view/login.fxml"
                            )
                    );

            Scene scene =
                    new Scene(loader.load());

            Stage stage =
                    (Stage) rootPane
                            .getScene()
                            .getWindow();

            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}