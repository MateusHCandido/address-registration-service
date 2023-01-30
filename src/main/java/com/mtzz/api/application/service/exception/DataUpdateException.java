package com.mtzz.api.application.service.exception;

public class DataUpdateException extends RuntimeException
{
    public DataUpdateException(String error_message)
    {
        super(error_message);
    }
}
