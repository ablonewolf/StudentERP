package com.arka99.FinalStudentERP.controller;

import com.arka99.FinalStudentERP.error_response.ErrorResponse;
import com.arka99.FinalStudentERP.exceptions.EmployeeAlreadyAddedException;
import com.arka99.FinalStudentERP.exceptions.InvalidEmployeeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = {InvalidEmployeeException.class})
    @ResponseBody
    public ResponseEntity<ErrorResponse> invalidEmployeeException(InvalidEmployeeException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setErrorMessage(exception.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseBody
    public ResponseEntity<ErrorResponse> httpMessageNotReadableException(HttpMessageNotReadableException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setErrorMessage(exception.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {EmployeeAlreadyAddedException.class})
    @ResponseBody
    public ResponseEntity<ErrorResponse> employeeAlreadyAddedException(EmployeeAlreadyAddedException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.CONFLICT.value());
        errorResponse.setErrorMessage(exception.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }
}

