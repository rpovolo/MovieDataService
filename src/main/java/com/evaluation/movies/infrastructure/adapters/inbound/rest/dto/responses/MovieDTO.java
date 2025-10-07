package com.evaluation.movies.infrastructure.adapters.inbound.rest.dto.responses;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MovieDTO {
    private String imdbID;
    private String title;
    private Integer year;
}
