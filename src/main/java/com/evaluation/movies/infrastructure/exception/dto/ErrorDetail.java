package com.evaluation.movies.infrastructure.exception.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorDetail {
    private int code;
    private String message;
    private String timestamp;

    @Builder
    public ErrorDetail(int code, String message, String timestamp) {
        this.code = code;
        this.message = message;
        this.timestamp = (timestamp != null) ? timestamp : LocalDateTime.now().toString();
    }
}