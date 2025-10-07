package com.evaluation.movies.infrastructure.adapters.inbound.rest;

import com.evaluation.movies.application.usecases.IndexMoviesUseCase;
import com.evaluation.movies.application.usecases.SearchMoviesUseCase;
import com.evaluation.movies.domain.model.Movie;
import com.evaluation.movies.infrastructure.mappers.MovieMapper;
import com.evaluation.movies.infrastructure.responses.MovieDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final IndexMoviesUseCase indexMoviesUseCase;
    private final SearchMoviesUseCase searchMoviesUseCase;
    private final MovieMapper movieMapper;


    public MovieController(IndexMoviesUseCase indexMoviesUseCase, SearchMoviesUseCase searchMoviesUseCase, MovieMapper movieMapper) {
        this.indexMoviesUseCase = indexMoviesUseCase;
        this.searchMoviesUseCase = searchMoviesUseCase;
        this.movieMapper = movieMapper;
    }

    /**
     * Endpoint to index all movies.
     * <p>
     * POST /movies/index
     * <p>
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
    public ResponseEntity<List<MovieDTO>> searchMovies(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "year", required = false) Integer year) {

        List<Movie> movies = searchMoviesUseCase.searchMovies(title, year);
        List<MovieDTO> movieDtos = movieMapper.toDTOs(movies);

        return ResponseEntity.ok(movieDtos);
    }

}
