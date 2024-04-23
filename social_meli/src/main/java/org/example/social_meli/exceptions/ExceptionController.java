package org.example.social_meli.exceptions;

import org.example.social_meli.dto.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(BadRequestException.class)
    private ResponseEntity<?> handlerBadRequestException(BadRequestException ex){
        ExceptionDTO exception = new ExceptionDTO(ex.getMessage());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<?> handlerNotFoundException(NotFoundException ex){
        ExceptionDTO exception = new ExceptionDTO(ex.getMessage());
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    private ResponseEntity<?> handlerConflictException(ConflictException ex){
        ExceptionDTO exception = new ExceptionDTO(ex.getMessage());
        return new ResponseEntity<>(exception, HttpStatus.CONFLICT);
    }
}
