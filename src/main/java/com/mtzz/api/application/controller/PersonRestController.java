package com.mtzz.api.application.controller;

import com.mtzz.api.application.controller.DTO.request.PersonRequest;
import com.mtzz.api.application.entities.Person;
import com.mtzz.api.application.mapper.PersonMapper;
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
    public ResponseEntity<Person> addPerson(@RequestBody PersonRequest person_request)
    {
         Person person = PersonMapper.toPerson(person_request);
         personService.addPerson(person);
         return ResponseEntity.status(HttpStatus.CREATED).body(person);
    }

    @GetMapping(path = "/find-by-id/{person_id}")
    public ResponseEntity<Object> findById(@PathVariable Long person_id)
    {
        Object person_localized = personService.findById(person_id);
        return ResponseEntity.ok().body(person_localized);
    }

    @GetMapping
    public ResponseEntity<List<Person>> findAll()
    {
        return ResponseEntity.ok(personService.find_all());
    }

    @PutMapping(path = "/update-data/{person_id}")
    public ResponseEntity<Person> updatePersonData(@PathVariable Long person_id, @RequestBody PersonRequest personRequest)
    {
        personService.updateDataPerson(personRequest, person_id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/person-id/{person_id}/address-id/{pa_id}")
    public ResponseEntity<Person> enterPrimaryAddress(@PathVariable Long person_id, @PathVariable Long pa_id)
    {
        personService.enterPrimaryAddress(person_id, pa_id);
        return ResponseEntity.noContent().build();
    }

}
