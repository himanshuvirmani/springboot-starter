package com.core.util.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by himanshu.virmani on 08/12/15.
 */
@Data
@AllArgsConstructor
public class ResponseExceptionDetails {

    @JsonProperty("error_code")
    private int errorCode;

    @JsonProperty("error_message")
    private String errorMessage;
}
