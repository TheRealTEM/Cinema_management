package com.example.dp.controller;

import com.example.dp.dao.ShowtimeDAO;
import com.example.dp.model.BookingShowtime;
import com.example.dp.model.Movie;
import com.example.dp.model.Showtime;
import com.example.dp.state.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BookingController {

    private Movie selectedMovie;

    private BookingShowtime selectedShowtime;

    private final ShowtimeDAO showtimeDAO =
            new ShowtimeDAO();

    private final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("dd - MM - yyyy");

    private final DateTimeFormatter timeFormatter =
            DateTimeFormatter.ofPattern("HH:mm");

    @FXML
    private HBox seatContainer;

    @FXML
    private Label theaterTitleLabel;

    @FXML
    private Label headerMovieTitleLabel;

    @FXML
    private Label showDateLabel;

    @FXML
    private Label showTimeLabel;

    @FXML
    private ImageView moviePosterImageView;

    @FXML
    private Label movieTitleLabel;

    @FXML
    private Label genreLabel;

    @FXML
    private Label durationLabel;

    @FXML
    private Label nextShowLabel;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Label selectedSeatsLabel;

    private final List<String> selectedSeats =
            new ArrayList<>();

    private double seatPrice = 15;

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

        double total =
                selectedSeats.size()
                        * seatPrice;

        totalPriceLabel.setText(
                formatPrice(total)
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

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/view/payment.fxml"
                            )
                    );

            Scene scene =
                    new Scene(
                            loader.load()
                    );

            PaymentController controller =
                    loader.getController();

            controller.setPaymentData(
                    selectedMovie.getTitle(),
                    selectedSeatsLabel.getText(),
                    selectedSeats.size() * seatPrice
            );

            Stage stage =
                    (Stage) totalPriceLabel
                            .getScene()
                            .getWindow();

            stage.setScene(scene);

        } catch (Exception e) {

            e.printStackTrace();
        }
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

    public void setMovie(
            Movie movie,
            Showtime showtime
    ) {

        selectedMovie = movie;

        headerMovieTitleLabel.setText(
                movie.getTitle()
        );

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

        theaterTitleLabel.setText(
                "Hall "
                        + showtime.getHallId()
        );

        showDateLabel.setText(
                showtime.getStartTime()
                        .toLocalDate()
                        .toString()
        );

        showTimeLabel.setText(
                showtime.getStartTime()
                        .toLocalTime()
                        .toString()
        );

        nextShowLabel.setText(
                showtime.getStartTime()
                        .toLocalTime()
                        .toString()
        );
    }

    private void loadShowtimeDetails(
            Movie movie
    ) {
        selectedShowtime =
                showtimeDAO
                        .getNextAvailableShowtimeByMovieId(
                                movie.getId()
                        );

        if(selectedShowtime == null) {
            theaterTitleLabel.setText(
                    "No showtime available"
            );

            showDateLabel.setText(
                    "-- - -- - ----"
            );

            showTimeLabel.setText(
                    "--:-- - --:--"
            );

            nextShowLabel.setText(
                    "No upcoming show"
            );

            return;
        }

        seatPrice =
                selectedShowtime.getBasePrice();

        theaterTitleLabel.setText(
                selectedShowtime.getHallName()
        );

        showDateLabel.setText(
                selectedShowtime
                        .getStartTime()
                        .format(dateFormatter)
        );

        showTimeLabel.setText(
                selectedShowtime
                        .getStartTime()
                        .format(timeFormatter)
                        + " - " +
                        selectedShowtime
                                .getEndTime()
                                .format(timeFormatter)
        );

        nextShowLabel.setText(
                selectedShowtime
                        .getStartTime()
                        .format(timeFormatter)
                        + " " +
                        selectedShowtime.getHallName()
        );

        updateSummary();
    }

    private String formatPrice(
            double price
    ) {
        if(price == Math.rint(price)) {
            return "$" + (int) price;
        }

        return String.format(
                "$%.2f",
                price
        );
    }
}
