package com.mtzz.api.application.mapper;

import com.mtzz.api.application.controller.DTO.request.PersonRequest;
import com.mtzz.api.application.controller.DTO.response.PersonResponse;
import com.mtzz.api.application.entities.Person;
import com.mtzz.api.application.util.DataFormat;

import lombok.SneakyThrows;

public class PersonMapper extends DataFormat {

    @SneakyThrows
    public static Person toPerson(PersonRequest request) {
        Person person = new Person();
        person.setName(name_format(request.getName()));
        person.setBirthDate(date_format(request.getBirthDate()));
        return person;
    }

    public static PersonRequest responseMinReturn(Person response)
    {
        PersonRequest person = new PersonRequest();
        person.setPersonId(response.getPersonId());
        person.setName(name_format(response.getName()));
        person.setBirthDate(String.valueOf(response.getBirthDate()));
        return person;
    }

    public static PersonResponse responseFullReturn(Person response)
    {
        PersonResponse person = new PersonResponse();
        person.setPersonId(response.getPersonId());
        person.setName(response.getName());
        person.setBirthDate(String.valueOf(response.getBirthDate()));
        person.setPrimaryAddress(response.getPrimaryAddress());
        person.setAddresses(response.getAddresses());
        return person;
    }
}
