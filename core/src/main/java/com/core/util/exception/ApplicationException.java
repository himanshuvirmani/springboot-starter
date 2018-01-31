package com.core.util.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by ankit.c on 22/06/16.
 */
public class ApplicationException extends RuntimeException {

    private ErrorResponse errorResponse;

    public ApplicationException(String message) {
        this(new ErrorResponse(message));
    }

    public ApplicationException(HttpStatus httpStatus) {
        this(new ErrorResponse(httpStatus));
    }

    public ApplicationException(HttpStatus httpStatus, String message) {
        this(new ErrorResponse(httpStatus, message));
    }

    public ApplicationException(ErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;
    }

    public ApplicationException(Throwable t, String message) {
        this(t, new ErrorResponse(message));
    }

    public ApplicationException(Throwable t, HttpStatus httpStatus, String message) {
        this(t, new ErrorResponse(httpStatus, message));
    }

    public ApplicationException(Throwable t, HttpStatus httpStatus) {
        this(t, new ErrorResponse(httpStatus, httpStatus.getReasonPhrase()));
    }

    public ApplicationException(Throwable t, ErrorResponse errorResponse) {
        super(errorResponse.getMessage(), t);
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return this.errorResponse;
    }
}