package com.evaluation.movies.infrastructure.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponseDTO {
    private int page;
    private int per_page;
    private int total;
    private int total_pages;
    private List<MovieDTO> data;
}