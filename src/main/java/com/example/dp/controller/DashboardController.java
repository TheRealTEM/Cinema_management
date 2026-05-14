package com.example.dp.controller;

import com.example.dp.dao.MovieDAO;
import com.example.dp.model.Movie;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;



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

        Image image =
                new Image(
                        movie.getPosterPath()
                );

        poster.setImage(image);

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

        Label showtime =
                new Label(
                        "00:00 Screen #"
                );

        showtime.getStyleClass()
                .add("showtime");

        Button button =
                new Button(
                        "Book now"
                );

        button.setOnAction(e -> {

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

                controller.setMovie(movie);

                Stage stage =
                        (Stage) button
                                .getScene()
                                .getWindow();

                stage.setScene(scene);

            } catch (Exception ex) {

                ex.printStackTrace();
            }
        });

        button.getStyleClass()
                .add("book-button");

        HBox buttonBox =
                new HBox(button);

        buttonBox.setStyle(
                "-fx-alignment: center-right;"
        );

        content.getChildren().addAll(
                titleRow,
                genre,
                nextShow,
                showtime,
                buttonBox
        );

        card.getChildren().addAll(
                poster,
                content
        );

        return card;
    }
}