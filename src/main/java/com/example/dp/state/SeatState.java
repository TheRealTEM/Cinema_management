package com.example.dp.state;

import javafx.scene.control.Button;

public interface SeatState {

    void handleClick(Button seatButton);

    String getStyleClass();
}