package com.example.testproject.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.testproject.model.AddressEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class PersonDto {
	private Long id;

	private String firstName;
	private String lastName;
	private String email;
	private Long mobileNo;
	private String status;
	private LocalDateTime createdOn;
	private LocalDateTime modifiedOn;
	private LocalDateTime deletedOn;

	private List<AddressEntity> addresses;

}
