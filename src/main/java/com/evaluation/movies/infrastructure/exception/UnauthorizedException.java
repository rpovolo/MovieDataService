package com.evaluation.movies.infrastructure.exception;

public class UnauthorizedException  extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}