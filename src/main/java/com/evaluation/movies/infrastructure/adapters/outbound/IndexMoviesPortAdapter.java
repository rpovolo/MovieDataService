package com.evaluation.movies.infrastructure.adapters.outbound;

import com.evaluation.movies.application.ports.outbound.IndexMoviesPort;
import com.evaluation.movies.domain.model.Movie;
import com.evaluation.movies.domain.repository.MovieRepositoryPort;
import com.evaluation.movies.infrastructure.adapters.outbound.movie.entity.MovieEntity;
import com.evaluation.movies.infrastructure.adapters.outbound.movie.mappers.MovieEntityMapper;
import com.evaluation.movies.infrastructure.exception.InternalServerErrorException;
import com.evaluation.movies.infrastructure.gateways.MoviesClient;
import com.evaluation.movies.infrastructure.mappers.MovieMapper;
import com.evaluation.movies.infrastructure.responses.MovieDTO;
import com.evaluation.movies.infrastructure.responses.MovieResponseDTO;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class IndexMoviesPortAdapter implements IndexMoviesPort {

    private final MoviesClient moviesClient;

    private final MovieMapper movieMapper;

    private final MovieRepositoryPort movieRepositoryPort;

    /**
     * Constructor for dependency injection.
     *
     * @param moviesClient        client to fetch movies from external API
     * @param movieMapper         mapper to convert DTOs to domain objects
     * @param movieRepositoryPort repository port to save movies
     */
    public IndexMoviesPortAdapter(MoviesClient moviesClient, MovieMapper movieMapper, MovieRepositoryPort movieRepositoryPort) {
        this.moviesClient = moviesClient;
        this.movieMapper = movieMapper;
        this.movieRepositoryPort = movieRepositoryPort;
    }

    /**
     * Index all movies by fetching them from external resources and saving
     * them to the repository.
     */
    @Override
    public void indexMovies() {
        List<Movie> movies = getAllMoviesFromExternalResources();

        movieRepositoryPort.saveAll(movies);
    }

    /**
     * Fetch all movies from external resources.
     * <p>
     * This method retrieves the first page to determine the total number of pages,
     * then iterates through all pages, collecting all movie data.
     *
     * @return List<Movie> list of domain movie objects
     */
    public List<Movie> getAllMoviesFromExternalResources() {
        try {
            MovieResponseDTO firstPage = moviesClient.getMovies(1);

            List<MovieDTO> allMovies =
                    java.util.stream.IntStream.rangeClosed(1, firstPage.total_pages())
                            .mapToObj(page -> moviesClient.getMovies(page).data())
                            .flatMap(List::stream)
                            .toList();

            return movieMapper.toDomains(allMovies);
        } catch (FeignException.FeignClientException e) {
            log.error("Error fetching movies from external API: status={}, message={}", e.status(), e.getMessage(), e);
            throw new InternalServerErrorException(e.getMessage());
        }catch (Exception e) {
            log.error("Unexpected error fetching movies: {}", e.getMessage(), e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
