package com.example.testproject.exception;

import javax.validation.UnexpectedTypeException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<String> handle(BadRequestException ex) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UnexpectedTypeException.class)
	public ResponseEntity<String> handleForRegexPattern(UnexpectedTypeException ex) {
		return new ResponseEntity<String>("Phone number is not valid. Phone number should be 10 digit. ",
				HttpStatus.BAD_REQUEST);
	}

}
