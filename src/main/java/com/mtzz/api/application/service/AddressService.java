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
    AddressRepository address_repository;

    @Autowired
    PersonRepository person_repository;


    public Address add_address(AddressRequest address_request)
    {
        Person person = person_repository.findById(address_request.getPerson_id())
                .orElseThrow(() -> new PersonNotFoundException(address_request.getPerson_id()));
        Address address = AddressMapper.toAddress(address_request);
        address.setPerson(person);
        if(person.getAddresses().isEmpty())
        {
            //if the person has no registered address, the first registered address will be the main
            person.setPrimary_address(1L);
            person_repository.save(person);
        }
        return address_repository.save(address);
    }

    public List<Address> persons_addresses(Long person_id){
        Person person = person_repository.findById(person_id).orElseThrow(() -> new PersonNotFoundException(person_id));
        List<Address> addresses = address_repository.findAddressByPerson(person);
        if(addresses.isEmpty())
        {
            throw new EmptyCollectionException("The person does not have registered addresses");
        }
        return addresses;
    }

    public Address findBy_id(Long address_id){
        return address_repository.findById(address_id)
                .orElseThrow(() -> new AddressNotFoundException(address_id));
    }
}
