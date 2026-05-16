package com.example.dp.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.example.dp.facade.BookingFacade;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class PaymentController {

    @FXML
    private Label movieTitleLabel;

    @FXML
    private Label seatsLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private ComboBox<String>
            paymentMethodComboBox;

    @FXML
    private Button confirmPaymentButton;

    private final BookingFacade bookingFacade =
            new BookingFacade();



    private int showtimeId;




    @FXML
    public void initialize() {

        paymentMethodComboBox
                .getItems()
                .addAll(
                        "CARD",
                        "CASH",
                        "WALLET"
                );
    }

    @FXML
    public void handleConfirmPayment() {

        String paymentMethod =
                paymentMethodComboBox
                        .getValue();

        if(paymentMethod == null) {

            System.out.println(
                    "Select payment method"
            );

            return;
        }

        double total =
                Double.parseDouble(
                        totalLabel
                                .getText()
                                .replace("$", "")
                );

        String seatsText =
                seatsLabel
                        .getText()
                        .replace("Seats: ", "");

        String[] seats =
                seatsText.split(", ");

        boolean success =
                bookingFacade.completeBooking(
                        1,
                        showtimeId,
                        seats,
                        total,
                        paymentMethod
                );



        if(!success) {

            System.out.println(
                    "Booking failed"
            );

            return;
        }
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
                    (Stage) confirmPaymentButton
                            .getScene()
                            .getWindow();

            stage.setScene(scene);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void setPaymentData(
            String movieName,
            String seats,
            double total,
            int showtimeId
    ) {

        this.showtimeId =
                showtimeId;

        movieTitleLabel.setText(
                movieName
        );

        seatsLabel.setText(
                seats
        );

        totalLabel.setText(
                "$" + total
        );
    }


}