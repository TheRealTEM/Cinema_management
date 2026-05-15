package com.example.dp.dao;

import com.example.dp.database.DatabaseConnection;
import com.example.dp.model.Showtime;
import com.example.dp.model.BookingShowtime;

import java.math.BigDecimal;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

public class ShowtimeDAO {

    private Connection connection;

    public ShowtimeDAO() {

        connection =
                DatabaseConnection
                        .getInstance()
                        .getConnection();
    }

    

    public List<Showtime> getShowtimesByMovie(
            int movieId
    ) {

        List<Showtime> showtimes =
                new ArrayList<>();

        String query =
                "SELECT * FROM showtimes " +
                        "WHERE movie_id = ? " +
                        "AND status = 'AVAILABLE'";

        try {

            PreparedStatement statement =
                    connection.prepareStatement(
                            query
                    );

            statement.setInt(
                    1,
                    movieId
            );

            ResultSet resultSet =
                    statement.executeQuery();

            while(resultSet.next()) {

                Showtime showtime =
                        new Showtime();

                showtime.setId(
                        resultSet.getInt("id")
                );

                showtime.setMovieId(
                        resultSet.getInt("movie_id")
                );

                showtime.setHallId(
                        resultSet.getInt("hall_id")
                );

                showtime.setStartTime(
                        resultSet.getTimestamp(
                                "start_time"
                        ).toLocalDateTime()
                );

                showtime.setEndTime(
                        resultSet.getTimestamp(
                                "end_time"
                        ).toLocalDateTime()
                );

                showtime.setBasePrice(
                        resultSet.getBigDecimal(
                                "base_price"
                        )
                );

                showtime.setStatus(
                        resultSet.getString(
                                "status"
                        )
                );

                showtimes.add(showtime);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return showtimes;
    }

    public BookingShowtime
    getNextAvailableShowtimeByMovieId(
            int movieId
    ) {

        String query =
                "SELECT " +
                        "showtimes.id, " +
                        "cinema_halls.name AS hall_name, " +
                        "cinema_halls.screen_type, " +
                        "showtimes.start_time, " +
                        "showtimes.end_time, " +
                        "showtimes.base_price " +
                        "FROM showtimes " +
                        "JOIN cinema_halls " +
                        "ON showtimes.hall_id = cinema_halls.id " +
                        "WHERE showtimes.movie_id = ? " +
                        "AND showtimes.status = 'AVAILABLE' " +
                        "ORDER BY showtimes.start_time ASC " +
                        "LIMIT 1";

        try {

            PreparedStatement statement =
                    connection.prepareStatement(
                            query
                    );

            statement.setInt(
                    1,
                    movieId
            );

            ResultSet resultSet =
                    statement.executeQuery();

            if(resultSet.next()) {

                BookingShowtime showtime =
                        new BookingShowtime();

                showtime.setId(
                        resultSet.getInt("id")
                );

                showtime.setHallName(
                        resultSet.getString(
                                "hall_name"
                        )
                );

                showtime.setScreenType(
                        resultSet.getString(
                                "screen_type"
                        )
                );

                showtime.setStartTime(
                        resultSet.getTimestamp(
                                "start_time"
                        ).toLocalDateTime()
                );

                showtime.setEndTime(
                        resultSet.getTimestamp(
                                "end_time"
                        ).toLocalDateTime()
                );

                showtime.setBasePrice(
                        resultSet.getDouble(
                                "base_price"
                        )
                );

                return showtime;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }


}