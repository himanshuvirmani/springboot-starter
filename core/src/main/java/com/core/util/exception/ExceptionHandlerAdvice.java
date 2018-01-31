package com.core.util.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by ankit.c on 22/06/16.
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {
    private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    @ExceptionHandler(value = ApplicationException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> applicationExceptionHandler(ApplicationException e) {
        log.error("ApplicationException: " + e.getErrorResponse().toString(), e);
        if (e.getErrorResponse().getHttpStatus().is5xxServerError()) {
            final long id = ThreadLocalRandom.current().nextLong();
            log.error(ExceptionUtils.formatLogMessage(id));
            e.getErrorResponse().setMessage(ExceptionUtils.toString(e));
            e.getErrorResponse().setError(ExceptionUtils.formatErrorMessage(id));
        } else {
            e.getErrorResponse().setMessage(ExceptionUtils.buildMessage(e));
        }
        return new ResponseEntity<>(e.getErrorResponse(), e.getErrorResponse().getHttpStatus());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHabdler(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException: " + e.getMessage());

        List<String> errors = new ArrayList<>();

        for (ObjectError error : e.getBindingResult().getGlobalErrors()) {
            errors.add(error.getDefaultMessage());
        }

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            String errorMsg = String.format("%s %s, (was %s)",
                    fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue());
            errors.add(errorMsg);
        }

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY, "Error while processing entity.", errors);

        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> servletException(NoHandlerFoundException e) {
        log.error("NoHandlerFoundException: " + e.getMessage());
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        String message = e.getHttpMethod() + " " + e.getRequestURL() + " not found";
        ErrorResponse response = new ErrorResponse(httpStatus, message);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> servletException(HttpMediaTypeNotSupportedException e) {
        log.error("HttpMediaTypeNotSupportedException: " + e.getMessage());
        HttpStatus httpStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        String msg = httpStatus.getReasonPhrase() + ", was [" + e.getContentType() + "]";
        ErrorResponse response = new ErrorResponse(httpStatus, msg);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> typeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("MethodArgumentTypeMismatchException: " + e.getMessage());
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String msg = "Bad Request";
        ErrorResponse response = new ErrorResponse(httpStatus, msg);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> servletException(HttpRequestMethodNotSupportedException e) {
        log.error("HttpRequestMethodNotSupportedException: " + e.getMessage());
        HttpStatus httpStatus = HttpStatus.METHOD_NOT_ALLOWED;

        HttpHeaders headers = new HttpHeaders();
        Set<HttpMethod> supportedMethods = e.getSupportedHttpMethods();
        if (!supportedMethods.isEmpty()) {
            headers.setAllow(supportedMethods);
        }

        ErrorResponse response = new ErrorResponse(httpStatus, "Request method " + e.getMethod() + " not supported");
        return new ResponseEntity<>(response, headers, response.getHttpStatus());
    }

    @ExceptionHandler(value = ServletRequestBindingException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> servletException(ServletRequestBindingException e) {
        log.error("ServletRequestBindingException: " + e.getMessage());
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponse response = new ErrorResponse(httpStatus, e.getMessage());
        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(value = InvalidMediaTypeException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> servletException(InvalidMediaTypeException e) {
        log.error("InvalidMediaTypeException: " + e.getMessage());
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponse response = new ErrorResponse(httpStatus, e.getMessage());
        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(value = ServletException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> servletException(ServletException e) {
        log.error("ServletException: " + e.getMessage(), e);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponse response = new ErrorResponse(httpStatus, e.getMessage());
        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException: " + e.getMessage());
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponse response = new ErrorResponse(httpStatus, e.getMessage());
        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> genericExceptionHadler(Exception e) {
        final long id = ThreadLocalRandom.current().nextLong();
        String message = ExceptionUtils.toString(e);
        log.error(ExceptionUtils.formatLogMessage(id));
        log.error(message, e);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(new ErrorResponse(httpStatus, message, ExceptionUtils.formatErrorMessage(id)), httpStatus);
    }

    @ExceptionHandler(MapSearchException.class)
    public ResponseEntity<ErrorResponse> getResponse(MapSearchException exception){

        return new ResponseEntity<ErrorResponse>(new ErrorResponse(exception.getStatusCode(),exception.getErrorMessage()),exception.getStatusCode());
    }

}
