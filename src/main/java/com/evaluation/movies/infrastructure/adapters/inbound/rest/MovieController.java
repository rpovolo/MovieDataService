package com.evaluation.movies.infrastructure.adapters.inbound.rest;

import com.evaluation.movies.application.usecases.IndexMoviesUseCase;
import com.evaluation.movies.application.usecases.SearchMoviesUseCase;
import com.evaluation.movies.domain.model.Movie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final IndexMoviesUseCase indexMoviesUseCase;
    private final SearchMoviesUseCase searchMoviesUseCase;

    public MovieController(IndexMoviesUseCase indexMoviesUseCase, SearchMoviesUseCase searchMoviesUseCase) {
        this.indexMoviesUseCase = indexMoviesUseCase;
        this.searchMoviesUseCase = searchMoviesUseCase;
    }

    /**
     * Endpoint to index all movies.
     *
     * POST /movies/index
     *
     * This method invokes the `indexMoviesUseCase` to index all available movies.
     * Returns a confirmation message when the operation completes successfully.
     *
     * @return ResponseEntity<String> with a success message
     */
    @PostMapping("/index")
    public ResponseEntity<String> indexAllMovies() {
        indexMoviesUseCase.indexMovies();
        return ResponseEntity.ok("Movies indexed successfully.");
    }

    /**
     * Endpoint to search movies by title and/or year.
     * GET /movies/search?title=abc&year=2020
     */
    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchMovies(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "year", required = false) Integer year) {

        List<Movie> movies = searchMoviesUseCase.searchMovies(title, year);
        return ResponseEntity.ok(movies);
    }
}
