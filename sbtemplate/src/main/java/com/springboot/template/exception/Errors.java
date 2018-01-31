package com.springboot.template.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Created by himanshu.virmani on 29/09/16.
 */
@AllArgsConstructor
public enum Errors {

    REQUEST_TIMEOUT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Request Wait Time exceeded"),
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown Error");

    @Getter
    private HttpStatus status;

    @Getter
    private String description;
}
