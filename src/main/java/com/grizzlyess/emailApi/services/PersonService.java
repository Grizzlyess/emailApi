package com.grizzlyess.emailApi.services;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.grizzlyess.emailApi.entity.Person;
import com.grizzlyess.emailApi.enums.DepartmentType;
import com.grizzlyess.emailApi.repository.PersonRepository;

@Service
public class PersonService {
    private static final Logger log = LoggerFactory.getLogger(PersonService.class);
    public PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person create(String firstName, String lastName, DepartmentType department) {

        
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@" + department.toString().toLowerCase() + ".com";
        String password = UUID.randomUUID().toString().substring(0, 8);
        Person person = new Person(firstName, lastName, department, email, password);
        log.info("Person created: " + person.toString());
        return personRepository.save(person);
        
    }

    public Person updatePassword(UUID id, String password, String newPassword) {
        Person person = personRepository.findById(id).orElseThrow(() -> new RuntimeException("Person not found"));
        if (!person.getPassword().equals(password)) {
            throw new RuntimeException("Incorrect password");
        }
        person.setPassword(newPassword);
        log.info("Password updated for person: " + person.toString());
        return personRepository.save(person);
    }

    

}
