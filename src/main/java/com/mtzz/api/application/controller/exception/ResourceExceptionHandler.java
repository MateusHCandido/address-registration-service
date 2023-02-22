package com.mtzz.api.application.controller.exception;

import com.mtzz.api.application.service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler
{

    //exception for update_person -> person not found
    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(PersonNotFoundException error, HttpServletRequest request)
    {
        String error_message = "Resource not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError standard_error = new StandardError(Instant.now(), status.value(), error_message,
                                                         error.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(standard_error);
    }

    //exception for update_person
    @ExceptionHandler(DataUpdateException.class)
    public ResponseEntity<StandardError> invalidRequest(DataUpdateException error, HttpServletRequest request)
    {
        String error_message = "Invalid creation data";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError standardError = new StandardError(Instant.now(), status.value(), error_message,
                error.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(standardError);
    }

    //exception for add_person
    @ExceptionHandler(PersonCreationException.class)
    public ResponseEntity<StandardError> invalidRequest(PersonCreationException error, HttpServletRequest request)
    {
        String error_message = "Invalid creation data";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError standardError = new StandardError(Instant.now(), status.value(), error_message,
                error.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(standardError);
    }

    //exception for empty collection
    @ExceptionHandler(EmptyCollectionException.class)
    public ResponseEntity<StandardError> emptyCollectionException(EmptyCollectionException error, HttpServletRequest request)
    {
        String error_message = "Resource not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError standardError = new StandardError(Instant.now(), status.value(), error_message,
                                                        error.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(standardError);
    }

    //Exception for address not found
    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<StandardError> addressNotFound(AddressNotFoundException error, HttpServletRequest request)
    {
        String error_message = "Resource not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError standard_error = new StandardError(Instant.now(), status.value(), error_message,
                error.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(standard_error);
    }

    @ExceptionHandler(NoAddressRegisteredException.class)
    public ResponseEntity<StandardError> noAddressRegistered(NoAddressRegisteredException error, HttpServletRequest request)
    {
        String error_message = "Request not accepted";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError standard_error = new StandardError(Instant.now(), status.value(), error_message,
                error.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(standard_error);
    }

    @ExceptionHandler(AddressAlreadyRegisteredException.class)
    public ResponseEntity<StandardError> addressAlreadyRegistered(AddressAlreadyRegisteredException error, HttpServletRequest request)
    {
        String error_message = "Request not accepted";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError standard_error = new StandardError(Instant.now(), status.value(), error_message,
                error.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(standard_error);
    }
}
