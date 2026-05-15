package com.example.dp.controller;

import com.example.dp.dao.BookingDAO;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MyBookingsController
        implements Initializable {

    @FXML
    private HBox bookingsContainer;

    private final BookingDAO bookingDAO =
            new BookingDAO();

    @Override
    public void initialize(
            URL url,
            ResourceBundle resourceBundle
    ) {

        loadBookings();
    }

    private void loadBookings() {

        int userId = com.example.dp.state.Session.getInstance().getLoggedInUser() != null ? 
                     com.example.dp.state.Session.getInstance().getLoggedInUser().getId() : 1;
        List<String[]> bookings =
                bookingDAO.getUserBookings(
                        userId
                );

        for(String[] booking : bookings) {

            VBox card =
                    createBookingCard(
                            booking
                    );

            bookingsContainer
                    .getChildren()
                    .add(card);
        }
    }

    private VBox createBookingCard(
            String[] booking
    ) {

        VBox card =
                new VBox();

        card.getStyleClass()
                .add("movie-card");

        card.setPrefWidth(220);

        ImageView poster =
                new ImageView(
                        new Image(
                                booking[1]
                        )
                );

        poster.setFitWidth(220);

        poster.setFitHeight(300);

        poster.setPreserveRatio(false);

        VBox content =
                new VBox();

        content.setSpacing(8);

        content.setStyle(
                "-fx-padding: 15;"
        );

        Label title =
                new Label(
                        booking[0]
                );

        title.getStyleClass()
                .add("movie-title");

        Label seats =
                new Label(
                        "Seats: " +
                                booking[2]
                );

        Label total =
                new Label(
                        "$" +
                                booking[3]
                );

        content.getChildren().addAll(
                title,
                seats,
                total
        );

        card.getChildren().addAll(
                poster,
                content
        );

        return card;
    }

    @FXML
    public void handleDashboard() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/view/dashboard.fxml"
                            )
                    );

            Scene scene =
                    new Scene(
                            loader.load()
                    );

            Stage stage =
                    (Stage) bookingsContainer
                            .getScene()
                            .getWindow();

            stage.setScene(
                    scene
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}