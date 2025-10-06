package com.evaluation.movies.infrastructure.mappers;

import com.evaluation.movies.domain.model.Movie;
import com.evaluation.movies.infrastructure.responses.MovieDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    Movie toDomain(MovieDTO stateDTO);

    MovieDTO toDTO(Movie movie);

    List<MovieDTO> toDTOs(List<Movie> movies);
    List<Movie> toDomains(List<MovieDTO> movieDTO);
}
