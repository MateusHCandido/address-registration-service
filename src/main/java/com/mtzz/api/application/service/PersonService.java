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

    @Autowired
    private AddressService addressService;


    public Person addPerson(PersonRequest personRequest){
        if (personRequest.getName().equals(""))
        {
            throw new PersonCreationException("Unable to create a person with blank name");
        }
        else if(personRequest.getBirthDate().equals(""))
        {
            throw new PersonCreationException("Unable to create a person with blank date");
        }
        Person person = PersonMapper.toPerson(personRequest);
        personRepository.save(person);
        return person;
    }

    public void updateDataPerson(PersonRequest personRequestData, Long personId)
    {
        Person person = personRepository.findById(personId).orElseThrow(
                () -> new PersonNotFoundException(personId));
        try
        {
            if(personRequestData.getName().equals(""))
            {
                throw new DataUpdateException("Unable to update to a blank name or null.");
            }
            else if(personRequestData.getBirthDate() != null)
            {
                throw new DataUpdateException("Unable to update a person's date of birth");
            }

        }catch (NullPointerException nullPointerException)
        {
            throw new DataUpdateException("Unable to update to a blank name or null.");
        }

        person.setName(DataFormat.name_format(personRequestData.getName()));
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

    public void addAddressInPerson(Long personId, Long addressId)
    {
        Person person = personRepository.findById(personId).orElseThrow(() -> new PersonNotFoundException(personId));
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new AddressNotFoundException(addressId));
        if(person.getPrimaryAddress() == null)
        {
            person.setPrimaryAddress(address.getAddressId());
        }
        person.getAddresses().add(address);
        personRepository.save(person);
    }

    public List<Person> findAll()
    {
        List<Person> persons = personRepository.findAll();
        if(persons.isEmpty())
        {
            throw new EmptyCollectionException(persons);
        }
        return persons;
    }

    public void enterMainAddress(Long personId, Long paId)
    {
        //instantiated the address just to check if the country exists
        Address address = addressRepository.findById(paId).orElseThrow(() -> new AddressNotFoundException(paId));
        Person person = personRepository.findById(personId).orElseThrow(() -> new PersonNotFoundException(personId));
        person.setPrimaryAddress(paId);
        personRepository.save(person);
    }
}
