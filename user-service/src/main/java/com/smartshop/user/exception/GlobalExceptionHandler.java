package com.smartshop.user.exception;

import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

      @ExceptionHandler(UserAlreadyExistsException.class)
      public ResponseEntity handleUserExists(UserAlreadyExistsException e){
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(e.getMessage());
      }

      @ExceptionHandler(DataIntegrityViolationException.class)
      public ResponseEntity handleDataIntegrity(DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Email uniqueness constraint violated");
      }

      @ExceptionHandler(MethodArgumentNotValidException.class)
      public ResponseEntity handleBadArgument(MethodArgumentNotValidException e){
            String message = e.getBindingResult().getFieldErrors()
                .stream()
                .map(err->err.getRejectedValue() + " is not valid" + err.getField())
                .collect(Collectors.joining("\n"))
                .toString();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(message);
      }
}
