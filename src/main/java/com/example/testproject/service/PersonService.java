package com.example.testproject.service;

import org.springframework.stereotype.Service;

import com.example.testproject.dto.PersonDto;
import com.example.testproject.dto.PersonListResponseDto;
import com.example.testproject.exception.BadRequestException;

@Service
public interface PersonService {

	void create(PersonDto personDto) throws BadRequestException;

	void update(PersonDto personDto, Long id) throws BadRequestException;

	PersonListResponseDto getAllPersons(Integer pageNo, Integer pageSize);

	PersonDto findById(Long id) throws BadRequestException;

	PersonDto deleteById(Long id) throws BadRequestException;

}
