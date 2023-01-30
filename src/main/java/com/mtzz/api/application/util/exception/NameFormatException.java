package com.mtzz.api.application.util.exception;

public class NameFormatException extends RuntimeException
{
    public NameFormatException(String error_message)
    {
        super(error_message);
    }
}
