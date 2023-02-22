package com.mtzz.api.application.service.exception;

public class AddressAlreadyRegisteredException extends RuntimeException
{
    public AddressAlreadyRegisteredException()
    {
        super("address already registered for that person");
    }
}
