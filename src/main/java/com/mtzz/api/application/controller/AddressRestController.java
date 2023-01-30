package com.mtzz.api.application.controller;

import com.mtzz.api.application.entities.Address;
import com.mtzz.api.application.controller.DTO.request.AddressRequest;
import com.mtzz.api.application.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(path = "/addresses")
public class AddressRestController
{
    @Autowired
    private AddressService address_service;


    @PostMapping(path = "/add")
    public ResponseEntity<Address> add_address(@RequestBody AddressRequest addressRequest)
    {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(address_service.add_address(addressRequest));
    }


    @GetMapping(path = "/person-id/{person_id}")
    public ResponseEntity<List<Address>> persons_address(@PathVariable Long person_id)
    {
        return ResponseEntity.ok().body(address_service.persons_addresses(person_id));
    }
}
