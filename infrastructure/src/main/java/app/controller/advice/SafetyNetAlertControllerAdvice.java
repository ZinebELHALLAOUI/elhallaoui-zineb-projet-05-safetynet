package app.controller.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import service.exception.EntityAlreadyExistException;
import service.exception.EntityNotFoundException;

@ControllerAdvice
public class SafetyNetAlertControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(SafetyNetAlertControllerAdvice.class);

    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseEntity<SafetyNetAlertErrorResponse> handleEntityAlreadyExistException(EntityAlreadyExistException e) {
        logger.error("Entity already exists", e);
        SafetyNetAlertErrorResponse body = new SafetyNetAlertErrorResponse();
        body.setMessage(e.getMessage());
        body.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(body);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<SafetyNetAlertErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        logger.error("Entity not found", e);
        SafetyNetAlertErrorResponse body = new SafetyNetAlertErrorResponse();
        body.setMessage(e.getMessage());
        body.setStatus(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<SafetyNetAlertErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error("Validation error", e);
        SafetyNetAlertErrorResponse body = new SafetyNetAlertErrorResponse();
        body.setMessage(e.getMessage());
        body.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<SafetyNetAlertErrorResponse> handleException(Exception e) {
        logger.error("Internal error", e);
        SafetyNetAlertErrorResponse body = new SafetyNetAlertErrorResponse();
        body.setMessage(e.getMessage());
        body.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(body);
    }
}
