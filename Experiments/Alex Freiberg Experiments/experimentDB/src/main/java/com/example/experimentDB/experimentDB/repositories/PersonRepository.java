package com.example.experimentDB.experimentDB.repositories;

import com.example.experimentDB.experimentDB.domain.Person;
import org.springframework.data.repository.CrudRepository;


public interface PersonRepository extends CrudRepository<Person, Long>{
}
