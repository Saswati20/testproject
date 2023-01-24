package com.example.testproject.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.testproject.dto.ApiResponse;
import com.example.testproject.dto.PersonDto;
import com.example.testproject.dto.PersonListResponseDto;
import com.example.testproject.exception.BadRequestException;
import com.example.testproject.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	PersonService personService;

	@PostMapping(value = "/add")
	public ResponseEntity<ApiResponse> addUser(@RequestBody PersonDto personDto) throws BadRequestException {
		personService.create(personDto);
		ApiResponse apiResponse = new ApiResponse("Person's profile added successfully");
		return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
	}

	@Transactional
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<ApiResponse> updateUser(@RequestBody PersonDto personDto, @PathVariable(value = "id") Long id)
			throws BadRequestException {
		personDto.setId(id);
		personService.update(personDto, id);
		ApiResponse apiResponse = new ApiResponse("Person's profile updated successfully");
		return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/all")
	public ResponseEntity<PersonListResponseDto> getAll(@RequestParam(defaultValue = "1") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize) {
		PersonListResponseDto userListResponse = personService.getAllPersons(pageNo, pageSize);
		return new ResponseEntity<>(userListResponse, HttpStatus.OK);
	}

	@GetMapping(value = "/view/{id}")
	public ResponseEntity<PersonDto> getPersonById(@PathVariable Long id) throws BadRequestException {
		PersonDto newUserRequest = personService.findById(id);
		return new ResponseEntity<>(newUserRequest, HttpStatus.OK);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<ApiResponse> deleteUserById(@PathVariable Long id) throws BadRequestException {
		personService.deleteById(id);
		ApiResponse apiResponse = new ApiResponse("Person's profile deleted successfully");
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);

	}

}
