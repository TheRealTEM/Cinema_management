package com.example.dp.controller;

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

        BookingFacade facade =
                new BookingFacade();

        facade.completeBooking(
                30,
                paymentMethod
        );

        System.out.println(
                "Payment completed"
        );
    }

    public void setPaymentData(
            String movieName,
            String seats,
            double total
    ) {

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