package com.evaluation.movies.application.ports.outbound;

import com.evaluation.movies.domain.model.Movie;

import java.util.List;

public interface SearchMoviesPort {
    List<Movie> searchMovies(String title, Integer year);
}
