package com.example.testtask.handler;

import com.example.testtask.exception.BadRequestException;
import com.example.testtask.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;

@ControllerAdvice
public class Handlers {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> validHandler(
            BadRequestException exception,
            ServletWebRequest request
    ) {
        ErrorResponse errorResponse = new com.example.testtask.responses.ErrorResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getRequest().getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validHandler(
            MethodArgumentNotValidException exception,
            ServletWebRequest request
    ) {
        BindingResult result = exception.getBindingResult();
        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                result.getFieldError().getDefaultMessage(),
                request.getRequest().getRequestURI()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}