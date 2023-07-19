package br.com.banco.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomException extends ResponseStatusException{

    private static final long serialVersionUID = 1L;

    public CustomException(String message, HttpStatus httpStatus) {
        super(httpStatus, message);
    }

    public CustomException(String message, HttpStatus httpStatus, Throwable e) {
        super(httpStatus, message, e);
    }

    public CustomException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public CustomException(HttpStatus httpStatus, String message, Throwable e) {
        super(httpStatus, message, e);
    }

}
