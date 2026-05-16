package com.example.dp.controller;

import com.example.dp.dao.MovieDAO;
import com.example.dp.model.Movie;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;

public class AdminMovieDialogController {

    @FXML
    private TextField titleField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextField genreField;

    @FXML
    private TextField durationField;

    @FXML
    private TextField ratingField;

    @FXML
    private TextField languageField;

    @FXML
    private DatePicker releaseDateField;

    @FXML
    private TextField posterPathField;

    @FXML
    private ComboBox<String> statusCombo;

    @FXML
    private Button saveButton;

    @FXML
    private Button deleteButton;

    private MovieDAO movieDAO;
    private Movie currentMovie;
    private boolean isEditMode = false;

    @FXML
    public void initialize() {
        movieDAO = new MovieDAO();
        statusCombo.setItems(FXCollections.observableArrayList("NOW_SHOWING", "COMING_SOON"));
        statusCombo.setValue("COMING_SOON");
    }

    public void setMovie(Movie movie) {
        this.currentMovie = movie;
        this.isEditMode = true;
        saveButton.setText("Update Movie");

        deleteButton.setVisible(true);
        deleteButton.setManaged(true);

        titleField.setText(movie.getTitle());
        descriptionArea.setText(movie.getDescription());
        genreField.setText(movie.getGenre());
        durationField.setText(String.valueOf(movie.getDurationMinutes()));
        ratingField.setText(movie.getRating());
        languageField.setText(movie.getLanguage());
        releaseDateField.setValue(movie.getReleaseDate());
        posterPathField.setText(movie.getPosterPath() != null ? movie.getPosterPath() : "");
        statusCombo.setValue(movie.getStatus());
    }

    @FXML
    public void handleSave() {
        try {
            String title = titleField.getText().trim();
            String description = descriptionArea.getText().trim();
            String genre = genreField.getText().trim();
            int duration = Integer.parseInt(durationField.getText().trim());
            String rating = ratingField.getText().trim();
            String language = languageField.getText().trim();
            LocalDate releaseDate = releaseDateField.getValue();
            String posterPath = posterPathField.getText().trim();
            String status = statusCombo.getValue();

            if (title.isEmpty() || releaseDate == null) {
                showError("Please fill in all required fields");
                return;
            }

            if (isEditMode) {
                currentMovie.setTitle(title);
                currentMovie.setDescription(description);
                currentMovie.setGenre(genre);
                currentMovie.setDurationMinutes(duration);
                currentMovie.setRating(rating);
                currentMovie.setLanguage(language);
                currentMovie.setReleaseDate(releaseDate);
                currentMovie.setPosterPath(posterPath);
                currentMovie.setStatus(status);

                movieDAO.updateMovie(currentMovie);
            } else {
                Movie movie = new Movie.Builder()
                        .setTitle(title)
                        .setDescription(description)
                        .setGenre(genre)
                        .setDurationMinutes(duration)
                        .setRating(rating)
                        .setLanguage(language)
                        .setReleaseDate(releaseDate)
                        .setPosterPath(posterPath)
                        .setStatus(status)
                        .build();

                movieDAO.addMovie(movie);
            }

            closeDialog();
        } catch (Exception e) {
            showError("Error saving movie: " + e.getMessage());
        }
    }

    @FXML
    public void handleCancel() {
        closeDialog();
    }

    @FXML
    public void handleDelete() {
        if (currentMovie == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Movie");
        confirm.setHeaderText("Delete \"" + currentMovie.getTitle() + "\"?");
        confirm.setContentText("This action cannot be undone.");

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                movieDAO.deleteMovie(currentMovie.getId());
                closeDialog();
            }
        });
    }

    @FXML
    public void handleSelectImage() {
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.setTitle("Select Poster Image");
        fileChooser.getExtensionFilters().add(
            new javafx.stage.FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        java.io.File file = fileChooser.showOpenDialog(titleField.getScene().getWindow());
        if (file != null) {
            posterPathField.setText(file.getAbsolutePath());
        }
    }

    private void closeDialog() {
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}

