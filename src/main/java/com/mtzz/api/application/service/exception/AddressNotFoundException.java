package com.mtzz.api.application.service.exception;

public class AddressNotFoundException extends RuntimeException
{
    public AddressNotFoundException(Long id)
    {
        super("Address not found. ID: " + id);
    }
}
