package com.example.dp.dao;

import com.example.dp.database.DatabaseConnection;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

            if(resultSet.next()) {

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

            while(resultSet.next()) {

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
}