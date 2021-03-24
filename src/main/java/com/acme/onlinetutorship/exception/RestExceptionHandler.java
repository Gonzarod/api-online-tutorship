package com.acme.onlinetutorship.exception;

import com.acme.onlinetutorship.controller.commons.MessageResponse;
import com.acme.onlinetutorship.controller.constants.ResponseConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return buildResponseEntity( MessageResponse.builder().code(ResponseConstants.ERROR_CODE).message(ex.getMessage()).build(),HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> buildResponseEntity(MessageResponse apiError, HttpStatus status) {
        return new ResponseEntity<>(apiError, status);
    }
}
