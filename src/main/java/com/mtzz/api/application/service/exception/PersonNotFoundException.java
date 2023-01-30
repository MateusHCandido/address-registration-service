package com.mtzz.api.application.service.exception;

public class PersonNotFoundException extends RuntimeException
{
    public PersonNotFoundException(Object id)
    {
        super("Person not found. ID: " + id);
    }
}
