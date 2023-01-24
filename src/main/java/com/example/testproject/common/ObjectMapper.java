package com.example.testproject.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class ObjectMapper<T> {

	/** ModelMapper : bean to bean mapping library. */
	private ModelMapper modelMapper;

	/** Constructor. */
	public ObjectMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	public <I, O> O map(I input, Class<O> outputClass) {
		return modelMapper.map(input, outputClass);
	}

	public <I, O> List<O> map(Collection<I> inputs, Class<O> outputClass) {
		List<O> outputs = new ArrayList<>();
		for (I input : inputs) {
			O output = map(input, outputClass);
			outputs.add(output);
		}
		return outputs;
	}
}
