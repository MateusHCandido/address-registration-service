package com.mtzz.api.application.service.exception;

public class ExistingAddressException extends RuntimeException
{
    public ExistingAddressException(String error_message)

    {
        super(error_message);
    }
}
