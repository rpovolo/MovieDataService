package com.evaluation.movies.infrastructure.adapters.outbound.movie.mappers;

import com.evaluation.movies.domain.model.Movie;
import com.evaluation.movies.infrastructure.adapters.outbound.movie.entity.MovieEntity;
import com.evaluation.movies.infrastructure.responses.MovieDTO;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")

public interface  MovieEntityMapper {
    Movie toDomain(MovieEntity movieEntity);
    MovieEntity toEntity(Movie movie);
    List<Movie> toDomains(List<MovieEntity> movieEntities);
    List<MovieEntity> toEntities(List<Movie> movie);


}
