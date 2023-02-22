package com.mtzz.api.application.service;

import com.mtzz.api.application.entities.Address;
import com.mtzz.api.application.controller.DTO.request.AddressRequest;
import com.mtzz.api.application.entities.Person;
import com.mtzz.api.application.mapper.AddressMapper;
import com.mtzz.api.application.repository.AddressRepository;
import com.mtzz.api.application.repository.PersonRepository;
import com.mtzz.api.application.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {


    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;


    public Boolean checkIfAddressExists(AddressRequest addressRequest) {
        Address searchReturn = addressRepository.findByCityAndCepAndStreetAddressAndNumber
                                                        (addressRequest.getCity(), addressRequest.getCep(),
                                                         addressRequest.getStreetAddress(), addressRequest.getNumber());
        if(searchReturn != null)
        {
            throw new ExistingAddressException("The displayed address already exists");
        }
        return true;
    }

    public Address addAddress(AddressRequest addressRequest)
    {
        Address address = AddressMapper.toAddress(addressRequest);
        checkIfAddressExists(addressRequest);
        addressRepository.save(address);
        return address;
    }

    //standby
    public List<Address> personsAddresses(Long personId){
        Person person = personRepository.findById(personId).orElseThrow(() -> new PersonNotFoundException(personId));
        List<Address> addresses = addressRepository.findAddressByPerson(person);
        if(addresses.isEmpty())
        {
            throw new EmptyCollectionException(person);
        }
        return addresses;
    }

    public Address findById(Long addressId){
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException(addressId));
    }
}
