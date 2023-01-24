package com.example.testproject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.testproject.model.PersonEntity;
import com.example.testproject.repository.PersonRepository;

@DataJpaTest
public class PersonRepositoryTest {

	@Autowired
	PersonRepository personRepository;

	@Test
	public void getPersonTest() {

		PersonEntity person = personRepository.findById(1L).get();

		Assertions.assertThat(person.getId()).isEqualTo(1L);

	}

}
