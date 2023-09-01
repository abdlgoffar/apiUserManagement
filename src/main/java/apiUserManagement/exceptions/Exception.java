package apiUserManagement.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import apiUserManagement.models.response.CoreResponse;

@RestControllerAdvice
public class Exception {
    /**
     * controller advice for ConstraintViolationException
     * @param exception
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CoreResponse<String>> constraintViolationException(ConstraintViolationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CoreResponse.<String>builder().status(false).error(exception.getMessage()).build());
    }


    /**
     * controller advice for ResponseStatusException
     * @param exception
     * @return
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<CoreResponse<String>> apiException(ResponseStatusException exception) {
        return ResponseEntity.status(exception.getStatusCode())
                .body(CoreResponse.<String>builder().status(false).error(exception.getReason()).build());
    }
}
