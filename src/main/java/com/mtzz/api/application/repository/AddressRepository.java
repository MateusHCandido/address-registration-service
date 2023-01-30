package com.mtzz.api.application.repository;

import com.mtzz.api.application.entities.Address;
import com.mtzz.api.application.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long>
{
    List<Address> findAddressByPerson(Person person);
}
