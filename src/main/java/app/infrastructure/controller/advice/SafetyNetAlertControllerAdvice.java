package app.infrastructure.controller.advice;

import app.domain.service.exception.ClientException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SafetyNetAlertControllerAdvice {

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<SafetyNetAlertErrorResponse> handleClientException(ClientException e) {
        SafetyNetAlertErrorResponse body = new SafetyNetAlertErrorResponse();
        body.setMessage(e.getMessage());
        body.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(body);
    }
}
