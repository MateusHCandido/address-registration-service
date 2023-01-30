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
        person.setBirth_date(date_format(request.getBirth_date()));
        return person;
    }

    public static PersonRequest response_min_return(Person response)
    {
        PersonRequest person = new PersonRequest();
        person.setPerson_id(response.getPerson_id());
        person.setName(name_format(response.getName()));
        person.setBirth_date(String.valueOf(response.getBirth_date()));
        return person;
    }

    public static PersonResponse response_full_return(Person response)
    {
        PersonResponse person = new PersonResponse();
        person.setPerson_id(response.getPerson_id());
        person.setName(response.getName());
        person.setBirth_date(String.valueOf(response.getBirth_date()));
        person.setPrimary_address(response.getPrimary_address());
        person.setAddresses(response.getAddresses());
        return person;
    }
}
