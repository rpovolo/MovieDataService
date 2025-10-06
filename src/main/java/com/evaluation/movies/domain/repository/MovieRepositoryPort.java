package com.evaluation.movies.domain.repository;

import com.evaluation.movies.domain.model.Movie;

import java.util.List;

public interface MovieRepositoryPort {
    List<Movie> findByTitle(String title);
    List<Movie> findByYear(Integer year);
    List<Movie> findByTitleAndYear(String title, Integer year);
    List<Movie> findAll();
    void saveAll(List<Movie> movies);
}
