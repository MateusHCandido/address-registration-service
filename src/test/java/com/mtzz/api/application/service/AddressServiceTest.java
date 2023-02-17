package com.mtzz.api.application.service;

import com.mtzz.api.application.controller.DTO.request.AddressRequest;
import com.mtzz.api.application.entities.Address;
import com.mtzz.api.application.repository.AddressRepository;
import com.mtzz.api.application.repository.PersonRepository;
import com.mtzz.api.application.service.exception.AddressCreationFailureException;
import com.mtzz.api.application.service.exception.ExistingAddressException;
import com.mtzz.api.builders.AddressBuilder;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;


import static org.junit.jupiter.api.Assertions.assertNotNull;



import static org.mockito.Mockito.*;

import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;


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
    }


    @Autowired
    private AddressService service;

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private AddressRequest addressRequest;


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
       when(addressRepository.findByCityAndCepAndStreetAddressAndNumber(addressRequest.getCity(), addressRequest.getCep()
                                                                   ,addressRequest.getStreetAddress(), addressRequest.getNumber()))
                .thenReturn(address);
        when(service.addAddress(addressRequest)).thenReturn(address);
    }

    @Test
    public void should_check_if_the_return_is_true_and_create_address()
    {
        AddressRequest addressRequest = AddressBuilder.generateAddress().now();
        when(addressRepository.findByCityAndCepAndStreetAddressAndNumber(addressRequest.getCity(), addressRequest.getCep()
                ,addressRequest.getStreetAddress(), addressRequest.getNumber()))
                .thenReturn(null);
        boolean validation = service.checkIfAddressExists(addressRequest);
        Address address = service.addAddress(addressRequest);
        verify(addressRepository, times(2)).findByCityAndCepAndStreetAddressAndNumber(addressRequest.getCity(), addressRequest.getCep()
                ,addressRequest.getStreetAddress(), addressRequest.getNumber());
        Assertions.assertTrue(validation);
        Assertions.assertNotNull(address);
    }

    @Test(expected = ExistingAddressException.class)
    public void should_return_null_value_and_not_create_address()
    {
        AddressRequest addressRequest = AddressBuilder.generateAddress().now();
        Address address = service.addAddress(addressRequest);

        when(addressRepository.findByCityAndCepAndStreetAddressAndNumber(addressRequest.getCity(), addressRequest.getCep()
                ,addressRequest.getStreetAddress(), addressRequest.getNumber()))
                .thenReturn(address);
        when(service.addAddress(addressRequest)).thenReturn(address);
        verify(addressRepository, times(2)).findByCityAndCepAndStreetAddressAndNumber(addressRequest.getCity(), addressRequest.getCep()
                ,addressRequest.getStreetAddress(), addressRequest.getNumber());
        verify(service,times(2)).addAddress(addressRequest);
        Address failAddress = service.addAddress(addressRequest);




    }
}