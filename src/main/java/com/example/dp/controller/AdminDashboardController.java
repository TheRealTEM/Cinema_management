package com.example.dp.controller;

import com.example.dp.dao.BookingDAO;
import com.example.dp.dao.MovieDAO;
import com.example.dp.dao.PaymentDAO;
import com.example.dp.dao.ShowtimeDAO;
import com.example.dp.model.Booking;
import com.example.dp.model.Movie;
import com.example.dp.model.Payment;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.time.LocalDateTime;

import java.math.BigDecimal;

public class AdminDashboardController implements Initializable {

    @FXML
    private Label totalBookingsLabel;

    @FXML
    private Label totalRevenueLabel;

    @FXML
    private Label activeMoviesLabel;

    @FXML
    private Label pendingBookingsLabel;

    @FXML
    private Label currentUserLabel;

    @FXML
    private TableView<Booking> recentBookingsTable;

    @FXML
    private TableView<Movie> moviesTable;

    @FXML
    private TableView<Booking> bookingsTable;

    @FXML
    private TableView<Payment> paymentsTable;

    @FXML
    private ComboBox<String> bookingStatusFilter;

    @FXML
    private ComboBox<String> paymentStatusFilter;

    @FXML
    private ComboBox<String> paymentMethodFilter;

    @FXML
    private Label cardPaymentsLabel;

    @FXML
    private Label cashPaymentsLabel;

    @FXML
    private Label walletPaymentsLabel;

    @FXML
    private VBox overviewSection;

    @FXML
    private VBox moviesSection;

    @FXML
    private VBox bookingsSection;

    @FXML
    private VBox paymentsSection;

    @FXML
    private Label overviewNav;

    @FXML
    private Label moviesNav;

    @FXML
    private Label bookingsNav;

    @FXML
    private Label paymentsNav;

    private MovieDAO movieDAO;

    private BookingDAO bookingDAO;

    private PaymentDAO paymentDAO;

    private ShowtimeDAO showtimeDAO;

    @FXML
    private VBox hallSection;

    @FXML
    private Label hallsNav;

    @FXML
    private ComboBox<Movie> movieComboBox;

    @FXML
    private ComboBox<String> hallComboBox;

    @FXML
    private TextField showtimeField;

    @FXML
    private TextField priceField;

    @Override
    public void initialize(
            URL url,
            ResourceBundle resourceBundle
    ) {
        movieDAO = new MovieDAO();
        bookingDAO = new BookingDAO();
        paymentDAO = new PaymentDAO();
        showtimeDAO = new ShowtimeDAO();

        overviewSection.managedProperty().bind(overviewSection.visibleProperty());
        moviesSection.managedProperty().bind(moviesSection.visibleProperty());
        bookingsSection.managedProperty().bind(bookingsSection.visibleProperty());
        paymentsSection.managedProperty().bind(paymentsSection.visibleProperty());
        hallSection.managedProperty()
                .bind(hallSection.visibleProperty());

        setupTableColumns();
        setupNavigation();
        setupFilters();
        loadOverviewData();
    }

    @SuppressWarnings("unchecked")
    private void setupTableColumns() {
        TableColumn<Movie, String> movieTitle   = (TableColumn<Movie, String>) moviesTable.getColumns().get(0);
        TableColumn<Movie, String> movieGenre   = (TableColumn<Movie, String>) moviesTable.getColumns().get(1);
        TableColumn<Movie, Integer> movieDur    = (TableColumn<Movie, Integer>) moviesTable.getColumns().get(2);
        TableColumn<Movie, String> movieStatus  = (TableColumn<Movie, String>) moviesTable.getColumns().get(3);
        TableColumn<Movie, String> movieDate    = (TableColumn<Movie, String>) moviesTable.getColumns().get(4);
        movieTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        movieGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        movieDur.setCellValueFactory(new PropertyValueFactory<>("durationMinutes"));
        movieStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        movieDate.setCellValueFactory(d -> new SimpleStringProperty(
                d.getValue().getReleaseDate() != null ? d.getValue().getReleaseDate().toString() : ""));

        TableColumn<Booking, Integer> rbId     = (TableColumn<Booking, Integer>) recentBookingsTable.getColumns().get(0);
        TableColumn<Booking, Integer> rbUser   = (TableColumn<Booking, Integer>) recentBookingsTable.getColumns().get(1);
        TableColumn<Booking, Integer> rbShow   = (TableColumn<Booking, Integer>) recentBookingsTable.getColumns().get(2);
        TableColumn<Booking, Double>  rbAmt    = (TableColumn<Booking, Double>)  recentBookingsTable.getColumns().get(3);
        TableColumn<Booking, String>  rbStatus = (TableColumn<Booking, String>)  recentBookingsTable.getColumns().get(4);
        TableColumn<Booking, String>  rbDate   = (TableColumn<Booking, String>)  recentBookingsTable.getColumns().get(5);
        rbId.setCellValueFactory(new PropertyValueFactory<>("id"));
        rbUser.setCellValueFactory(new PropertyValueFactory<>("userId"));
        rbShow.setCellValueFactory(new PropertyValueFactory<>("showtimeId"));
        rbAmt.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        rbStatus.setCellValueFactory(new PropertyValueFactory<>("bookingStatus"));
        rbDate.setCellValueFactory(b -> new SimpleStringProperty(
                b.getValue().getBookingDate() != null ? b.getValue().getBookingDate().toString() : ""));

        // Bookings management table
        TableColumn<Booking, Integer> bkId     = (TableColumn<Booking, Integer>) bookingsTable.getColumns().get(0);
        TableColumn<Booking, Integer> bkUser   = (TableColumn<Booking, Integer>) bookingsTable.getColumns().get(1);
        TableColumn<Booking, Integer> bkShow   = (TableColumn<Booking, Integer>) bookingsTable.getColumns().get(2);
        TableColumn<Booking, Integer> bkShowt  = (TableColumn<Booking, Integer>) bookingsTable.getColumns().get(3);
        TableColumn<Booking, Double>  bkAmt    = (TableColumn<Booking, Double>)  bookingsTable.getColumns().get(4);
        TableColumn<Booking, String>  bkStatus = (TableColumn<Booking, String>)  bookingsTable.getColumns().get(5);
        bkId.setCellValueFactory(new PropertyValueFactory<>("id"));
        bkUser.setCellValueFactory(new PropertyValueFactory<>("userId"));
        bkShow.setCellValueFactory(new PropertyValueFactory<>("showtimeId"));
        bkShowt.setCellValueFactory(new PropertyValueFactory<>("showtimeId"));
        bkAmt.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        bkStatus.setCellValueFactory(new PropertyValueFactory<>("bookingStatus"));

        // Payments table
        TableColumn<Payment, Integer> pyId     = (TableColumn<Payment, Integer>) paymentsTable.getColumns().get(0);
        TableColumn<Payment, Integer> pyBkId   = (TableColumn<Payment, Integer>) paymentsTable.getColumns().get(1);
        TableColumn<Payment, Double>  pyAmt    = (TableColumn<Payment, Double>)  paymentsTable.getColumns().get(2);
        TableColumn<Payment, String>  pyMethod = (TableColumn<Payment, String>)  paymentsTable.getColumns().get(3);
        TableColumn<Payment, String>  pyStatus = (TableColumn<Payment, String>)  paymentsTable.getColumns().get(4);
        TableColumn<Payment, String>  pyDate   = (TableColumn<Payment, String>)  paymentsTable.getColumns().get(5);
        pyId.setCellValueFactory(new PropertyValueFactory<>("id"));
        pyBkId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        pyAmt.setCellValueFactory(new PropertyValueFactory<>("amount"));
        pyMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        pyStatus.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
        pyDate.setCellValueFactory(p -> new SimpleStringProperty(
                p.getValue().getPaymentDate() != null ? p.getValue().getPaymentDate().toString() : ""));
    }

    private void setupNavigation() {
        overviewNav.setOnMouseClicked(e -> showOverview());
        moviesNav.setOnMouseClicked(e -> showMovies());
        bookingsNav.setOnMouseClicked(e -> showBookings());
        paymentsNav.setOnMouseClicked(e -> showPayments());
        hallsNav.setOnMouseClicked(
                e -> showHallManagement()
        );
    }

    private void setupFilters() {
        bookingStatusFilter.setItems(FXCollections.observableArrayList(
            "All Statuses",
            "PENDING",
            "CONFIRMED",
            "CANCELLED"
        ));
        bookingStatusFilter.setValue("All Statuses");
        bookingStatusFilter.setOnAction(e -> loadBookingsData());

        paymentStatusFilter.setItems(FXCollections.observableArrayList(
            "All Statuses",
            "PAID",
            "FAILED",
            "PENDING"
        ));
        paymentStatusFilter.setValue("All Statuses");
        paymentStatusFilter.setOnAction(e -> loadPaymentsData());

        paymentMethodFilter.setItems(FXCollections.observableArrayList(
            "All Methods",
            "CARD",
            "CASH",
            "WALLET"
        ));
        paymentMethodFilter.setValue("All Methods");
        paymentMethodFilter.setOnAction(e -> loadPaymentsData());
    }

    private void showOverview() {
        overviewSection.setVisible(true);
        moviesSection.setVisible(false);
        bookingsSection.setVisible(false);
        paymentsSection.setVisible(false);

        updateNavActive(overviewNav);
        loadOverviewData();
    }

    private void showMovies() {
        overviewSection.setVisible(false);
        moviesSection.setVisible(true);
        bookingsSection.setVisible(false);
        paymentsSection.setVisible(false);

        updateNavActive(moviesNav);
        loadMoviesData();
    }

    private void showBookings() {
        overviewSection.setVisible(false);
        moviesSection.setVisible(false);
        bookingsSection.setVisible(true);
        paymentsSection.setVisible(false);

        updateNavActive(bookingsNav);
        loadBookingsData();
    }

    private void showPayments() {
        overviewSection.setVisible(false);
        moviesSection.setVisible(false);
        bookingsSection.setVisible(false);
        paymentsSection.setVisible(true);

        updateNavActive(paymentsNav);
        loadPaymentsData();
    }

    private void updateNavActive(Label activeNav) {
        overviewNav.getStyleClass().removeAll("admin-nav-item-active");
        moviesNav.getStyleClass().removeAll("admin-nav-item-active");
        bookingsNav.getStyleClass().removeAll("admin-nav-item-active");
        paymentsNav.getStyleClass().removeAll("admin-nav-item-active");
        hallsNav.getStyleClass()
                .removeAll(
                        "admin-nav-item-active"
                );

        activeNav.getStyleClass().add("admin-nav-item-active");
    }

    private void loadOverviewData() {
        try {
            int totalBookings = bookingDAO.getTotalBookingsCount();
            double totalRevenue = paymentDAO.getTotalRevenuePaid();
            int activeMovies = movieDAO.getActiveMoviesCount();
            int pendingBookings = bookingDAO.getPendingBookingsCount();

            totalBookingsLabel.setText(String.valueOf(totalBookings));
            totalRevenueLabel.setText(String.format("$%.2f", totalRevenue));
            activeMoviesLabel.setText(String.valueOf(activeMovies));
            pendingBookingsLabel.setText(String.valueOf(pendingBookings));

            loadRecentBookings();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadRecentBookings() {
        try {
            List<Booking> bookings = bookingDAO.getRecentBookings(10);
            ObservableList<Booking> data = FXCollections.observableArrayList(bookings);
            recentBookingsTable.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void loadMoviesData() {
        try {
            System.out.println("DEBUG: Loading movies data from database...");
            List<Movie> movies = movieDAO.getAllMovies();
            System.out.println("DEBUG: Retrieved " + movies.size() + " movies from database");

            if (movies.isEmpty()) {
                System.out.println("WARNING: No movies found in database!");
                showInfo("No movies in database. Please add some movies first.");
            }

            ObservableList<Movie> data = FXCollections.observableArrayList(movies);
            moviesTable.setItems(data);
            System.out.println("DEBUG: Movies table populated successfully");
        } catch (Exception e) {
            System.err.println("ERROR: Failed to load movies");
            e.printStackTrace();
            showInfo("Error loading movies: " + e.getMessage());
        }
    }

    private void loadBookingsData() {
        try {
            String status = bookingStatusFilter.getValue();
            List<Booking> bookings;

            if ("All Statuses".equals(status)) {
                bookings = bookingDAO.getAllBookings();
            } else {
                bookings = bookingDAO.getBookingsByStatus(status);
            }

            ObservableList<Booking> data = FXCollections.observableArrayList(bookings);
            bookingsTable.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPaymentsData() {
        try {
            String status = paymentStatusFilter.getValue();
            String method = paymentMethodFilter.getValue();

            List<Payment> payments = paymentDAO.getPaymentsByFilters(status, method);
            ObservableList<Payment> data = FXCollections.observableArrayList(payments);
            paymentsTable.setItems(data);

            double cardTotal = paymentDAO.getTotalRevenueByMethod("CARD");
            double cashTotal = paymentDAO.getTotalRevenueByMethod("CASH");
            double walletTotal = paymentDAO.getTotalRevenueByMethod("WALLET");

            cardPaymentsLabel.setText(String.format("$%.2f", cardTotal));
            cashPaymentsLabel.setText(String.format("$%.2f", cashTotal));
            walletPaymentsLabel.setText(String.format("$%.2f", walletTotal));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddMovie() {
        openMovieDialog(null);
    }

    @FXML
    public void handleEditMovie() {
        Movie selected = moviesTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            openMovieDialog(selected);
        } else {
            showInfo("Please select a movie to edit.");
        }
    }

    @FXML
    public void handleDeleteMovie() {
        Movie selected = moviesTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showInfo("Please select a movie to delete.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Movie");
        confirm.setHeaderText("Delete \"" + selected.getTitle() + "\"?");
        confirm.setContentText("This action cannot be undone.");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                movieDAO.deleteMovie(selected.getId());
                loadMoviesData();
            }
        });
    }

    private void openMovieDialog(Movie movie) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/admin-movie-dialog.fxml")
            );
            Parent root = loader.load();
            AdminMovieDialogController controller = loader.getController();

            if (movie != null) {
                controller.setMovie(movie);
            }

            Stage stage = new Stage();
            stage.setTitle(movie == null ? "Add Movie" : "Edit Movie");
            stage.setScene(new Scene(root, 900, 820));
            stage.setMinWidth(750);
            stage.setMinHeight(700);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnHidden(e -> loadMoviesData());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleConfirmBooking() {
        Booking selected = bookingsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            System.out.println("Confirm Booking: " + selected.getId());
        }
    }

    @FXML
    public void handleCancelBooking() {
        Booking selected = bookingsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            System.out.println("Cancel Booking: " + selected.getId());
        }
    }

    private void showHallManagement() {

        overviewSection.setVisible(false);

        moviesSection.setVisible(false);

        bookingsSection.setVisible(false);

        paymentsSection.setVisible(false);

        hallSection.setVisible(true);

        updateNavActive(hallsNav);

        loadHallManagementData();
    }

    private void loadHallManagementData() {

        movieComboBox.getItems().clear();

        hallComboBox.getItems().clear();

        movieComboBox.getItems().addAll(
                movieDAO.getAllMovies()
        );

        hallComboBox.getItems().addAll(
                showtimeDAO.getAllHallNames()
        );
    }

    @FXML
    public void handleCreateShowtime() {

        Movie movie =
                movieComboBox.getValue();

        String hall =
                hallComboBox.getValue();

        String showtime =
                showtimeField.getText();

        String price =
                priceField.getText();

        if(movie == null ||
                hall == null ||
                showtime.isEmpty() ||
                price.isEmpty()) {

            showInfo(
                    "Please fill all fields"
            );

            return;
        }

        try {

            int hallId =
                    Integer.parseInt(
                            hall.replace(
                                    "Hall ",
                                    ""
                            )
                    );

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern(
                            "yyyy-M-d HH:mm:ss"
                    );

            LocalDateTime startTime =
                    LocalDateTime.parse(
                            showtime,
                            formatter
                    );

            LocalDateTime endTime =
                    startTime.plusHours(2);

            BigDecimal ticketPrice =
                    BigDecimal.valueOf(
                            Double.parseDouble(price)
                    );

            showtimeDAO.createShowtime(
                    movie.getId(),
                    hallId,
                    startTime,
                    endTime,
                    ticketPrice
            );

            showInfo(
                    "Showtime created successfully"
            );

            showtimeField.clear();

            priceField.clear();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}

