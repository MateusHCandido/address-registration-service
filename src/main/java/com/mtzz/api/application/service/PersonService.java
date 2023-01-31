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
    private PersonRepository personRepository;

    @Autowired
    private AddressRepository addressRepository;


    public Person addPerson(Person personRequest){
        if (personRequest.getName().equals(""))
        {
            throw new PersonCreationException("Unable to create a person with blank name");
        }
        else if(personRequest.getBirthDate().equals(""))
        {
            throw new PersonCreationException("Unable to create a person with blank date");
        }
        return personRepository.save(personRequest);
    }

    public void updateDataPerson(PersonRequest person_request_data, Long person_id)
    {
        Person person = personRepository.findById(person_id).orElseThrow(
                () -> new PersonNotFoundException(person_id));
        person.setName(DataFormat.name_format(person_request_data.getName()));

        if(person.getName().equals(""))
        {
            throw new DataUpdateException("Unable to update to a blank name or null.");
        }
        else if(person_request_data.getBirthDate() != null)
        {
            throw new DataUpdateException("Unable to update a person's date of birth");
        }

        personRepository.save(person);
    }

    public Object findById(Long personId)
    {
        //If the person has no registered address, only his id, name and birthday will be launched as a search.
        Person person = personRepository.findById(personId).orElseThrow(
                () -> new PersonNotFoundException(personId));
        if(person.getPrimaryAddress() == null)
        {
            return PersonMapper.responseMinReturn(person);
        }
        return PersonMapper.responseFullReturn(person);
    }

    public List<Person> find_all()
    {
        List<Person> persons = personRepository.findAll();
        if(persons.isEmpty())
        {
            throw new EmptyCollectionException(persons);
        }
        return personRepository.findAll();
    }

    public void enterPrimaryAddress(Long personId, Long paId)
    {
        //instantiated the address just to check if the country exists
        Address address = addressRepository.findById(paId).orElseThrow(() -> new AddressNotFoundException(paId));
        Person person = personRepository.findById(personId).orElseThrow(() -> new PersonNotFoundException(personId));
        person.setPrimaryAddress(paId);
        personRepository.save(person);
    }
}
