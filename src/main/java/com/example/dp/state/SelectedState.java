package com.example.dp.state;

import javafx.scene.control.Button;

public class SelectedState
        implements SeatState {

    @Override
    public void handleClick(
            Button seatButton
    ) {

        seatButton.getProperties()
                .put(
                        "state",
                        new AvailableState()
                );

        seatButton.getStyleClass()
                .removeAll(
                        "seat-button",
                        "booked-seat-button",
                        "selected-seat-button"
                );

        seatButton.getStyleClass()
                .add("seat-button");
    }

    @Override
    public String getStyleClass() {

        return "selected-seat-button";
    }
}