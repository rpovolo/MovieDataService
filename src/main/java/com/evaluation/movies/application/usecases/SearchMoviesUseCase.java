package com.evaluation.movies.application.usecases;

import com.evaluation.movies.domain.model.Movie;

import java.util.List;

public interface SearchMoviesUseCase {
    List<Movie> searchMovies(String title, Integer year);

}
