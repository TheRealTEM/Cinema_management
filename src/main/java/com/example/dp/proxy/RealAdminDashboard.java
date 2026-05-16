package com.example.dp.proxy;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RealAdminDashboard
        implements AdminAccess {

    private Stage stage;

    public RealAdminDashboard(
            Stage stage
    ) {

        this.stage = stage;
    }

    @Override
    public void openDashboard() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/view/admin-dashboard.fxml"
                            )
                    );

            Scene scene =
                    new Scene(
                            loader.load()
                    );

            stage.setScene(scene);

            stage.show();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}