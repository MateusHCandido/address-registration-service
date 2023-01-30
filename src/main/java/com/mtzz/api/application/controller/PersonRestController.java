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
    private PersonService person_service;


    @SneakyThrows
    @PostMapping(path = "/add")
    public ResponseEntity<Person> add_person(@RequestBody PersonRequest person_request)
    {
         Person person = PersonMapper.toPerson(person_request);
         person_service.add_person(person);
         return ResponseEntity.status(HttpStatus.CREATED).body(person);
    }

    @GetMapping(path = "/find-by-id/{person_id}")
    public ResponseEntity<Object> findBy_id(@PathVariable Long person_id)
    {
        Object person_localized = person_service.findBy_id(person_id);
        return ResponseEntity.ok().body(person_localized);
    }

    @GetMapping
    public ResponseEntity<List<Person>> find_all()
    {
        return ResponseEntity.ok(person_service.find_all());
    }

    @PutMapping(path = "/update-data/{person_id}")
    public ResponseEntity<Person> update_person_data(@PathVariable Long person_id, @RequestBody PersonRequest personRequest)
    {
        person_service.update_data_person(personRequest, person_id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/person-id/{person_id}/address-id/{pa_id}")
    public ResponseEntity<Person> enter_primary_address(@PathVariable Long person_id, @PathVariable Long pa_id)
    {
        person_service.enter_primary_address(person_id, pa_id);
        return ResponseEntity.noContent().build();
    }

}
