package com.example.dp.state;

import javafx.scene.control.Button;

public class AvailableState
        implements SeatState {

    @Override
    public void handleClick(
            Button seatButton
    ) {
        seatButton.getProperties()
                .put(
                        "state",
                        new SelectedState()
                );
        seatButton.getStyleClass()
                .removeAll(
                        "seat-button",
                        "booked-seat-button",
                        "selected-seat-button"
                );

        seatButton.getStyleClass()
                .add("selected-seat-button");
    }

    @Override
    public String getStyleClass() {

        return "seat-button";
    }
}