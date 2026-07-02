package com.grizzlyess.emailApi.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grizzlyess.emailApi.entity.Person;

public interface PersonRepository extends JpaRepository<Person, UUID> {
    boolean existsByEmail(String email);
    Optional<Person> findByEmail(String email);
    
}
