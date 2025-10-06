package com.evaluation.movies.infrastructure.gateways;

import com.evaluation.movies.infrastructure.responses.MovieResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Cliente Feign para consumir el endpoint remoto de Hackerrank.
 */
@FeignClient(
        name = "hackerrankMovieClient",
        url = "${movies.client.url}"
)
public interface MoviesClient {

    @GetMapping("/search/")
    MovieResponseDTO getMovies(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page
    );
}


