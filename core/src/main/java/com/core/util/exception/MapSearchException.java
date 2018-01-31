package com.core.util.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Created by vaibhav.tomar on 25/07/17.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class MapSearchException extends Exception{
    private HttpStatus statusCode;
    private String errorMessage;

}
