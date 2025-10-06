package com.evaluation.movies.infrastructure.adapters.outbound.movie.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "movie")
public class MovieEntity {
    @Id
    private String imdbID;
    private String title;
    private Integer year;
}