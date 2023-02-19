package com.mtzz.api.application.service;

import com.mtzz.api.application.controller.DTO.request.AddressRequest;
import com.mtzz.api.application.controller.DTO.request.PersonRequest;
import com.mtzz.api.application.entities.Address;
import com.mtzz.api.application.entities.Person;
import com.mtzz.api.application.repository.AddressRepository;
import com.mtzz.api.application.repository.PersonRepository;
import com.mtzz.api.application.service.exception.*;
import com.mtzz.api.builders.AddressBuilder;
import com.mtzz.api.builders.PersonBuilder;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class PersonServiceTest
{
    @TestConfiguration
    static class PersonServiceTestConfiguration
    {
        @Bean
        public PersonService personService() {return new PersonService();}

        @Bean
        public AddressService addressService() {return new AddressService();}
    }


    @Autowired
    private PersonService service;

    @Autowired
    private AddressService addressService;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private AddressRepository addressRepository;


    @Test
    public void should_create_person()
    {
        PersonRequest personRequest = PersonBuilder.generatePerson().now();
        Person person = new Person();

        when(service.addPerson(personRequest)).thenReturn(person);
        person = service.addPerson(personRequest);
        assertNotNull(person);
    }


    @Test(expected = PersonCreationException.class)
    public void should_return_error_in_name_field()
    {
        PersonRequest personRequest = PersonBuilder.generatePerson().now();
        personRequest.setName("");
        Person person = service.addPerson(personRequest);
    }


    @Test(expected = PersonCreationException.class)
    public void should_return_error_in_date_field()
    {
        PersonRequest personRequest = PersonBuilder.generatePerson().now();
        personRequest.setBirthDate("");
        Person person = service.addPerson(personRequest);
    }


    @Test
    public void should_update_data_of_person()
    {
        PersonRequest personRequest = PersonBuilder.generatePerson().now();
        PersonRequest personForUpdate = new PersonRequest(1L, "Marcos Garcia", null);
        Person person = service.addPerson(personRequest);
        when(personRepository.findById(person.getPersonId())).thenReturn(Optional.of(person));
        service.updateDataPerson(personForUpdate, person.getPersonId());
        assertEquals("MARCOS GARCIA", person.getName());
    }

    @Test(expected = DataUpdateException.class)
    public void should_generate_error_trying_to_update_date()
    {
        PersonRequest personRequest = PersonBuilder.generatePerson().now();
        PersonRequest personForUpdate = new PersonRequest(1L, "Marcos Garcia", "1999-10-17");
        Person person = service.addPerson(personRequest);
        when(personRepository.findById(person.getPersonId())).thenReturn(Optional.of(person));
        service.updateDataPerson(personForUpdate, person.getPersonId());
    }


    @Test(expected = DataUpdateException.class)
    public void should_generate_error_trying_to_update_name()
    {
        PersonRequest personRequest = PersonBuilder.generatePerson().now();
        PersonRequest personForUpdate = new PersonRequest(1L, "", null);
        Person person = service.addPerson(personRequest);
        when(personRepository.findById(person.getPersonId())).thenReturn(Optional.of(person));
        service.updateDataPerson(personForUpdate, person.getPersonId());
    }


    @Test(expected = DataUpdateException.class)
    public void should_throw_error_when_trying_to_update_null_name()
    {
        PersonRequest personRequest = PersonBuilder.generatePerson().now();
        PersonRequest personForUpdate = new PersonRequest(1L, null, null);
        Person person = service.addPerson(personRequest);
        when(personRepository.findById(person.getPersonId())).thenReturn(Optional.of(person));
        service.updateDataPerson(personForUpdate, person.getPersonId());
    }


    @Test(expected = PersonNotFoundException.class)
    public void should_throw_person_not_found_error()
    {
        PersonRequest personForUpdate = new PersonRequest(1L, "test", null);
        service.updateDataPerson(personForUpdate, 2L); // 2L is an object never registered in the bank
    }


    @Test
    public void should_generate_find_person_returning_minimal_fields()
    {
        PersonRequest personRequest = PersonBuilder.generatePerson().now();
        AddressRequest addressRequest = AddressBuilder.generateAddress().now();
        Address address = addressService.addAddress(addressRequest);


        Person person = service.addPerson(personRequest);


        when(personRepository.findById(person.getPersonId())).thenReturn(Optional.of(person));
        Object personFound = service.findById(person.getPersonId());

        verify(personRepository, times(1)).findById(person.getPersonId());
    }


    @Test
    public void should_generate_find_person_returning_all_fields()
    {
        PersonRequest personRequest = PersonBuilder.generatePerson().now();
        Person person = service.addPerson(personRequest);

        person.setPrimaryAddress(1L);
        when(personRepository.findById(person.getPersonId())).thenReturn(Optional.of(person));
        Object personFound = service.findById(person.getPersonId());

        verify(personRepository,times(1)).findById(person.getPersonId());
    }


    @Test(expected = PersonNotFoundException.class)
    public void should_return_a_person_not_found_and_the_id()
    {
        Object personNotFound = service.findById(1L); //object never registered in the bank
    }


    @Test
    public void should_return_list_of_people()
    {
        PersonRequest personRequest = PersonBuilder.generatePerson().now();
        Person person = service.addPerson(personRequest);

        when(personRepository.findAll()).thenReturn(new ArrayList(Collections.singleton(person)));
        List<Person> personList = service.findAll();

        boolean validation = !personList.isEmpty();
        assertTrue(validation);
    }

    @Test(expected = EmptyCollectionException.class)
    public void should_return_empty_list_of_people()
    {
        when(service.findAll()).thenReturn(personRepository.findAll());
        service.findAll();
    }


    @Test
    public void should_add_address_in_person()
    {
        PersonRequest personRequest = PersonBuilder.generatePerson().now();
        AddressRequest addressRequest = AddressBuilder.generateAddress().now();
        Person person = service.addPerson(personRequest);
        Address address = addressService.addAddress(addressRequest);

       when(personRepository.findById(person.getPersonId())).thenReturn(Optional.of(person));
       when(addressRepository.findById(address.getAddressId())).thenReturn(Optional.of(address));
       service.addAddressInPerson(person.getPersonId(), address.getAddressId());

       boolean validation = !person.getAddresses().isEmpty();
       assertTrue(validation);
       assertNotNull(person.getPrimaryAddress());
    }

    @Test(expected = PersonNotFoundException.class)
    public void should_return_person_not_located_when_adding_address_in_person()
    {
        //not generated person object
        AddressRequest addressRequest = AddressBuilder.generateAddress().now();
        Address address = addressService.addAddress(addressRequest);
        when(addressRepository.findById(address.getAddressId())).thenReturn(Optional.of(address));
        service.addAddressInPerson(1L, address.getAddressId());
    }


    @Test(expected = AddressNotFoundException.class)
    public void should_return_address_not_located_when_adding_address_in_person()
    {
        PersonRequest personRequest = PersonBuilder.generatePerson().now();
        Person person = service.addPerson(personRequest);
        when(personRepository.findById(person.getPersonId())).thenReturn(Optional.of(person));
        service.addAddressInPerson(person.getPersonId(), 1L);
    }

    @Test
    public void should_add_a_main_address_to_person()
    {
        PersonRequest personRequest = PersonBuilder.generatePerson().now();
        AddressRequest addressRequest = AddressBuilder.generateAddress().now();
        Person person = service.addPerson(personRequest);
        Address address = addressService.addAddress(addressRequest);

        when(personRepository.findById(person.getPersonId())).thenReturn(Optional.of(person));
        when(addressRepository.findById(address.getAddressId())).thenReturn(Optional.of(address));
        service.enterMainAddress(person.getPersonId(), address.getAddressId());

        assertNotNull(person.getPrimaryAddress());
    }


    @Test(expected = PersonNotFoundException.class)
    public void should_return_error_person_not_found_when_trying_to_add_a_primary_address_to_person()
    {
        AddressRequest addressRequest = AddressBuilder.generateAddress().now();
        Address address = addressService.addAddress(addressRequest);

        when(addressRepository.findById(address.getAddressId())).thenReturn(Optional.of(address));
        service.enterMainAddress(1L, address.getAddressId());
    }


    @Test(expected = AddressNotFoundException.class)
    public void should_return_error_address_not_found_when_trying_to_add_a_primary_address_to_person()
    {
        PersonRequest personRequest = PersonBuilder.generatePerson().now();
        Person person = service.addPerson(personRequest);

        when(personRepository.findById(person.getPersonId())).thenReturn(Optional.of(person));
        service.enterMainAddress(person.getPersonId(), 1L);
    }
}
