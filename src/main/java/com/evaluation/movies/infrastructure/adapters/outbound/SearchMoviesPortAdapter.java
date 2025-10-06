package com.evaluation.movies.infrastructure.adapters.outbound;

import com.evaluation.movies.application.ports.outbound.IndexMoviesPort;
import com.evaluation.movies.application.ports.outbound.SearchMoviesPort;
import com.evaluation.movies.domain.model.Movie;
import com.evaluation.movies.domain.repository.MovieRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchMoviesPortAdapter implements SearchMoviesPort {
    private final MovieRepositoryPort movieRepositoryPort;

    public SearchMoviesPortAdapter(MovieRepositoryPort movieRepositoryPort) {
        this.movieRepositoryPort = movieRepositoryPort;
    }

    /**
     * Searches for movies based on the provided title and/or year.
     *
     * <p>The method determines which search strategy to use based on the
     * parameters provided:</p>
     * <ul>
     *   <li>If both {@code title} and {@code year} are provided, it searches by both.</li>
     *   <li>If only {@code title} is provided, it searches by title.</li>
     *   <li>If only {@code year} is provided, it searches by year.</li>
     *   <li>If neither parameter is provided, it returns all movies.</li>
     * </ul>
     *
     * @param title the movie title (can be partial or full), may be {@code null}
     * @param year  the release year of the movie, may be {@code null}
     * @return a list of movies matching the search criteria
     */
    @Override
    public List<Movie> searchMovies(String title, Integer year) {

        if (title != null && year != null) {
            return movieRepositoryPort.findByTitleAndYear(title, year);
        }
        else if (title != null) {
            return movieRepositoryPort.findByTitle(title);
        }
        else if (year != null) {
            return movieRepositoryPort.findByYear(year);
        }
        else {
            return movieRepositoryPort.findAll();
        }
    }
}
