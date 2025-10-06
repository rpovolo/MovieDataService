package com.evaluation.movies.infrastructure.adapters.outbound.movie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "movie")
public class MovieEntity {
    @Id
    private String imdbID;
    private String title;
    private Integer year;
}