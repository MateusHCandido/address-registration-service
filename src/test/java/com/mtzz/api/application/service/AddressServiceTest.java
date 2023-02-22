package com.mtzz.api.application.service;

import com.mtzz.api.application.controller.DTO.request.AddressRequest;
import com.mtzz.api.application.controller.DTO.request.PersonRequest;
import com.mtzz.api.application.entities.Address;
import com.mtzz.api.application.entities.Person;
import com.mtzz.api.application.repository.AddressRepository;
import com.mtzz.api.application.repository.PersonRepository;
import com.mtzz.api.application.service.exception.AddressNotFoundException;
import com.mtzz.api.application.service.exception.EmptyCollectionException;
import com.mtzz.api.application.service.exception.ExistingAddressException;
import com.mtzz.api.builders.AddressBuilder;
import com.mtzz.api.builders.PersonBuilder;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;


import static org.junit.jupiter.api.Assertions.assertNotNull;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;


@RunWith(SpringRunner.class)
public class AddressServiceTest {

    @TestConfiguration
    static class AddressServiceTestConfiguration
    {
        @Bean
        public AddressService addressService()
        {
            return new AddressService();
        }

        @Bean
        public PersonService personService(){return new PersonService();}

    }


    @Autowired
    private AddressService service;

    @Autowired
    private PersonService personService;

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private PersonRepository personRepository;


    @Test
    public void should_create_address()
    {
        AddressRequest addressRequest = AddressBuilder.generateAddress().now();
        Address address = new Address();

        when(service.addAddress(addressRequest)).thenReturn(address);
        address = service.addAddress(addressRequest);

        assertNotNull(address);
    }


    @Test(expected = ExistingAddressException.class)
    public void should_give_address_creation_error()
    {
        AddressRequest addressRequest = AddressBuilder.generateAddress().now();
        Address address = service.addAddress(addressRequest);
       when(addressRepository.
               findByCityAndCepAndStreetAddressAndNumber(addressRequest.getCity()
                       ,addressRequest.getCep()
                       ,addressRequest.getStreetAddress()
                       ,addressRequest.getNumber())).thenReturn(address);

       when(service.addAddress(addressRequest)).thenReturn(address);
    }


    @Test
    public void should_check_if_the_return_is_true_and_create_address()
    {
        AddressRequest addressRequest = AddressBuilder.generateAddress().now();

        when(addressRepository
                .findByCityAndCepAndStreetAddressAndNumber(addressRequest.getCity()
                        ,addressRequest.getCep()
                        ,addressRequest.getStreetAddress()
                        ,addressRequest.getNumber())).thenReturn(null);
        boolean validation = service.checkIfAddressExists(addressRequest);
        Address address = service.addAddress(addressRequest);
        verify(addressRepository, times(2))
                .findByCityAndCepAndStreetAddressAndNumber(addressRequest.getCity()
                        ,addressRequest.getCep()
                        ,addressRequest.getStreetAddress()
                        ,addressRequest.getNumber());

        Assertions.assertTrue(validation);
        Assertions.assertNotNull(address);
    }


    @Test(expected = ExistingAddressException.class)
    public void should_return_null_value_and_not_create_address()
    {
        AddressRequest addressRequest = AddressBuilder.generateAddress().now();
        Address address = service.addAddress(addressRequest);

        when(addressRepository.
                findByCityAndCepAndStreetAddressAndNumber(addressRequest.getCity()
                        ,addressRequest.getCep()
                        ,addressRequest.getStreetAddress()
                        ,addressRequest.getNumber())).thenReturn(address);
        when(service.addAddress(addressRequest)).thenReturn(address);

        Address failAddress = service.addAddress(addressRequest);
    }


    @Test
    public void should_list_addresses_of_the_person()
    {
        PersonRequest personRequest = PersonBuilder.generatePerson().now();
        AddressRequest addressRequest = AddressBuilder.generateAddress().now();
        Person person = personService.addPerson(personRequest);
        Address address = service.addAddress(addressRequest);

        when(personRepository.findById(1L)).thenReturn(Optional.ofNullable(person));
        when(addressRepository.findById(1L)).thenReturn(Optional.ofNullable(address));
        personService.addAddressInPerson(1L, 1L);

        when(personRepository.findById(1L)).thenReturn(Optional.ofNullable(person));
        when(addressRepository.findAddressByPerson(person)).thenReturn(new ArrayList(Collections.singleton(address)));
        List<Address> addressList = service.personsAddresses(1L);

        boolean validation = !addressList.isEmpty();
        assertTrue(validation);
    }


    @Test(expected = EmptyCollectionException.class)
    public void should_not_list_addresses_of_the_person()
    {
        PersonRequest personRequest = PersonBuilder.generatePerson().now();
        Person person = personService.addPerson(personRequest);
        List<Address> addresses = new ArrayList<>();

        when(personRepository.findById(person.getPersonId())).thenReturn(Optional.of(person));
        when(service.personsAddresses(person.getPersonId())).thenReturn(addresses);
    }


    @Test
    public void should_find_address_by_id()
    {
        AddressRequest addressRequest = AddressBuilder.generateAddress().now();
        Address address = service.addAddress(addressRequest);
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        Address addressFound = service.findById(1L);

        assertNotNull(addressFound);
    }


    @Test(expected = AddressNotFoundException.class)
    public void should_not_find_address_by_id()
    {
        AddressRequest addressRequest = AddressBuilder.generateAddress().now();
        Address address = service.addAddress(addressRequest);
        when(addressRepository.findById(2L)).thenReturn(Optional.of(address));
        Address addressFound = service.findById(1L);

        assertNotNull(addressFound);
    }
}