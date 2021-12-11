package com.bibliotek.config;

import com.bibliotek.domain.exception.InvalidEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.nio.file.AccessDeniedException;
import java.util.*;

@ControllerAdvice()
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(
            {EntityNotFoundException.class, com.bibliotek.domain.exception.EntityNotFoundException.class})
    public ResponseEntity<ErrorResponse<String>> handleNotFoundException(HttpServletRequest request, Exception ex) {
        log.error("handleNotFoundException {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse<>( "Entity not found exception", List.of(ex.getMessage())));
    }

    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity<ErrorResponse<String>> handleInvalidEntityException(HttpServletRequest request, InvalidEntityException ex) {
        log.error("handleInvalidEntityException {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse<>( "Invalid entity exception", List.of(ex.getMessage())));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse<String>> handleHttpRequestMethodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {
        log.error("handleHttpRequestMethodNotSupportedException {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse<>( "HttpRequest method not supported exception", List.of(ex.getMessage())));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse<String>> handleValidationException(HttpServletRequest request, ValidationException ex) {
        log.error("handleValidationException {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse<>("Validation exception", List.of(ex.getMessage())));
    }


    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<ErrorResponse<String>> handleInvalidDataAccessApiUsageException(HttpServletRequest request, InvalidDataAccessApiUsageException ex) {
        log.error("handleInvalidDataAccessApiUsageException {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse<>("The given id must not be null!", ex.getMessage() != null ? List.of(ex.getMessage()) : null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<Map<String, String>>> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        log.error("handleMethodArgumentNotValidException {}\n", request.getRequestURI(), ex);

        List<Map<String, String>> details = new ArrayList<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> {
                    Map<String, String> detail = new HashMap<>();
                    detail.put("objectName", fieldError.getObjectName());
                    detail.put("field", fieldError.getField());
                    detail.put("rejectedValue", "" + fieldError.getRejectedValue());
                    detail.put("errorMessage", fieldError.getDefaultMessage());
                    details.add(detail);
                });

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse<>("Method argument validation failed", details));
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse<String>> handleAccessDeniedException(HttpServletRequest request, AccessDeniedException ex) {
        log.error("handleAccessDeniedException {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse<>( "Access denied", List.of(ex.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<String>> handleInternelServerError(HttpServletRequest request, Exception ex) {
        log.error("handleInternelServerError {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse<>( "Internal server error", List.of(ex.getMessage())));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse<String>> handleMissingServletRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException ex) {
        log.error("hanldeMissingServletRequestParameterException {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse<>( "Missing request parameter", ex.getMessage() != null ? List.of(ex.getMessage()) : null));
    }

    @ExceptionHandler({ConstraintViolationException.class, HttpMessageConversionException.class})
    public ResponseEntity<ErrorResponse<String>> handle(HttpServletRequest request, Exception ex) {
        log.error("handle{} {}\n", ex.getClass().getSimpleName(), request.getRequestURI(), ex);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse<>( ex.getClass().getSimpleName(), List.of(ex.getMessage())));
    }

}
