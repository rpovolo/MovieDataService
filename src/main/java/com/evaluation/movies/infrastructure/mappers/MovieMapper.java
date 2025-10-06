package com.evaluation.movies.infrastructure.mappers;

import com.evaluation.movies.domain.model.Movie;
import com.evaluation.movies.infrastructure.responses.MovieDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    @Mapping(source = "title", target = "title")
    @Mapping(source = "year", target = "year")
    @Mapping(source = "imdbID", target = "imdbID")
    Movie toDomain(MovieDTO stateDTO);

    MovieDTO toDTO(Movie movie);

    List<MovieDTO> toDTOs(List<Movie> movies);
    List<Movie> toDomains(List<MovieDTO> movieDTO);
}
