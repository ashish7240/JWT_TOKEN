package com.newhms.exception;

import com.newhms.payload.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler (UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDto>userAlreadyExist(UserAlreadyExistsException u, WebRequest request){
            ErrorDto errorDto = new ErrorDto(u.getMessage(),new Date(), request.getContextPath());
            return  new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
