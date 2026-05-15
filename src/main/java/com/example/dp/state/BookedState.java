package com.example.dp.state;

import javafx.scene.control.Button;

public class BookedState
        implements SeatState {

    @Override
    public void handleClick(
            Button seatButton
    ) {

        System.out.println(
                "Seat already booked"
        );
    }

    @Override
    public String getStyleClass() {

        return "booked-seat-button";
    }
}