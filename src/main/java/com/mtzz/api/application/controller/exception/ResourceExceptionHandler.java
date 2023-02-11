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
    public ResponseEntity<StandardError> resource_not_found(PersonNotFoundException error, HttpServletRequest request)
    {
        String error_message = "Resource not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError standard_error = new StandardError(Instant.now(), status.value(), error_message,
                                                         error.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(standard_error);
    }

    //exception for update_person
    @ExceptionHandler(DataUpdateException.class)
    public ResponseEntity<StandardError> invalid_request(DataUpdateException error, HttpServletRequest request)
    {
        String error_message = "Invalid creation data";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError standardError = new StandardError(Instant.now(), status.value(), error_message,
                error.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(standardError);
    }

    //exception for add_person
    @ExceptionHandler(PersonCreationException.class)
    public ResponseEntity<StandardError> invalid_request(PersonCreationException error, HttpServletRequest request)
    {
        String error_message = "Invalid creation data";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError standardError = new StandardError(Instant.now(), status.value(), error_message,
                error.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(EmptyCollectionException.class)
    public ResponseEntity<StandardError> empty_collection_exception(EmptyCollectionException error, HttpServletRequest request)
    {
        String error_message = "Resource not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError standardError = new StandardError(Instant.now(), status.value(), error_message,
                                                        error.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<StandardError> address_not_found(AddressNotFoundException error, HttpServletRequest request)
    {
        String error_message = "Resource not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError standardError = new StandardError(Instant.now(), status.value(), error_message,
                error.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(AddressCreationFailureException.class)
    public ResponseEntity<StandardError> address_creation_failure(AddressCreationFailureException error, HttpServletRequest request)
    {
        String error_message = "Address creation failure";
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardError standardError = new StandardError(Instant.now(), status.value(), error_message,
                error.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(standardError);
    }
}
