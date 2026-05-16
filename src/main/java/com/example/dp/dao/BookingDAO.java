package com.example.dp.dao;

import com.example.dp.database.DatabaseConnection;
import com.example.dp.model.Booking;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    private final Connection connection =
            DatabaseConnection
                    .getInstance()
                    .getConnection();

    public int createBooking(
            int userId,
            int showtimeId,
            double totalAmount
    ) {

        String query =
                "INSERT INTO bookings " +
                        "(user_id, showtime_id, total_amount, booking_status) " +
                        "VALUES (?, ?, ?, 'CONFIRMED')";

        try {

            PreparedStatement statement =
                    connection.prepareStatement(
                            query,
                            PreparedStatement.RETURN_GENERATED_KEYS
                    );

            statement.setInt(
                    1,
                    userId
            );

            statement.setInt(
                    2,
                    showtimeId
            );

            statement.setDouble(
                    3,
                    totalAmount
            );

            statement.executeUpdate();

            ResultSet resultSet =
                    statement.getGeneratedKeys();

            if (resultSet.next()) {

                return resultSet.getInt(1);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return -1;
    }

    public void saveBookedSeat(
            int bookingId,
            int showtimeId,
            int seatId
    ) {

        String query =
                "INSERT INTO booking_seats " +
                        "(booking_id, showtime_id, seat_id) " +
                        "VALUES (?, ?, ?)";

        try {

            PreparedStatement statement =
                    connection.prepareStatement(
                            query
                    );

            statement.setInt(
                    1,
                    bookingId
            );

            statement.setInt(
                    2,
                    showtimeId
            );

            statement.setInt(
                    3,
                    seatId
            );

            statement.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public List<String> getBookedSeats(
            int showtimeId
    ) {

        List<String> bookedSeats =
                new ArrayList<>();

        String query =
                "SELECT s.seat_row, s.seat_number " +
                        "FROM booking_seats bs " +
                        "JOIN seats s ON bs.seat_id = s.id " +
                        "WHERE bs.showtime_id = ?";

        try {

            PreparedStatement statement =
                    connection.prepareStatement(
                            query
                    );

            statement.setInt(
                    1,
                    showtimeId
            );

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                String seat =
                        resultSet.getString(
                                "seat_row"
                        )
                                +
                                resultSet.getInt(
                                        "seat_number"
                                );

                bookedSeats.add(
                        seat
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return bookedSeats;
    }

    public List<String[]> getUserBookings(
            int userId
    ) {

        List<String[]> bookings =
                new ArrayList<>();

        String query =
                "SELECT " +
                        "m.title, " +
                        "m.poster_path, " +
                        "GROUP_CONCAT(" +
                        "CONCAT(s.seat_row, s.seat_number) " +
                        "SEPARATOR ', '" +
                        ") AS seats, " +
                        "b.total_amount " +
                        "FROM bookings b " +
                        "JOIN showtimes st " +
                        "ON b.showtime_id = st.id " +
                        "JOIN movies m " +
                        "ON st.movie_id = m.id " +
                        "JOIN booking_seats bs " +
                        "ON b.id = bs.booking_id " +
                        "JOIN seats s " +
                        "ON bs.seat_id = s.id " +
                        "WHERE b.user_id = ? " +
                        "GROUP BY b.id";

        try {

            PreparedStatement statement =
                    connection.prepareStatement(
                            query
                    );

            statement.setInt(
                    1,
                    userId
            );

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                String[] booking =
                        new String[4];

                booking[0] =
                        resultSet.getString(
                                "title"
                        );

                booking[1] =
                        resultSet.getString(
                                "poster_path"
                        );

                booking[2] =
                        resultSet.getString(
                                "seats"
                        );

                booking[3] =
                        resultSet.getString(
                                "total_amount"
                        );

                bookings.add(
                        booking
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return bookings;
    }

    public int getTotalBookingsCount() {
        String query = "SELECT COUNT(*) as count FROM bookings";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getPendingBookingsCount() {
        String query = "SELECT COUNT(*) as count FROM bookings WHERE booking_status = 'PENDING'";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Booking> getRecentBookings(int limit) {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings ORDER BY booking_date DESC LIMIT ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, limit);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bookings.add(new Booking.Builder()
                        .setId(resultSet.getInt("id"))
                        .setUserId(resultSet.getInt("user_id"))
                        .setShowtimeId(resultSet.getInt("showtime_id"))
                        .setBookingDate(resultSet.getTimestamp("booking_date"))
                        .setTotalAmount(resultSet.getDouble("total_amount"))
                        .setBookingStatus(resultSet.getString("booking_status"))
                        .build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bookings.add(new Booking.Builder()
                        .setId(resultSet.getInt("id"))
                        .setUserId(resultSet.getInt("user_id"))
                        .setShowtimeId(resultSet.getInt("showtime_id"))
                        .setBookingDate(resultSet.getTimestamp("booking_date"))
                        .setTotalAmount(resultSet.getDouble("total_amount"))
                        .setBookingStatus(resultSet.getString("booking_status"))
                        .build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public List<Booking> getBookingsByStatus(String status) {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings WHERE booking_status = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, status);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bookings.add(new Booking.Builder()
                        .setId(resultSet.getInt("id"))
                        .setUserId(resultSet.getInt("user_id"))
                        .setShowtimeId(resultSet.getInt("showtime_id"))
                        .setBookingDate(resultSet.getTimestamp("booking_date"))
                        .setTotalAmount(resultSet.getDouble("total_amount"))
                        .setBookingStatus(resultSet.getString("booking_status"))
                        .build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookings;
    }
}