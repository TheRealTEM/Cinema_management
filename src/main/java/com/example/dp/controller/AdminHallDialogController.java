package com.example.dp.controller;

import com.example.dp.dao.MovieDAO;
import com.example.dp.dao.ShowtimeDAO;
import com.example.dp.model.Movie;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

import java.math.BigDecimal;

import java.net.URL;

import java.time.LocalDateTime;

import java.util.ResourceBundle;

public class AdminHallDialogController
        implements Initializable {

    @FXML
    private ComboBox<Movie> movieComboBox;

    @FXML
    private ComboBox<String> hallComboBox;

    @FXML
    private TextField showtimeField;

    @FXML
    private TextField priceField;

    private final MovieDAO movieDAO =
            new MovieDAO();

    private final ShowtimeDAO showtimeDAO =
            new ShowtimeDAO();

    @Override
    public void initialize(
            URL url,
            ResourceBundle resourceBundle
    ) {

        movieComboBox.getItems().addAll(
                movieDAO.getAllMovies()
        );

        hallComboBox.getItems().addAll(
                showtimeDAO.getAllHallNames()
        );
    }

    @FXML
    public void handleCreateShowtime() {

        try {

            Movie movie =
                    movieComboBox.getValue();

            String hall =
                    hallComboBox.getValue();

            LocalDateTime startTime =
                    LocalDateTime.parse(
                            showtimeField.getText()
                    );

            LocalDateTime endTime =
                    startTime.plusHours(2);

            BigDecimal price =
                    BigDecimal.valueOf(
                            Double.parseDouble(
                                    priceField.getText()
                            )
                    );

            int hallId =
                    showtimeDAO.getHallIdByName(
                            hall
                    );

            showtimeDAO.createShowtime(
                    movie.getId(),
                    hallId,
                    startTime,
                    endTime,
                    price
            );

            System.out.println(
                    "Showtime created successfully"
            );

            handleClear();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @FXML
    public void handleClear() {

        movieComboBox.setValue(null);

        hallComboBox.setValue(null);

        showtimeField.clear();

        priceField.clear();
    }

    @FXML
    public void handleCancel() {

        Stage stage =
                (Stage) movieComboBox
                        .getScene()
                        .getWindow();

        stage.close();
    }
}