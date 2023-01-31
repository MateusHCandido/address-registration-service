package com.mtzz.api.application.service;

import com.mtzz.api.application.entities.Address;
import com.mtzz.api.application.controller.DTO.request.AddressRequest;
import com.mtzz.api.application.entities.Person;
import com.mtzz.api.application.mapper.AddressMapper;
import com.mtzz.api.application.repository.AddressRepository;
import com.mtzz.api.application.repository.PersonRepository;
import com.mtzz.api.application.service.exception.AddressNotFoundException;
import com.mtzz.api.application.service.exception.EmptyCollectionException;
import com.mtzz.api.application.service.exception.PersonNotFoundException;
import com.mtzz.api.application.util.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AddressService extends DataFormat
{
    @Autowired
    AddressRepository addressRepository;

    @Autowired
    PersonRepository personRepository;


    public Address addAddress(AddressRequest addressRequest)
    {
        Person person = personRepository.findById(addressRequest.getPersonId())
                .orElseThrow(() -> new PersonNotFoundException(addressRequest.getPersonId()));
        Address address = AddressMapper.toAddress(addressRequest);
        address.setPerson(person);
        if(person.getAddresses().isEmpty())
        {
            //if the person has no registered address, the first registered address will be the main
            person.setPrimaryAddress(1L);
            personRepository.save(person);
        }
        return addressRepository.save(address);
    }

    public List<Address> persons_addresses(Long personId){
        Person person = personRepository.findById(personId).orElseThrow(() -> new PersonNotFoundException(personId));
        List<Address> addresses = addressRepository.findAddressByPerson(person);
        if(addresses.isEmpty())
        {
            throw new EmptyCollectionException("The person does not have registered addresses");
        }
        return addresses;
    }

    public Address findBy_id(Long addressId){
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException(addressId));
    }
}
