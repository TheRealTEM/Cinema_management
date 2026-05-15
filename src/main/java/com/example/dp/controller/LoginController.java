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
            com.example.dp.state.Session.getInstance().setLoggedInUser(user);

            try {
                String fxmlPath = user.getRole() != null && user.getRole().equalsIgnoreCase("ADMIN")
                        ? "/view/admin-dashboard.fxml"
                        : "/view/dashboard.fxml";

                FXMLLoader loader =
                        new FXMLLoader(
                                getClass().getResource(
                                        fxmlPath
                                )
                        );

                Scene scene =
                        new Scene(loader.load());

                Stage stage =
                        (Stage) rootPane
                                .getScene()
                                .getWindow();

                stage.setScene(scene);

                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            System.out.println(
                    "Invalid Email or Password"
            );
        }
    }

    @FXML
    private AnchorPane rootPane;

    @FXML
    public void openSignup() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/view/signup.fxml"
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