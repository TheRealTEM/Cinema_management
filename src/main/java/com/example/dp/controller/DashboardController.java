package com.example.dp.controller;

import com.example.dp.dao.MovieDAO;
import com.example.dp.model.Movie;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.example.dp.dao.ShowtimeDAO;
import com.example.dp.model.Showtime;

import java.time.format.DateTimeFormatter;


import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController
        implements Initializable {

    @FXML
    private HBox moviesContainer;

    private MovieDAO movieDAO =
            new MovieDAO();

    private ShowtimeDAO showtimeDAO =
            new ShowtimeDAO();

    @Override
    public void initialize(
            URL url,
            ResourceBundle resourceBundle
    ) {

        loadMovies();
    }

    public void loadMovies() {

        List<Movie> movies =
                movieDAO.getAllMovies();

        for(Movie movie : movies) {

            VBox card =
                    createMovieCard(movie);

            moviesContainer
                    .getChildren()
                    .add(card);
        }
    }

    private VBox createMovieCard(
            Movie movie
    ) {

        VBox card =
                new VBox();

        card.getStyleClass()
                .add("movie-card");

        card.setPrefWidth(180);

        card.setSpacing(0);

        ImageView poster =
                new ImageView();

        poster.setFitWidth(180);

        poster.setFitHeight(250);

        poster.setPreserveRatio(false);

        try {
            String path = movie.getPosterPath();
            if (path != null && !path.trim().isEmpty()) {
                if (!path.startsWith("http") && !path.startsWith("file:")) {
                    path = "file:" + path;
                }
                Image image = new Image(path, true); // true for background loading
                poster.setImage(image);
            }
        } catch (Exception e) {
            System.err.println("Could not load poster for movie: " + movie.getTitle());
        }

        VBox content =
                new VBox();

        content.setSpacing(8);

        content.setStyle(
                "-fx-padding: 15;"
        );

        HBox titleRow =
                new HBox();

        titleRow.setSpacing(10);

        Label title =
                new Label(
                        movie.getTitle()
                );

        title.getStyleClass()
                .add("movie-title");

        Label duration =
                new Label(
                        movie.getDurationMinutes()
                                + "min"
                );

        duration.getStyleClass()
                .add("movie-duration");

        titleRow.getChildren().addAll(
                title,
                duration
        );

        Label genre =
                new Label(
                        movie.getGenre()
                );

        genre.getStyleClass()
                .add("movie-genre");

        Label nextShow =
                new Label(
                        "Next Show"
                );

        nextShow.getStyleClass()
                .add("next-show");



        VBox showtimesBox =
                new VBox();

        showtimesBox.setSpacing(6);

        List<Showtime> showtimes =
                showtimeDAO.getShowtimesByMovie(
                        movie.getId()
                );

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "hh:mm a"
                );

        for(Showtime showtime : showtimes) {

            Button showtimeButton =
                    new Button(
                            showtime.getStartTime()
                                    .format(formatter)
                    );

            showtimeButton.getStyleClass()
                    .add("showtime-button");

            showtimeButton.setOnAction(e -> {

                try {

                    FXMLLoader loader =
                            new FXMLLoader(
                                    getClass().getResource(
                                            "/view/booking.fxml"
                                    )
                            );

                    Scene scene =
                            new Scene(loader.load());

                    BookingController controller =
                            loader.getController();

                    controller.setMovie(
                            movie,
                            showtime
                    );

                    Stage stage =
                            (Stage) showtimeButton
                                    .getScene()
                                    .getWindow();

                    stage.setScene(scene);

                } catch (Exception ex) {

                    ex.printStackTrace();
                }
            });

            showtimesBox.getChildren()
                    .add(showtimeButton);
        }


        content.getChildren().addAll(
                titleRow,
                genre,
                nextShow,
                showtimesBox

        );

        card.getChildren().addAll(
                poster,
                content
        );

        return card;
    }
}