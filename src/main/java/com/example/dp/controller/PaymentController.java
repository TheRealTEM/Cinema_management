package com.example.dp.controller;

import com.example.dp.dao.BookingDAO;
import com.example.dp.dao.SeatDAO;

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

    private int showtimeId;

    private final BookingDAO bookingDAO =
            new BookingDAO();

    private final SeatDAO seatDAO =
            new SeatDAO();

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

        int bookingId =
                bookingDAO.createBooking(
                        1,
                        showtimeId,
                        total
                );

        if(bookingId == -1) {

            System.out.println(
                    "Booking failed"
            );

            return;
        }

        String seatsText =
                seatsLabel
                        .getText()
                        .replace("Seats: ", "");

        String[] seats =
                seatsText.split(", ");

        for(String seat : seats) {

            int seatId =
                    seatDAO.getSeatIdBySeatNumber(
                            seat
                    );

            if(seatId != -1) {

                bookingDAO.saveBookedSeat(
                        bookingId,
                        showtimeId,
                        seatId
                );
            }
        }

        System.out.println(
                "Booking saved successfully"
        );
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