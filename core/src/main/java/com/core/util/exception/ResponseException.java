package com.core.util.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by himanshu.virmani on 08/12/15.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResponseException extends Exception {
    private ResponseExceptionDetails responseExceptionDetails;

    public ResponseException(ResponseExceptionDetails responseExceptionDetails) {
        this.responseExceptionDetails = responseExceptionDetails;
    }
}
