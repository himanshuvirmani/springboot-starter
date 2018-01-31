package com.core.util.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Created by ankit.c on 22/06/16.
 */
@Getter
@JsonIgnoreProperties(value = "httpStatus")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private int status;
    private HttpStatus httpStatus;
    private String error;
    private String message;
    private List<String> errors;

    public ErrorResponse(String message) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public ErrorResponse(HttpStatus status) {
        this(status, status.getReasonPhrase());
    }

    public ErrorResponse(HttpStatus status, String message) {
        this(status, message, status.getReasonPhrase());
    }

    public ErrorResponse(HttpStatus status, String message, String error) {
        this(status, message, error, null);
    }

    public ErrorResponse(HttpStatus status, String message, List<String> errors) {
        this(status, message, status.getReasonPhrase(), errors);
    }

    public ErrorResponse(HttpStatus status, String message, String error, List<String> errors) {
        this.status = status.value();
        this.httpStatus = status;
        this.message = message;
        this.errors = errors;
        this.error = StringUtils.isNotEmpty(error) ? error : status.getReasonPhrase();
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void setError(String error) {
        if (StringUtils.isNotEmpty(error))
            this.error = error;
    }

    public void setMessage(String message) {
        if (StringUtils.isNotEmpty(message))
            this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "status=" + status +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", errors=" + errors +
                '}';
    }
}
