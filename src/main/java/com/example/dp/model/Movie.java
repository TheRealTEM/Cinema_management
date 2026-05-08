package com.example.dp.model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Movie {

    private int id;

    private String title;

    private String description;

    private String genre;

    private int durationMinutes;

    private String rating;

    private String language;

    private LocalDate releaseDate;

    private String posterPath;

    private String status;

    private Timestamp createdAt;

    public Movie() {
    }

    public Movie(
            int id,
            String title,
            String description,
            String genre,
            int durationMinutes,
            String rating,
            String language,
            LocalDate releaseDate,
            String posterPath,
            String status,
            Timestamp createdAt
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.durationMinutes = durationMinutes;
        this.rating = rating;
        this.language = language;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return title;
    }
}