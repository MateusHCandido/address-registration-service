package com.mtzz.api.application.service.exception;

public class AddressCreationFailureException extends RuntimeException
{
    public AddressCreationFailureException(String error_message)
    {
        super(error_message);
    }
}
