package com.example.testproject.serviceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.UnexpectedTypeException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.testproject.common.EnumStatus;
import com.example.testproject.common.ObjectMapper;
import com.example.testproject.dto.PersonDto;
import com.example.testproject.dto.PersonListResponseDto;
import com.example.testproject.exception.BadRequestException;
import com.example.testproject.model.AddressEntity;
import com.example.testproject.model.PersonEntity;
import com.example.testproject.repository.AddressRepository;
import com.example.testproject.repository.PersonRepository;
import com.example.testproject.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	PersonRepository personRepository;

	@Autowired
	AddressRepository addressRepository;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	public void create(PersonDto personDto) throws BadRequestException {

		PersonEntity userExists = personRepository.findByEmail(personDto.getEmail());
		if (userExists != null) {
			throw new BadRequestException(
					personDto.getEmail() + " :: This email already registered , Please try with a new email id.",
					HttpStatus.NOT_FOUND);
		}

		PersonEntity personEntity = new PersonEntity();
		personEntity.setFirstName(personDto.getFirstName());
		personEntity.setLastName(personDto.getLastName());
		personEntity.setEmail(personDto.getEmail());
		personEntity.setStatus(EnumStatus.CREATED.toString());
		personEntity.setCreatedOn(LocalDateTime.now());
		personEntity.setAddresses(personDto.getAddresses());
		String pattern = "^$|^[0-9X]{10}$";
		if (personDto.getMobileNo().toString().matches(pattern)) {
			personEntity.setMobileNo(personDto.getMobileNo());
		} else {
			throw new UnexpectedTypeException();
		}

		personRepository.save(personEntity);

	}

	@Override
	public void update(PersonDto personDto, Long id) throws BadRequestException {

		Optional<PersonEntity> otionalPerson = personRepository.findById(id);
		if (otionalPerson.isEmpty()) {
			throw new BadRequestException("Value is not present for this id", HttpStatus.NOT_FOUND);
		}
		if (!otionalPerson.isEmpty()) {
			PersonEntity personEntity = otionalPerson.get();
			personEntity.setFirstName(personDto.getFirstName());
			personEntity.setLastName(personDto.getLastName());
			personEntity.setEmail(personDto.getEmail());
			
			String pattern = "^$|^[0-9X]{10}$";
			if (personDto.getMobileNo().toString().matches(pattern)) {
				personEntity.setMobileNo(personDto.getMobileNo());
			} else {
				throw new UnexpectedTypeException();
			}
			
			personEntity.setStatus(EnumStatus.UPDATED.toString());
			personEntity.setModifiedOn(LocalDateTime.now());
			personEntity.setId(id);

			personDto.getAddresses().forEach(list -> {
				// System.out.println("70----" + otionalAddress.get());
				Optional<AddressEntity> otionalAddress = addressRepository.findById(list.getId());
				System.out.println("72----" + otionalAddress.get());
				AddressEntity address = otionalAddress.get();
				address.setId(list.getId());
				address.setAddress(list.getAddress());
				personEntity.getAddresses().add(address);
				personRepository.save(personEntity);
			});
		}

	}

	@Override
	public PersonListResponseDto getAllPersons(Integer pageNo, Integer pageSize) {

		PageRequest paging = PageRequest.of(pageNo - 1, pageSize);
		Page<PersonEntity> getAll = personRepository.findAll(paging);
		List<PersonEntity> userResultList = getAll.getContent();

		List<PersonDto> postDtoList = Arrays.asList(modelMapper.map(userResultList, PersonDto[].class));

		return PersonListResponseDto.builder().pageNo(pageNo).limit(pageSize).totalPages(getAll.getTotalPages())
				.data(postDtoList).build();
	}

	@Override
	public PersonDto findById(Long id) throws BadRequestException {
		Optional<PersonEntity> result = personRepository.findById(id);
		PersonEntity user = null;

		if (result.isPresent()) {
			user = result.get();
		} else {
			throw new BadRequestException("Value is not present for this id", HttpStatus.BAD_REQUEST);
		}

		ObjectMapper<PersonDto> objectMapper = new ObjectMapper<>();
		PersonDto personDto = objectMapper.map(user, PersonDto.class);
		return personDto;
	}

	@Override
	public PersonDto deleteById(Long id) throws BadRequestException {
		Optional<PersonEntity> otionalPerson = personRepository.findById(id);

		if (otionalPerson.isEmpty()) {
			throw new BadRequestException("Value is not present for this id", HttpStatus.NOT_FOUND);
		}

		PersonEntity personEntity = otionalPerson.get();
		personEntity.setDeletedOn(LocalDateTime.now());
		personEntity.setStatus(EnumStatus.DELETED.toString());
		personEntity = personRepository.save(personEntity);

		ObjectMapper<PersonEntity> objectMapper = new ObjectMapper<>();
		return objectMapper.map(personEntity, PersonDto.class);
	}

}
