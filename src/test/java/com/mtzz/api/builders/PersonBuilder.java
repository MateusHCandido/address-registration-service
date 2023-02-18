package com.mtzz.api.builders;

import com.mtzz.api.application.controller.DTO.request.PersonRequest;

import java.util.Date;

public class PersonBuilder
{
    private PersonRequest person;
    private PersonBuilder(){}


    public static PersonBuilder generatePerson()
    {
        PersonBuilder builder = new PersonBuilder();
        builder.person = new PersonRequest();
        builder.person.setPersonId(1L);
        builder.person.setName("Joseph Cambridge");
        builder.person.setBirthDate("1999-10-17");
        return builder;
    }

    public PersonRequest now() {return person;}
}
