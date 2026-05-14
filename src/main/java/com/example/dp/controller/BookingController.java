package com.example.dp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

import com.example.dp.model.Movie;
import javafx.scene.image.Image;
import com.example.dp.state.*;

public class BookingController {

    private Movie selectedMovie;

    @FXML
    private HBox seatContainer;

    @FXML
    private ImageView moviePosterImageView;

    @FXML
    private Label movieTitleLabel;

    @FXML
    private Label genreLabel;

    @FXML
    private Label durationLabel;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Label selectedSeatsLabel;

    private final List<String> selectedSeats =
            new ArrayList<>();

    private final int seatPrice = 15;

    @FXML
    public void initialize() {

        Image image =
                new Image(
                        "https://upload.wikimedia.org/wikipedia/en/1/15/Minions_the_Rise_of_Gru_poster.jpg"
                );

        moviePosterImageView.setImage(image);

        generateSeats();

        updateSummary();
    }


    @FXML
    public void handleSeatClick(
            ActionEvent event
    ) {

        Button seatButton =
                (Button) event.getSource();

        SeatState state =
                (SeatState)
                        seatButton
                                .getProperties()
                                .get("state");

        String seatId =
                seatButton.getId();

        if(state instanceof SelectedState) {

            selectedSeats.remove(
                    seatId
            );

        } else if(state instanceof AvailableState) {

            selectedSeats.add(
                    seatId
            );
        }

        state.handleClick(
                seatButton
        );

        updateSummary();
    }

    private void updateSummary() {

        int total =
                selectedSeats.size()
                        * seatPrice;

        totalPriceLabel.setText(
                "$" + total
        );

        if(selectedSeats.isEmpty()) {

            selectedSeatsLabel.setText(
                    "No seats selected"
            );

        } else {

            selectedSeatsLabel.setText(
                    "Seats: " +
                            String.join(
                                    ", ",
                                    selectedSeats
                            )
            );
        }
    }

    @FXML
    public void handlePayment() {

        if(selectedSeats.isEmpty()) {

            System.out.println(
                    "Select at least one seat!"
            );

            return;
        }

        System.out.println(
                "Proceeding to payment..."
        );

        System.out.println(
                "Selected Seats: " +
                        selectedSeats
        );

        System.out.println(
                "Total Price: $" +
                        (selectedSeats.size() * seatPrice)
        );
    }

    private void generateSeats() {

        GridPane leftGrid =
                new GridPane();

        GridPane rightGrid =
                new GridPane();

        leftGrid.getStyleClass()
                .add("seat-grid");

        rightGrid.getStyleClass()
                .add("seat-grid");

        leftGrid.setHgap(8);
        leftGrid.setVgap(8);

        rightGrid.setHgap(8);
        rightGrid.setVgap(8);

        leftGrid.setAlignment(Pos.CENTER);
        rightGrid.setAlignment(Pos.CENTER);

        int rows = 8;
        int cols = 6;

        for(int row = 0; row < rows; row++) {

            for(int col = 0; col < cols; col++) {

                String seatId =
                        String.valueOf(
                                (char)('A' + row)
                        ) + (col + 1);

                Button seatButton =
                        new Button();

                seatButton.setId(seatId);

                SeatState state =
                        new AvailableState();

                seatButton.getProperties()
                        .put(
                                "state",
                                state
                        );

                seatButton.getStyleClass()
                        .add(
                                state.getStyleClass()
                        );



                seatButton.setOnAction(
                        this::handleSeatClick
                );



                if(col < 6) {

                    leftGrid.add(
                            seatButton,
                            col,
                            row
                    );

                } else {

                    rightGrid.add(
                            seatButton,
                            col - 6,
                            row
                    );
                }
            }
        }

        seatContainer.getChildren()
                .addAll(
                        leftGrid,
                        rightGrid
                );
    }

    public void setMovie(Movie movie) {

        selectedMovie = movie;

        movieTitleLabel.setText(
                movie.getTitle()
        );

        genreLabel.setText(
                movie.getGenre()
        );

        durationLabel.setText(
                movie.getDurationMinutes()
                        + " min"
        );

        Image image =
                new Image(
                        movie.getPosterPath()
                );

        moviePosterImageView.setImage(image);
    }
}