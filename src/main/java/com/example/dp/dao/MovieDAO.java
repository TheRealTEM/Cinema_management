package com.example.dp.dao;

import com.example.dp.database.DatabaseConnection;
import com.example.dp.model.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

public class MovieDAO {

    private Connection connection;

    public MovieDAO() {

        connection =
                DatabaseConnection
                        .getInstance()
                        .getConnection();
    }

    public List<Movie> getAllMovies() {

        List<Movie> movies =
                new ArrayList<>();

        String query =
                "SELECT * FROM movies";

        try {

            PreparedStatement statement =
                    connection.prepareStatement(query);

            ResultSet resultSet =
                    statement.executeQuery();

            while(resultSet.next()) {

                Movie movie =
                        new Movie();

                movie.setId(
                        resultSet.getInt("id")
                );

                movie.setTitle(
                        resultSet.getString("title")
                );

                movie.setDescription(
                        resultSet.getString("description")
                );

                movie.setGenre(
                        resultSet.getString("genre")
                );

                movie.setDurationMinutes(
                        resultSet.getInt(
                                "duration_minutes"
                        )
                );

                movie.setRating(
                        resultSet.getString("rating")
                );

                movie.setLanguage(
                        resultSet.getString("language")
                );

                movie.setReleaseDate(
                        resultSet.getDate(
                                "release_date"
                        ).toLocalDate()
                );

                movie.setPosterPath(
                        resultSet.getString(
                                "poster_path"
                        )
                );

                movie.setStatus(
                        resultSet.getString("status")
                );

                movies.add(movie);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }

    public List<String> getAllGenres() {

        List<String> genres =
                new ArrayList<>();

        String query =
                "SELECT DISTINCT genre FROM movies";

        try {

            PreparedStatement statement =
                    connection.prepareStatement(
                            query
                    );

            ResultSet resultSet =
                    statement.executeQuery();

            genres.add("All");

            while(resultSet.next()) {

                genres.add(
                        resultSet.getString(
                                "genre"
                        )
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return genres;
    }

    public List<Movie> getMoviesByGenre(
            String genre
    ) {

        List<Movie> movies =
                new ArrayList<>();

        String query =
                "SELECT * FROM movies " +
                        "WHERE genre = ?";

        try {

            PreparedStatement statement =
                    connection.prepareStatement(
                            query
                    );

            statement.setString(
                    1,
                    genre
            );

            ResultSet resultSet =
                    statement.executeQuery();

            while(resultSet.next()) {

                Movie movie =
                        new Movie();

                movie.setId(
                        resultSet.getInt(
                                "id"
                        )
                );

                movie.setTitle(
                        resultSet.getString(
                                "title"
                        )
                );

                movie.setGenre(
                        resultSet.getString(
                                "genre"
                        )
                );

                movie.setDurationMinutes(
                        resultSet.getInt(
                                "duration_minutes"
                        )
                );

                movie.setPosterPath(
                        resultSet.getString(
                                "poster_path"
                        )
                );

                movies.add(
                        movie
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return movies;
    }
}