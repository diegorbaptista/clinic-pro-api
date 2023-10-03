package com.clinicpro.api.infra.errors;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.clinicpro.api.infra.security.error.UnauthorizedException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handle404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<CustomValidationError>> handle400(MethodArgumentNotValidException exception) {
        var errors = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(CustomValidationError::new).toList());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomErrorMessage> handle400(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(new CustomErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<CustomErrorMessage> handle422(EntityExistsException exception) {
        return ResponseEntity.unprocessableEntity().body(new CustomErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity<CustomErrorMessage> handle401(JWTCreationException exception) {
        return ResponseEntity.status(401).body(new CustomErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<CustomErrorMessage> handle401(UnauthorizedException exception) {
        return ResponseEntity.status(401).body(new CustomErrorMessage(exception.getMessage()));
    }

    public record CustomErrorMessage(String message) {
    }

    public record CustomValidationError(String field, String message) {
        public CustomValidationError(FieldError fieldError) {
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    };

}
