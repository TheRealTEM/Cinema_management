package com.example.dp.service;

import com.example.dp.model.Movie;
import java.util.List;

public interface MovieService {
    List<Movie> getAllMovies();
    int getActiveMoviesCount();
    boolean deleteMovie(int id);
}
