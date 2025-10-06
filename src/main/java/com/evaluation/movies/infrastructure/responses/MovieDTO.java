package com.evaluation.movies.infrastructure.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Year")
    private Integer year;
    private String imdbID;
}