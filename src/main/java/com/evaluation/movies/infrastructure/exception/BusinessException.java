package com.evaluation.movies.infrastructure.exception;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public abstract class BusinessException extends RuntimeException {
    private HttpStatus status;
    private String detail;
    private String code;
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public BusinessException(HttpStatus status, String message, String detail) {
        this(status, message);
        this.detail = detail;
    }

    public BusinessException(HttpStatus status, String message, String detail, Throwable cause) {
        this(status, message, detail);
        initCause(cause);
    }

    public BusinessException(HttpStatus status, String message, String detail, String code) {
        this(status, message, detail);
        this.detail = detail;
        this.code = code;
    }

    public BusinessException(HttpStatus status, String message, String detail, String code, Throwable cause) {
        this(status, message, detail, code);
        initCause(cause);
    }
}
