package com.mtzz.api.builders;

import com.mtzz.api.application.controller.DTO.request.AddressRequest;


public class AddressBuilder
{
    private AddressRequest address;

    private AddressBuilder(){}

    public static AddressBuilder generateAddress()
    {
        AddressBuilder builder = new AddressBuilder();
        builder.address = new AddressRequest();
        builder.address.setId(1L);
        builder.address.setCity("LONDON");
        builder.address.setCep("55555555");
        builder.address.setStreetAddress("RUA DOS ALFENEIROS");
        builder.address.setNumber("4");
        return builder;
    }

    public AddressRequest now()
    {
        return address;
    }

}
