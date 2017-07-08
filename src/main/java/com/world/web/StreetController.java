package com.world.web;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.world.domain.City;
import com.world.domain.Street;
import com.world.repository.StreetRepository;

@RestController
@Transactional
@RequestMapping(value="/streets")
public class StreetController {

	@Autowired
	private StreetRepository streetRepository;

	@RequestMapping(value="/", method = RequestMethod.GET)
	public Collection<Street> get() {
		return streetRepository.findAll();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public Street read(@PathVariable String id) {
		return streetRepository.findById(id);
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody Street input) {
		streetRepository.save(input);
		return new ResponseEntity<>("success", HttpStatus.OK);
	} 
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	public ResponseEntity<?> addAll(@RequestBody List<Street> list) {
		streetRepository.saveAll(list);
		return new ResponseEntity<>("success", HttpStatus.OK);
	} 
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable String id, @RequestBody Street input) {
		Street result = streetRepository.findById(id);

		if (result == null) {
			return new ResponseEntity<String>("Street with id " + id + " not found.", HttpStatus.NOT_FOUND);
		} else {
			result.setName(input.getName());
			result.setExtent(input.getExtent());
			streetRepository.save(result);
		}

		return new ResponseEntity<>("success", HttpStatus.OK); 
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable String id) {
		streetRepository.deleteById(id);
		return new ResponseEntity<City>(HttpStatus.NO_CONTENT); 
	}
}