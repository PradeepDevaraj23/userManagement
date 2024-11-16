package com.prdtech.com.usermanagement.exceptionHandling;

import com.prdtech.com.usermanagement.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public @ResponseBody ErrorResponse handleResourceNotFoundException(ResourceNotFoundException ex){
         return new ErrorResponse(HttpStatus.NOT_FOUND.value(),ex.getMessage());
    }

    @ExceptionHandler
    public @ResponseBody ErrorResponse handleRuntimeException(RuntimeException ex){
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(),ex.getMessage());
    }
}
