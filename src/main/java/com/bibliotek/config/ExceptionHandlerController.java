package com.bibliotek.config;

import com.bibliotek.domain.exception.EntityNotFoundException;
import com.bibliotek.domain.exception.InvalidEntityException;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.List;

@ControllerAdvice("com.bibliotek.config")
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleNotFoundException(HttpServletRequest request, EntityNotFoundException ex) {
        log.error("handleNotFoundException {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse<>(new Date(),"Entity not found exception", List.of(ex.getMessage())));
    }

    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity<ErrorResponse<String>> handleInvalidEntityException(HttpServletRequest request, InvalidEntityException ex) {
        log.error("handleInvalidEntityException {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse<>(new Date(), "Invalid entity exception", List.of(ex.getMessage())));
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse<String>> handleAccessDeniedException(HttpServletRequest request, AccessDeniedException ex) {
        log.error("handleAccessDeniedException {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse<>(new Date(), "Access denied", List.of(ex.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<String>> handleInternelServerError(HttpServletRequest request, Exception ex) {
        log.error("handleInternelServerError {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse<>(new Date(), "Internal server error", List.of(ex.getMessage())));
    }

//
//    @ExceptionHandler({
//            InvalidEntityException.class,
//            ConstraintViolationException.class,
//            HttpMessageConversionException.class})
//    public ResponseEntity<ErrorResponse> handle(Exception ex){
//        log.error(ex.getMessage());
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getClass().getSimpleName(), ex.getMessage()));
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<String> handle(AccessDeniedException ex){
//        log.error(ex.getMessage());
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
//    }
}
