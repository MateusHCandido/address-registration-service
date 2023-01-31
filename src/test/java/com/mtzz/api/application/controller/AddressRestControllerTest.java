package com.mtzz.api.application.controller;


import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mtzz.api.application.controller.DTO.request.AddressRequest;
import com.mtzz.api.application.controller.DTO.request.PersonRequest;
import com.mtzz.api.application.entities.Address;
import com.mtzz.api.application.entities.Person;
import com.mtzz.api.application.repository.PersonRepository;
import com.mtzz.api.application.service.AddressService;
import com.mtzz.api.application.service.PersonService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

@WebMvcTest
public class AddressRestControllerTest
{
    @Autowired
    private AddressRestController addressRestController;

    @MockBean
    private AddressService addressService;

    @MockBean
    PersonRepository personRepository;

    @MockBean
    PersonService personService;

    @BeforeEach
    public void setup()
    {
        standaloneSetup(addressRestController);
    }

    @Test
    public void createNewAddressSuccessfully_WhenToCallAddAddress()
    {
        //scenery
        Person person = new Person(1L, "Mateus Henrique", "1999-10-17");
        AddressRequest address = new AddressRequest(1L, "Rua dos Alfeneiros", "55555-555"
                , "4", "London Recifense", 1L);
        //action
        personService.addPerson(person);
        when(this.addressService.addAddress(address))
                .thenReturn(new Address(1L, "Rua dos Alfeneiros", "55555-555"
                        , "4", "London Recifense", person));

        given()
                .accept(ContentType.JSON)
                .when()
                .post("/addresses/add")
                .then()
                .status(HttpStatus.NOT_FOUND);

        //validation
    }
}
