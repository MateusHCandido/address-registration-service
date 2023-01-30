package com.mtzz.api.application.service;

import com.mtzz.api.application.entities.Address;
import com.mtzz.api.application.controller.DTO.request.PersonRequest;
import com.mtzz.api.application.entities.Person;
import com.mtzz.api.application.mapper.PersonMapper;
import com.mtzz.api.application.repository.AddressRepository;
import com.mtzz.api.application.repository.PersonRepository;
import com.mtzz.api.application.service.exception.*;
import com.mtzz.api.application.util.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class PersonService
{
    @Autowired
    private PersonRepository person_repository;

    @Autowired
    private AddressRepository address_repository;


    public Person add_person(Person person_request){
        if (person_request.getName().equals(""))
        {
            throw new PersonCreationException("Unable to create a person with blank name");
        }
        else if(person_request.getBirth_date().equals(""))
        {
            throw new PersonCreationException("Unable to create a person with blank date");
        }
        return person_repository.save(person_request);
    }

    public void update_data_person(PersonRequest person_request_data, Long person_id)
    {
        Person person = person_repository.findById(person_id).orElseThrow(
                () -> new PersonNotFoundException(person_id));
        person.setName(DataFormat.name_format(person_request_data.getName()));

        if(person.getName().equals(""))
        {
            throw new DataUpdateException("Unable to update to a blank name or null.");
        }
        else if(person_request_data.getBirth_date() != null)
        {
            throw new DataUpdateException("Unable to update a person's date of birth");
        }

        person_repository.save(person);
    }

    public Object findBy_id(Long person_id)
    {
        //If the person has no registered address, only his id, name and birthday will be launched as a search.
        Person person = person_repository.findById(person_id).orElseThrow(
                () -> new PersonNotFoundException(person_id));
        if(person.getPrimary_address() == null)
        {
            return PersonMapper.response_min_return(person);
        }
        return PersonMapper.response_full_return(person);
    }

    public List<Person> find_all()
    {
        List<Person> persons = person_repository.findAll();
        if(persons.isEmpty())
        {
            throw new EmptyCollectionException(persons);
        }
        return person_repository.findAll();
    }

    public void enter_primary_address(Long person_id, Long pa_id)
    {
        //instantiated the address just to check if the country exists
        Address address = address_repository.findById(pa_id).orElseThrow(() -> new AddressNotFoundException(pa_id));
        Person person = person_repository.findById(person_id).orElseThrow(() -> new PersonNotFoundException(person_id));
        person.setPrimary_address(pa_id);
        person_repository.save(person);
    }
}
