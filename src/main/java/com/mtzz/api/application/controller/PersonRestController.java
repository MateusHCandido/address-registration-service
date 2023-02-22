package com.mtzz.api.application.controller;

import com.mtzz.api.application.controller.DTO.request.PersonRequest;
import com.mtzz.api.application.entities.Person;
import com.mtzz.api.application.service.PersonService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/persons")
public class PersonRestController
{
    @Autowired
    private PersonService personService;


    @SneakyThrows
    @PostMapping(path = "/add")
    public ResponseEntity<Person> addPerson(@RequestBody PersonRequest personRequest)
    {
        Person person = personService.addPerson(personRequest);
         return ResponseEntity.status(HttpStatus.CREATED).body(person);
    }

    @GetMapping(path = "/find-by-id/{personId}")
    public ResponseEntity<Object> findById(@PathVariable Long personId)
    {
        Object personLocalized = personService.findById(personId);
        return ResponseEntity.ok().body(personLocalized);
    }

    @GetMapping
    public ResponseEntity<List<Person>> findAll()
    {
        return ResponseEntity.ok(personService.findAll());
    }

    @PutMapping(path = "/update-data/{personId}")
    public ResponseEntity<Person> updatePersonData(@PathVariable Long personId, @RequestBody PersonRequest personRequest)
    {
        personService.updateDataPerson(personRequest, personId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/register-address/{addressId}/to/{personId}")
    public ResponseEntity<Person> addAddressInPerson( @PathVariable Long addressId, @PathVariable Long personId)
    {
        personService.addAddressInPerson(personId, addressId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/person-id/{personId}/address-id/{paId}")
    public ResponseEntity<Person> enterPrimaryAddress(@PathVariable Long personId, @PathVariable Long paId)
    {
        personService.enterMainAddress(personId, paId);
        return ResponseEntity.noContent().build();
    }


}
