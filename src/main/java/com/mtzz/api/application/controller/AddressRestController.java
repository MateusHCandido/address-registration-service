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
    private AddressService addressService;


    @PostMapping(path = "/add")
    public ResponseEntity<Address> addAddress(@RequestBody AddressRequest addressRequest)
    {
        Address address = addressService.addAddress(addressRequest);
        //wait response
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(address);
    }


    @GetMapping(path = "/person-id/{person_id}")
    public ResponseEntity<List<Address>> personsAddress(@PathVariable Long person_id)
    {
        return ResponseEntity.ok().body(addressService.personsAddresses(person_id));
    }
}
