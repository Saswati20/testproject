package com.example.testproject.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonListResponseDto {

	Integer pageNo;
	Integer limit;
	Integer totalPages;
	List<PersonDto> data;
}
