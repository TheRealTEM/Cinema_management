package com.example.dp.proxy;

import com.example.dp.dao.MovieDAO;
import com.example.dp.model.Movie;
import com.example.dp.service.MovieService;

import java.util.List;

public class CachedMovieProxy implements MovieService {
    private MovieDAO movieDAO;
    private List<Movie> movieCache;

    public CachedMovieProxy() {
        this.movieDAO = new MovieDAO();
    }

    @Override
    public List<Movie> getAllMovies() {
        if (movieCache == null) {
            System.out.println("[PROXY] Cache empty. Fetching from database...");
            movieCache = movieDAO.getAllMovies();
        } else {
            System.out.println("[PROXY] Returning cached movies.");
        }
        return movieCache;
    }

    @Override
    public int getActiveMoviesCount() {
        // We can either cache this or delegating directly
        return movieDAO.getActiveMoviesCount();
    }

    @Override
    public boolean deleteMovie(int id) {
        boolean success = movieDAO.deleteMovie(id);
        if (success) {
            System.out.println("[PROXY] Movie deleted. Invalidating cache.");
            movieCache = null; // Invalidate cache on modification
        }
        return success;
    }

    public void clearCache() {
        movieCache = null;
    }
}
