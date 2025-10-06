package com.evaluation.movies.infrastructure.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MovieDTO(
        @JsonProperty("Title") String title,
        @JsonProperty("Year") int year,
        @JsonProperty("imdbID") String imdbID
) {}