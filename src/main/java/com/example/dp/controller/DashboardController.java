package com.example.dp.controller;

import com.example.dp.dao.MovieDAO;
import com.example.dp.model.Movie;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
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
import javafx.scene.layout.StackPane;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController
        implements Initializable {

    @FXML
    private HBox moviesContainer;

    @FXML private ComboBox<String> genreComboBox;
    @FXML private Label nowPlayingLabel;
    @FXML private Label comingSoonLabel;
    @FXML private StackPane modalOverlay;
    @FXML private VBox movieModal;
    @FXML private ImageView modalPoster;
    @FXML private Label modalTitle;
    @FXML private Label modalGenre;
    @FXML private Label modalDuration;
    @FXML private Label modalRating;
    @FXML private Label modalReleaseDate;
    @FXML private Label modalDescription;
    @FXML private Label modalActionTitle;
    @FXML private HBox modalShowtimesContainer;
    @FXML private Button modalActionButton;

    private String currentCategory = "NOW_SHOWING";
    private MovieDAO movieDAO = new MovieDAO();
    private ShowtimeDAO showtimeDAO = new ShowtimeDAO();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genreComboBox.getItems().addAll(movieDAO.getAllGenres());
        genreComboBox.setValue("All");

        // Key press listener for modal ESC
        if (moviesContainer != null) {
            moviesContainer.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    newScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                        if (event.getCode() == KeyCode.ESCAPE && modalOverlay.isVisible()) {
                            handleCloseModal();
                        }
                    });
                }
            });
        }

        loadMovies();
    }

    @FXML
    public void handleNowPlayingClick() {
        currentCategory = "NOW_SHOWING";
        nowPlayingLabel.getStyleClass().add("active-category");
        nowPlayingLabel.getStyleClass().remove("inactive-category");
        comingSoonLabel.getStyleClass().add("inactive-category");
        comingSoonLabel.getStyleClass().remove("active-category");
        loadMovies();
    }

    @FXML
    public void handleComingSoonClick() {
        currentCategory = "COMING_SOON";
        comingSoonLabel.getStyleClass().add("active-category");
        comingSoonLabel.getStyleClass().remove("inactive-category");
        nowPlayingLabel.getStyleClass().add("inactive-category");
        nowPlayingLabel.getStyleClass().remove("active-category");
        loadMovies();
    }

    @FXML
    public void handleCloseModal() {
        FadeTransition ft = new FadeTransition(Duration.millis(200), modalOverlay);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setOnFinished(e -> modalOverlay.setVisible(false));
        ft.play();
    }

    @FXML
    public void consumeClick(MouseEvent event) {
        event.consume();
    }

    public void loadMovies() {

        moviesContainer
                .getChildren()
                .clear();

        String selectedGenre = genreComboBox.getValue();
        List<Movie> movies = movieDAO.getMoviesByStatusAndGenre(currentCategory, selectedGenre);

        for(Movie movie : movies) {

            VBox card =
                    createMovieCard(movie);

            moviesContainer
                    .getChildren()
                    .add(card);
        }
    }

    @FXML
    public void handleGenreFilter() {

        loadMovies();
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

        content.getChildren().addAll(
                titleRow,
                genre
        );

        card.getChildren().addAll(
                poster,
                content
        );

        card.setOnMouseClicked(e -> showMovieModal(movie));

        return card;
    }

    private void showMovieModal(Movie movie) {
        modalTitle.setText(movie.getTitle() != null ? movie.getTitle() : "Unknown");
        modalGenre.setText(movie.getGenre() != null ? movie.getGenre() : "");
        modalDuration.setText(movie.getDurationMinutes() + " min");
        modalRating.setText(movie.getRating() != null ? movie.getRating() : "NR");
        modalReleaseDate.setText(movie.getReleaseDate() != null ? movie.getReleaseDate().toString() : "");
        modalDescription.setText(movie.getDescription() != null ? movie.getDescription() : "No description available.");

        try {
            String path = movie.getPosterPath();
            if (path != null && !path.trim().isEmpty()) {
                if (!path.startsWith("http") && !path.startsWith("file:")) {
                    path = "file:" + path;
                }
                modalPoster.setImage(new Image(path, true));
            } else {
                modalPoster.setImage(null);
            }
        } catch (Exception e) {
            modalPoster.setImage(null);
        }

        modalShowtimesContainer.getChildren().clear();

        if ("NOW_SHOWING".equals(currentCategory)) {
            modalActionTitle.setText("Available Showtimes");
            modalActionButton.setVisible(false);
            modalActionButton.setManaged(false);
            modalShowtimesContainer.setVisible(true);
            modalShowtimesContainer.setManaged(true);

            List<Showtime> showtimes = showtimeDAO.getShowtimesByMovie(movie.getId());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

            if (showtimes == null || showtimes.isEmpty()) {
                Label noShowtimes = new Label("No showtimes available.");
                noShowtimes.setStyle("-fx-text-fill: #9BA1B0;");
                modalShowtimesContainer.getChildren().add(noShowtimes);
            } else {
                for (Showtime showtime : showtimes) {
                    Button stBtn = new Button(showtime.getStartTime().format(formatter));
                    stBtn.getStyleClass().add("showtime-button");
                    stBtn.setOnAction(e -> navigateToBooking(movie, showtime, stBtn));
                    modalShowtimesContainer.getChildren().add(stBtn);
                }
            }
        } else {
            modalActionTitle.setText("Coming Soon");
            modalShowtimesContainer.setVisible(false);
            modalShowtimesContainer.setManaged(false);
            modalActionButton.setVisible(true);
            modalActionButton.setManaged(true);
            modalActionButton.setText("Watch Trailer");
            modalActionButton.setOnAction(e -> {
                System.out.println("Trailer clicked for: " + movie.getTitle());
            });
        }

        modalOverlay.setOpacity(0.0);
        modalOverlay.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(300), modalOverlay);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }

    private void navigateToBooking(Movie movie, Showtime showtime, Button sourceButton) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/booking.fxml"));
            Scene scene = new Scene(loader.load());
            BookingController controller = loader.getController();
            controller.setPreviousScene(sourceButton.getScene());
            controller.setMovie(movie, showtime);
            Stage stage = (Stage) sourceButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void handleMyBookings() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/view/mybookings.fxml"
                            )
                    );

            Scene scene =
                    new Scene(
                            loader.load()
                    );

            Stage stage =
                    (Stage) moviesContainer
                            .getScene()
                            .getWindow();

            stage.setScene(
                    scene
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}