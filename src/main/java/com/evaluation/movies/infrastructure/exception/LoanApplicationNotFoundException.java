package com.evaluation.movies.infrastructure.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class LoanApplicationNotFoundException extends BusinessException {

    private final String responseBody;

    public LoanApplicationNotFoundException(String message, HttpStatus status, String responseBody) {
        super(status, message);
        this.responseBody = responseBody;
    }

    public LoanApplicationNotFoundException(String message, HttpStatus status) {
        super(status, message);
        this.responseBody = null;
    }
}
