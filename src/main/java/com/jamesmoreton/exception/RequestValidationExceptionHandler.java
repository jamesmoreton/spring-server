package com.jamesmoreton.exception;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RequestValidationExceptionHandler {

  @ExceptionHandler(value = RequestValidationException.class)
  public ResponseEntity<Object> handleRequestValidationException(RequestValidationException e) {
    return ResponseEntity.badRequest()
        .contentType(MediaType.APPLICATION_JSON)
        .body(new ErrorResponse(e.getErrorCode().name(), e.getMessage()));
  }
}
