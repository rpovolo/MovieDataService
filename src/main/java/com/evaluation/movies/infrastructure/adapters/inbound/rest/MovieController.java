package com.evaluation.movies.infrastructure.adapters.inbound.rest;

import com.evaluation.movies.application.usecases.IndexMoviesUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final IndexMoviesUseCase indexMoviesUseCase;

    public MovieController(IndexMoviesUseCase indexMoviesUseCase) {
        this.indexMoviesUseCase = indexMoviesUseCase;
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
}
