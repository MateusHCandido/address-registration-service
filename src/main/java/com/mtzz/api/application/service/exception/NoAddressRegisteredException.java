package com.mtzz.api.application.service.exception;

public class NoAddressRegisteredException extends RuntimeException
{
        public NoAddressRegisteredException(Long id)
        {
                super("No address with Id = " + id + " registered");
        }

}
