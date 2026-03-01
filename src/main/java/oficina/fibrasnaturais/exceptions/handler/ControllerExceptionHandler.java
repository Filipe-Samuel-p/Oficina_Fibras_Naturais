package oficina.fibrasnaturais.exceptions.handler;


import jakarta.servlet.http.HttpServletRequest;
import oficina.fibrasnaturais.DTOs.error.CustomErrorDTO;
import oficina.fibrasnaturais.DTOs.error.ValidationErrorDTO;
import oficina.fibrasnaturais.exceptions.BadCredentialsException;
import oficina.fibrasnaturais.exceptions.ConflictException;
import oficina.fibrasnaturais.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomErrorDTO> resourceNotFound(ResourceNotFoundException exception, HttpServletRequest request) {
        var httpStatus = HttpStatus.NOT_FOUND;
        var customError = new CustomErrorDTO(Instant.now(), httpStatus.value(),
                exception.getMessage(), request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(customError);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CustomErrorDTO> badCredentials(BadCredentialsException e, HttpServletRequest request) {
        var httpStatus = HttpStatus.UNAUTHORIZED;
        var customError = new CustomErrorDTO(Instant.now(), httpStatus.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(customError);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<CustomErrorDTO> conflictException(ConflictException exception, HttpServletRequest request){
        var httpStatus = HttpStatus.CONFLICT;
        var customError = new CustomErrorDTO(Instant.now(),httpStatus.value(),
                exception.getMessage(),request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(customError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorDTO> methodArgumentNotValidation(MethodArgumentNotValidException error, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationErrorDTO err = new ValidationErrorDTO(Instant.now(), status.value(), "Dados inválidos", request.getRequestURI());
        for (FieldError f : error.getBindingResult().getFieldErrors()) {
            err.addError(f.getField(), f.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(err);
    }

}


