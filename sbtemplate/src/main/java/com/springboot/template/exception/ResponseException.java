package com.springboot.template.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;

/**
 * Created by himanshu.virmani on 29/09/16.
 */
@Data
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseException extends Exception {

    private String errorReason;
    private String errorDescription;
    private int errorCode;

    private HttpStatus status;

    public ResponseException(Errors errors) {
        super(errors.getDescription());
        this.status = errors.getStatus();
        this.errorCode = errors.getStatus().value();
        this.errorReason = errors.getStatus().getReasonPhrase();
        this.errorDescription = errors.getDescription();
    }

    public ResponseException(Errors errors, HttpStatus httpStatus) {
        super(errors.getDescription());
        this.errorDescription = errors.getDescription();
        if (httpStatus == null) {
            this.status = errors.getStatus();
            this.errorCode = errors.getStatus().value();
            this.errorReason = errors.getStatus().getReasonPhrase();
        } else {
            this.status = httpStatus;
            this.errorCode = httpStatus.value();
            this.errorReason = httpStatus.getReasonPhrase();
        }

    }

    public ResponseException(Errors errors, HttpStatus httpStatus, String message) {
        super(errors.getDescription());
        this.errorDescription = (StringUtils.isEmpty(message)) ? errors.getDescription() : message;
        if (httpStatus == null) {
            this.status = errors.getStatus();
            this.errorCode = errors.getStatus().value();
            this.errorReason = errors.getStatus().getReasonPhrase();
        } else {
            this.status = httpStatus;
            this.errorCode = httpStatus.value();
            this.errorReason = httpStatus.getReasonPhrase();
        }
    }
}
