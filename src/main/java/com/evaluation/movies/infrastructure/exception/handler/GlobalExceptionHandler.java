package com.evaluation.movies.infrastructure.exception.handler;

import com.evaluation.movies.infrastructure.exception.InternalServerErrorException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.evaluation.movies.infrastructure.exception.LoanApplicationNotFoundException;
import com.evaluation.movies.infrastructure.exception.dto.ErrorDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

/**
 * Global exception handler for the application.
 * This class intercepts exceptions and provides appropriate HTTP responses.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String BFF_PREFIX_ERROR = "OPERATIONS-ERROR";
    private static final String ERROR_CODE_KEY = "codigo";
    private static final String ERROR_MESSAGE_KEY = "mensaje";
    private static final String DEFAULT_ERROR_MESSAGE = "Unknown error";

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Handles general exceptions and returns an INTERNAL_SERVER_ERROR response.
     *
     * @param exception the Exception that occurred
     * @return a ResponseEntity with error details and a 500 status
     */
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorDetail> handleGeneralException(Exception exception) {
        log.error("{} : {}.", BFF_PREFIX_ERROR, exception.getMessage(), exception);
        ErrorDetail errorDetail = ErrorDetail.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Internal Server Error: " + exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetail);
    }

    /**
     * Creates an ErrorDetail object from an exception and a response map.
     *
     * @param exception             the exception that occurred
     * @param renaperErrorResponse  the response data from RENAPER
     * @return an ErrorDetail object with the extracted error information
     */
    private ErrorDetail createErrorDetail(Exception exception, Map<String, Object> renaperErrorResponse) {
        log.error("{} : {}.", BFF_PREFIX_ERROR, exception.getMessage(), exception);
        return ErrorDetail.builder()
                .code((int) renaperErrorResponse.getOrDefault(ERROR_CODE_KEY, HttpStatus.NOT_FOUND.value()))
                .message((String) renaperErrorResponse.getOrDefault(ERROR_MESSAGE_KEY, DEFAULT_ERROR_MESSAGE))
                .build();
    }
}
