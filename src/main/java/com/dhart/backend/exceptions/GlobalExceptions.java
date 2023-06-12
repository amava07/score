package com.dhart.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler({RegisteredResourceException.class})
    public ResponseEntity<ErrorMessage> registeredResource(RegisteredResourceException ex){
        ErrorMessage message = new ErrorMessage();
        message.setMessage("That resource is already registered");
        message.setDescription(ex.getMessage());
        message.setStatusCode(409);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorMessage> notFound(NotFoundException ex){
        ErrorMessage message = new ErrorMessage();
        message.setMessage("Resource not found");
        message.setDescription(ex.getMessage());
        message.setStatusCode(404);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }


}

