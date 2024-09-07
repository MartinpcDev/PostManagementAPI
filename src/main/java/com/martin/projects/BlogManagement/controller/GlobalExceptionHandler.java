package com.martin.projects.BlogManagement.controller;

import com.martin.projects.BlogManagement.dto.ExceptionResponse;
import com.martin.projects.BlogManagement.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  ZoneId zoneId = ZoneId.of("America/Lima");
  LocalDateTime timestamp = LocalDateTime.now(zoneId);

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(
      ResourceNotFoundException resourceNotFoundException,
      HttpServletRequest request) {

    int httpStatus = HttpStatus.NOT_FOUND.value();
    ExceptionResponse exceptionResponse = new ExceptionResponse(
        httpStatus,
        request.getMethod(),
        resourceNotFoundException.getMessage(),
        resourceNotFoundException.getMessage(),
        timestamp,
        null
    );

    return ResponseEntity.status(httpStatus).body(exceptionResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException methodArgumentNotValidException, HttpServletRequest request) {

    int httpStatus = HttpStatus.BAD_REQUEST.value();
    List<ObjectError> errors = methodArgumentNotValidException.getAllErrors();
    List<String> details = errors.stream().map(error -> {
      if (error instanceof FieldError fieldError) {
        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
      }
      return error.getDefaultMessage();
    }).toList();

    ExceptionResponse exceptionResponse = new ExceptionResponse(
        httpStatus,
        request.getMethod(),
        "La request tiene parametros invalidos o incompletos",
        methodArgumentNotValidException.getMessage(),
        timestamp,
        details
    );

    return ResponseEntity.status(httpStatus).body(exceptionResponse);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionResponse> handleAllExceptions(Exception exception,
      HttpServletRequest request) {

    int httpStatus = HttpStatus.BAD_REQUEST.value();
    ExceptionResponse exceptionResponse = new ExceptionResponse(
        httpStatus,
        request.getMethod(),
        "Ocurrio un error inesperado",
        exception.getMessage(),
        timestamp,
        null
    );

    return ResponseEntity.status(httpStatus).body(exceptionResponse);
  }
}
