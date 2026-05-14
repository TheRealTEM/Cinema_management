package com.example.dp.dao;

import com.example.dp.database.DatabaseConnection;
import com.example.dp.model.BookingShowtime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class ShowtimeDAO {

    private final Connection connection;

    public ShowtimeDAO() {
        connection =
                DatabaseConnection
                        .getInstance()
                        .getConnection();
    }

    public BookingShowtime getNextAvailableShowtimeByMovieId(
            int movieId
    ) {
        BookingShowtime showtime =
                findShowtime(movieId, true);

        if(showtime == null) {
            showtime =
                    findShowtime(movieId, false);
        }

        return showtime;
    }

    private BookingShowtime findShowtime(
            int movieId,
            boolean futureOnly
    ) {
        String query =
                "SELECT " +
                        "showtimes.id, " +
                        "showtimes.start_time, " +
                        "showtimes.end_time, " +
                        "showtimes.base_price, " +
                        "cinema_halls.hall_name, " +
                        "cinema_halls.screen_type " +
                "FROM showtimes " +
                "JOIN cinema_halls " +
                        "ON cinema_halls.id = showtimes.hall_id " +
                "WHERE showtimes.movie_id = ? " +
                        "AND showtimes.status = 'AVAILABLE' ";

        if(futureOnly) {
            query +=
                    "AND showtimes.start_time >= NOW() ";
        }

        query +=
                "ORDER BY showtimes.start_time ASC " +
                "LIMIT 1";

        try(PreparedStatement statement =
                    connection.prepareStatement(query)) {

            statement.setInt(1, movieId);

            try(ResultSet resultSet =
                        statement.executeQuery()) {

                if(resultSet.next()) {
                    return mapBookingShowtime(resultSet);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private BookingShowtime mapBookingShowtime(
            ResultSet resultSet
    ) throws Exception {
        BookingShowtime showtime =
                new BookingShowtime();

        showtime.setId(
                resultSet.getInt("id")
        );

        showtime.setHallName(
                resultSet.getString("hall_name")
        );

        showtime.setScreenType(
                resultSet.getString("screen_type")
        );

        showtime.setStartTime(
                toLocalDateTime(
                        resultSet.getTimestamp("start_time")
                )
        );

        showtime.setEndTime(
                toLocalDateTime(
                        resultSet.getTimestamp("end_time")
                )
        );

        showtime.setBasePrice(
                resultSet.getDouble("base_price")
        );

        return showtime;
    }

    private java.time.LocalDateTime toLocalDateTime(
            Timestamp timestamp
    ) {
        if(timestamp == null) {
            return null;
        }

        return timestamp.toLocalDateTime();
    }
}
