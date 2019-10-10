package com.rafaelbenz.sgsc.sgscapi.resources.exception;

import com.rafaelbenz.sgsc.sgscapi.services.exceptions.DataIntegretyServiceException;
import com.rafaelbenz.sgsc.sgscapi.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException ex, HttpServletRequest request){
        StandardError error = new StandardError(HttpStatus.NOT_FOUND.value(),ex.getMessage(),System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DataIntegretyServiceException.class)
    public ResponseEntity<StandardError> dataIntegretyServiceException(DataIntegretyServiceException ex, HttpServletRequest request){
        StandardError error = new StandardError(HttpStatus.BAD_REQUEST.value(),ex.getMessage(),System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> dataIntegretyViolationException(DataIntegrityViolationException ex, HttpServletRequest request){
        ValidationError error = new ValidationError(HttpStatus.BAD_REQUEST.value(),ex.getMessage(),System.currentTimeMillis());
        if(ex.getMessage().contains("EMAIL")){
            error.setMsg("Erro de validação.");
            error.addError("email","Email já existe!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validationException(MethodArgumentNotValidException ex, HttpServletRequest request){
        ValidationError error = new ValidationError(HttpStatus.BAD_REQUEST.value(),"Erro de validação.",System.currentTimeMillis());
        ex.getBindingResult().getFieldErrors().forEach(f->{error.addError(f.getField(),f.getDefaultMessage());});
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}

