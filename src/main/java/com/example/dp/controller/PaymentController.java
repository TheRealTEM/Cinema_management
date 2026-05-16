package com.example.dp.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.example.dp.decorator.*;
import com.example.dp.facade.CinemaFacade;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.CheckBox;

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
    private CheckBox popcornCheckBox;

    @FXML
    private CheckBox vipLoungeCheckBox;

    @FXML
    private Button confirmPaymentButton;



    private int showtimeId;
    private final CinemaFacade cinemaFacade = new CinemaFacade();



    @FXML
    public void initialize() {
        paymentMethodComboBox.getItems().addAll("CARD", "CASH", "WALLET");
    }

    @FXML
    private void handleAddonsChange() {
        // Demonstrate Decorator Pattern
        double basePrice = Double.parseDouble(totalLabel.getText().replace("$", ""));
        Ticket myTicket = new BaseTicket(movieTitleLabel.getText(), basePrice);

        if (popcornCheckBox != null && popcornCheckBox.isSelected()) {
            myTicket = new PopcornDecorator(myTicket);
        }
        if (vipLoungeCheckBox != null && vipLoungeCheckBox.isSelected()) {
            myTicket = new VIPLoungeDecorator(myTicket);
        }

        System.out.println("[DECORATOR] Current Ticket: " + myTicket.getDescription());
        // For demonstration, we could update the totalLabel here if we had the original base price stored
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

        double total = Double.parseDouble(totalLabel.getText().replace("$", ""));
        // Using the Decorator to calculate final total
        Ticket finalTicket = new BaseTicket(movieTitleLabel.getText(), total);
        if (popcornCheckBox != null && popcornCheckBox.isSelected()) finalTicket = new PopcornDecorator(finalTicket);
        if (vipLoungeCheckBox != null && vipLoungeCheckBox.isSelected()) finalTicket = new VIPLoungeDecorator(finalTicket);
        
        double finalAmount = finalTicket.getPrice();
        String seatsText = seatsLabel.getText().replace("Seats: ", "");
        List<String> seatList = Arrays.asList(seatsText.split(", "));

        // Using the Facade to handle the entire complex process
        boolean success = cinemaFacade.purchaseTicket(showtimeId, seatList, finalAmount, paymentMethod);

        if(!success) {
            System.out.println("Booking failed via Facade");
            return;
        }

        System.out.println("Booking successfully processed via Facade. Final Description: " + finalTicket.getDescription());

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