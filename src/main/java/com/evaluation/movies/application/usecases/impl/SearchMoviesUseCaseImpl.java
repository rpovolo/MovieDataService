package com.evaluation.movies.application.usecases.impl;

import com.evaluation.movies.application.ports.outbound.SearchMoviesPort;
import com.evaluation.movies.application.usecases.SearchMoviesUseCase;
import com.evaluation.movies.domain.model.Movie;

import java.util.List;

public class SearchMoviesUseCaseImpl implements SearchMoviesUseCase {
    private final SearchMoviesPort searchMoviesPort;

    /**
     * Constructs the use case implementation with the required search port dependency.
     *
     * @param searchMoviesPort the port responsible for performing the movie search
     */
    public SearchMoviesUseCaseImpl(SearchMoviesPort searchMoviesPort) {
        this.searchMoviesPort = searchMoviesPort;
    }

    /**
     * Searches for movies based on the provided title and/or year.
     *
     * <p>This method delegates the search operation to the port, maintaining
     * the separation of concerns between the domain logic and the infrastructure.</p>
     *
     * @param title the movie title (full or partial), may be {@code null}
     * @param year  the release year of the movie, may be {@code null}
     * @return a list of movies matching the given search criteria
     */
    @Override
    public List<Movie> searchMovies(String title, Integer year) {
        if (title == null && year == null) {
            throw new IllegalArgumentException("At least one of 'title' or 'year' must be provided");
        }
        return searchMoviesPort.searchMovies(title, year);
    }

}
