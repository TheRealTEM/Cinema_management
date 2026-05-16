package com.example.dp.proxy;

import javafx.stage.Stage;

public class AdminProxy
        implements AdminAccess {

    private String role;

    private RealAdminDashboard dashboard;

    public AdminProxy(
            String role,
            Stage stage
    ) {

        this.role =
                role;

        dashboard =
                new RealAdminDashboard(
                        stage
                );
    }

    @Override
    public void openDashboard() {

        if(role.equalsIgnoreCase(
                "ADMIN"
        )) {

            dashboard.openDashboard();

        } else {

            System.out.println(
                    "Access Denied"
            );
        }
    }
}