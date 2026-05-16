package com.example.dp.dao;

import com.example.dp.database.DatabaseConnection;
import com.example.dp.model.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import com.example.dp.service.MovieService;

public class MovieDAO implements MovieService {

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

            System.out.println("DEBUG: Executing query: " + query);

            while(resultSet.next()) {

                Movie movie = new Movie.Builder()
                        .setId(resultSet.getInt("id"))
                        .setTitle(resultSet.getString("title"))
                        .setDescription(resultSet.getString("description"))
                        .setGenre(resultSet.getString("genre"))
                        .setDurationMinutes(resultSet.getInt("duration_minutes"))
                        .setRating(resultSet.getString("rating"))
                        .setLanguage(resultSet.getString("language"))
                        .setReleaseDate(resultSet.getDate("release_date").toLocalDate())
                        .setPosterPath(resultSet.getString("poster_path"))
                        .setStatus(resultSet.getString("status"))
                        .build();

                System.out.println("DEBUG: Loaded movie: " + movie.getTitle());
                movies.add(movie);
            }

            System.out.println("DEBUG: Total movies loaded: " + movies.size());

        } catch (Exception e) {
            System.err.println("ERROR: Failed to load movies from database");
            e.printStackTrace();
        }

        return movies;
    }

    public Movie getMovieById(int movieId) {
        String query = "SELECT * FROM movies WHERE id = ?";

        try {
            PreparedStatement statement =
                    connection.prepareStatement(query);

            statement.setInt(1, movieId);

            ResultSet resultSet =
                    statement.executeQuery();

            if(resultSet.next()) {
                Movie movie = new Movie.Builder()
                        .setId(resultSet.getInt("id"))
                        .setTitle(resultSet.getString("title"))
                        .setDescription(resultSet.getString("description"))
                        .setGenre(resultSet.getString("genre"))
                        .setDurationMinutes(resultSet.getInt("duration_minutes"))
                        .setRating(resultSet.getString("rating"))
                        .setLanguage(resultSet.getString("language"))
                        .setReleaseDate(resultSet.getDate("release_date").toLocalDate())
                        .setPosterPath(resultSet.getString("poster_path"))
                        .setStatus(resultSet.getString("status"))
                        .setCreatedAt(resultSet.getTimestamp("created_at"))
                        .build();
                return movie;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public int getActiveMoviesCount() {
        String query = "SELECT COUNT(*) as count FROM movies WHERE status = 'NOW_SHOWING'";

        try {
            PreparedStatement statement =
                    connection.prepareStatement(query);

            ResultSet resultSet =
                    statement.executeQuery();

            if(resultSet.next()) {
                return resultSet.getInt("count");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public boolean updateMovieStatus(int movieId, String status) {
        String query = "UPDATE movies SET status = ? WHERE id = ?";

        try {
            PreparedStatement statement =
                    connection.prepareStatement(query);

            statement.setString(1, status);
            statement.setInt(2, movieId);

            int rows = statement.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean addMovie(Movie movie) {
        String query =
                "INSERT INTO movies (title, description, genre, duration_minutes, rating, language, release_date, poster_path, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement =
                    connection.prepareStatement(query);

            statement.setString(1, movie.getTitle());
            statement.setString(2, movie.getDescription());
            statement.setString(3, movie.getGenre());
            statement.setInt(4, movie.getDurationMinutes());
            statement.setString(5, movie.getRating());
            statement.setString(6, movie.getLanguage());
            statement.setDate(7, java.sql.Date.valueOf(movie.getReleaseDate()));
            statement.setString(8, movie.getPosterPath());
            statement.setString(9, movie.getStatus());

            int rows = statement.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateMovie(Movie movie) {
        String query =
                "UPDATE movies SET title = ?, description = ?, genre = ?, duration_minutes = ?, " +
                "rating = ?, language = ?, release_date = ?, poster_path = ?, status = ? WHERE id = ?";

        try {
            PreparedStatement statement =
                    connection.prepareStatement(query);

            statement.setString(1, movie.getTitle());
            statement.setString(2, movie.getDescription());
            statement.setString(3, movie.getGenre());
            statement.setInt(4, movie.getDurationMinutes());
            statement.setString(5, movie.getRating());
            statement.setString(6, movie.getLanguage());
            statement.setDate(7, java.sql.Date.valueOf(movie.getReleaseDate()));
            statement.setString(8, movie.getPosterPath());
            statement.setString(9, movie.getStatus());
            statement.setInt(10, movie.getId());

            int rows = statement.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteMovie(int movieId) {
        String query = "DELETE FROM movies WHERE id = ?";

        try {
            PreparedStatement statement =
                    connection.prepareStatement(query);

            statement.setInt(1, movieId);

            int rows = statement.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
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
                Movie movie = new Movie.Builder()
                        .setId(resultSet.getInt("id"))
                        .setTitle(resultSet.getString("title"))
                        .setDescription(resultSet.getString("description"))
                        .setGenre(resultSet.getString("genre"))
                        .setDurationMinutes(resultSet.getInt("duration_minutes"))
                        .setRating(resultSet.getString("rating"))
                        .setLanguage(resultSet.getString("language"))
                        .setReleaseDate(resultSet.getDate("release_date").toLocalDate())
                        .setPosterPath(resultSet.getString("poster_path"))
                        .setStatus(resultSet.getString("status"))
                        .build();
                movies.add(movie);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }

    public List<Movie> getMoviesByStatusAndGenre(String status, String genre) {
        List<Movie> movies = new ArrayList<>();
        String query;
        
        if (genre == null || genre.equals("All")) {
            query = "SELECT * FROM movies WHERE status = ?";
        } else {
            query = "SELECT * FROM movies WHERE status = ? AND genre = ?";
        }

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, status);
            if (genre != null && !genre.equals("All")) {
                statement.setString(2, genre);
            }

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Movie movie = new Movie.Builder()
                        .setId(resultSet.getInt("id"))
                        .setTitle(resultSet.getString("title"))
                        .setDescription(resultSet.getString("description"))
                        .setGenre(resultSet.getString("genre"))
                        .setDurationMinutes(resultSet.getInt("duration_minutes"))
                        .setRating(resultSet.getString("rating"))
                        .setLanguage(resultSet.getString("language"))
                        .setReleaseDate(resultSet.getDate("release_date").toLocalDate())
                        .setPosterPath(resultSet.getString("poster_path"))
                        .setStatus(resultSet.getString("status"))
                        .build();
                movies.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }
}