package com.mtzz.api.application.repository;

import com.mtzz.api.application.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;




public interface PersonRepository extends JpaRepository<Person, Long>
{

}
