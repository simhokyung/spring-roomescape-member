package roomescape.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import roomescape.dto.ErrorResponse;
import roomescape.exception.DuplicateResourceException;
import roomescape.exception.ErrorCode;
import roomescape.exception.InvalidInputException;
import roomescape.exception.NotFoundException;
import roomescape.exception.PastReservationException;
import roomescape.exception.ResourceInUseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        ErrorCode errorCode = ErrorCode.INVALID_REQUEST_BODY;

        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(new ErrorResponse(
                        errorCode.name(),
                        errorCode.getMessage())
                );
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInputException e) {
        ErrorCode errorCode = ErrorCode.INVALID_INPUT;

        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(new ErrorResponse(
                        errorCode.name(),
                        e.getMessage()));
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        ErrorCode errorCode = ErrorCode.NOT_FOUND;

        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(new ErrorResponse(errorCode.name(), errorCode.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorCode errorCode = ErrorCode.INVALID_INPUT;
        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(new ErrorResponse(errorCode.name(), message));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        ErrorCode errorCode = ErrorCode.INVALID_INPUT;

        String message = e.getConstraintViolations()
                .stream()
                .findFirst()
                .map(ConstraintViolation::getMessage)
                .orElse(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(new ErrorResponse(errorCode.name(), message));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        ErrorCode errorCode = ErrorCode.INVALID_FORMAT;

        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(new ErrorResponse(
                        errorCode.name(),
                        e.getName() + errorCode.getMessage()
                ));
    }

    @ExceptionHandler(PastReservationException.class)
    public ResponseEntity<ErrorResponse> handlePastReservationException(PastReservationException e) {
        ErrorCode errorCode = ErrorCode.PAST_RESERVATION;

        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(new ErrorResponse(
                        errorCode.name(),
                        e.getMessage()));
    }

    @ExceptionHandler(ResourceInUseException.class)
    public ResponseEntity<ErrorResponse> handleResourceInUseException(ResourceInUseException e) {
        ErrorCode errorCode = ErrorCode.RESOURCE_IN_USE;

        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(new ErrorResponse(
                        errorCode.name(),
                        e.getMessage()));
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResourceException(DuplicateResourceException e) {
        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(new ErrorResponse(
                        errorCode.name(),
                        errorCode.getMessage()
                ));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        ErrorCode errorCode = ErrorCode.NOT_FOUND;

        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(new ErrorResponse(
                        errorCode.name(),
                        e.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;

        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(new ErrorResponse(
                        errorCode.name(),
                        errorCode.getMessage()
                ));
    }
}
