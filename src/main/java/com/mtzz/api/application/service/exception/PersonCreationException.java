package com.mtzz.api.application.service.exception;

public class PersonCreationException extends RuntimeException
{
    public PersonCreationException(String error_message)
    {
        super(error_message);
    }
}
