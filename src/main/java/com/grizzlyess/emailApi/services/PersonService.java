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

        
        String email = generateEmail(firstName, lastName, department);
        String password = UUID.randomUUID().toString().substring(0, 8);
        Person person = new Person(firstName, lastName,  email, password);
        person.setDepartment(department);
        switch (department) {
            case SALES->{
                person.setMailboxCapacity(10000);
            }

            case DEVELOPMENT->{
                person.setMailboxCapacity(15000);
            }

            case ACCOUNT->{
                person.setMailboxCapacity(5000);
            }
        }
        log.info("Person created: " + person.toString());
        return personRepository.save(person);
        
    }

    public String generateEmail(String firstName, String lastName, DepartmentType department){
        
        String dominio = "@" + department.toString().toLowerCase() + ".com";
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + dominio;
        int contador = 1;

        while (personRepository.existsByEmail(email)) {
            email = email + contador + dominio;
            contador++;
        }

        return email;

    }

    public Person updatePassword(String email, String password, String newPassword) {

        Person person = personRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Person not found"));
        if (personRepository.existsByEmail(email)) {
            throw new RuntimeException("Person not found");
        }
        if (!person.getPassword().equals(password)) {
            throw new RuntimeException("Incorrect password");
        }
        person.setPassword(newPassword);
        log.info("Password updated for person: " + person.toString());
        return personRepository.save(person);
    }

    

}
