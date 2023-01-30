package com.mtzz.api.application.service.exception;

public class EmptyCollectionException extends RuntimeException
{
    public EmptyCollectionException(Object id)
    {
        super("THE LIST IS EMPTY OR NOT FOUND");
    }
}
