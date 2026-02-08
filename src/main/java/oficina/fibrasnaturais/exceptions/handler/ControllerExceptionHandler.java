package oficina.fibrasnaturais.exceptions.handler;


import jakarta.servlet.http.HttpServletRequest;
import oficina.fibrasnaturais.DTOs.erros.CustomErrorDTO;
import oficina.fibrasnaturais.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomErrorDTO> resourceNotFound(ResourceNotFoundException exception, HttpServletRequest request){
        var httpStatus = HttpStatus.NOT_FOUND;
        var customError = new CustomErrorDTO(Instant.now(),httpStatus.value(),
                exception.getMessage(),request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(customError);
    }

}
