package com.example.testproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.testproject.model.PersonEntity;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

	PersonEntity findByEmail(String email);

}
