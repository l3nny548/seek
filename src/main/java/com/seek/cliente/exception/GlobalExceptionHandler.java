package com.seek.cliente.exception;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import com.seek.cliente.dto.ErrorDto;

import org.springframework.security.authentication.BadCredentialsException;


@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> handleUsernameNotFoundException(BadCredentialsException ex) {
        ErrorDto error = ErrorDto.builder()
        .code(HttpStatus.NOT_FOUND.toString())
        .message("Error: Usuario no encontrado.")
        .description("Error: Usuario no encontrado.")
        .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDto> handleBadRequestException(BadRequestException ex) {
        ErrorDto error = ErrorDto.builder()
        .code(HttpStatus.BAD_REQUEST.toString())
        .message(ex.getMessage())
        .description(ex.getMessage())
        .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


     @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorDto> handleResponseStatusException(ResponseStatusException ex) {
        ErrorDto error = ErrorDto.builder()
        .code(ex.getStatusCode().toString())
        .message(ex.getReason())
        .description("Ha ocurrido un error.")
        .build();

        return ResponseEntity.status(ex.getStatusCode())
                .body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGenericException(Exception ex) {
        ErrorDto error = ErrorDto.builder()
        .code(HttpStatus.INTERNAL_SERVER_ERROR.toString())
        .message("Error interno del servidor. Por favor, contacte al administrador.")
        .description("Error interno del servidor. Por favor, contacte al administrador.")
        .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorDto> handleValidationException(ValidationException ex) {
        String description = ex.getErrors().entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .reduce((msg1, msg2) -> msg1 + ", " + msg2)
                .orElse("Validation errors occurred");

        ErrorDto error = ErrorDto.builder()
        .code(HttpStatus.BAD_REQUEST.toString())
        .message("Campos inválidos")
        .description(description)
        .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

}

