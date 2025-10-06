package com.evaluation.movies.infrastructure.responses;

import java.util.List;

public record MovieResponseDTO(
        int page,
        int per_page,
        int total,
        int total_pages,
        List<MovieDTO> data
) {}