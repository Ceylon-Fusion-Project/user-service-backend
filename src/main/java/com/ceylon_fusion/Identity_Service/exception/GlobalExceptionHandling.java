package com.ceylon_fusion.Identity_Service.exception;

import com.ceylon_fusion.Identity_Service.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandling {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<StandardResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StandardResponse(404, ex.getMessage(), null));
    }

    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<StandardResponse> handleInvalidLoginException(InvalidLoginException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new StandardResponse(401, ex.getMessage(), null));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new StandardResponse(400, ex.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardResponse> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StandardResponse(500, "Something went wrong!", null));
    }

}
