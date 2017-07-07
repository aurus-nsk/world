package com.world.web;

import java.util.Collection;

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
import com.world.domain.Organization;
import com.world.repository.OrganizationRepository;

@RestController
@Transactional
@RequestMapping(value="/organizations")
public class OrganizationController {

	@Autowired
	private OrganizationRepository organizationRepository;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public Collection<Organization> get() {
		return organizationRepository.findAll();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public Organization read(@PathVariable String id) {
		return organizationRepository.findById(id);
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody Organization input) {
		organizationRepository.save(input);
		return new ResponseEntity<>("success", HttpStatus.OK);
	} 
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable String id, @RequestBody Organization input) {
		Organization result = organizationRepository.findById(id);

		if (result == null) {
			return new ResponseEntity<String>("Organization with id " + id + " not found.", HttpStatus.NOT_FOUND);
		} else {
			result.setName(input.getName());
			result.setScope(input.getScope());
			result.setHomeNumber(input.getHomeNumber());
			result.setCity(input.getCity());
			result.setStreet(input.getStreet());
			organizationRepository.save(result);
		}

		return new ResponseEntity<>("success", HttpStatus.OK); 
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable String id) {
		organizationRepository.deleteById(id);
		return new ResponseEntity<City>(HttpStatus.NO_CONTENT); 
	}
}