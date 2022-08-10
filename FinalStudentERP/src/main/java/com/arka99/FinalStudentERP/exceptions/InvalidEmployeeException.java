package com.arka99.FinalStudentERP.exceptions;

public class InvalidEmployeeException extends RuntimeException{
    public InvalidEmployeeException() {
        super();
    }

    public InvalidEmployeeException(String message) {
        super(message);
    }

    public InvalidEmployeeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEmployeeException(Throwable cause) {
        super(cause);
    }
}
