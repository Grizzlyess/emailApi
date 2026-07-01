package com.grizzlyess.emailApi.services;

import com.grizzlyess.emailApi.entity.Person;
import com.grizzlyess.emailApi.repository.PersonRepository;

public class PersonService {
    public PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person create(Person person) {
        return personRepository.save(person);
    }

    

}
