package com.world.web;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

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
	
	@RequestMapping(value="/name", method = RequestMethod.GET)
	public Collection<Organization> findByName(@RequestParam("name") String name) {
		return organizationRepository.findByName(name);
	}
	
	@RequestMapping(value="/from", method = RequestMethod.GET)
	public Collection<Organization> findFromDateUpdate(@RequestParam("from") @DateTimeFormat(pattern="yyyy-MM-dd'T'hh:mm:ss.SSSZ") Date fromDate) {
		return organizationRepository.findFromDateUpdate(fromDate);
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody Organization input, UriComponentsBuilder ucBuilder) {
		int id = organizationRepository.save(input);
		organizationRepository.updateFts();
		HttpHeaders headers = new HttpHeaders(); 
		headers.setLocation(ucBuilder.path("/organizations/{id}").buildAndExpand(id).toUri());
		return new ResponseEntity<HttpHeaders>(headers, HttpStatus.CREATED);
	} 
	
	@RequestMapping(value="/batch", method = RequestMethod.POST)
	public ResponseEntity<?> addAll(@RequestBody List<Organization> list) {
		organizationRepository.saveAll(list);
		organizationRepository.updateFts();
		return new ResponseEntity<>("oranization batchUpdate - success", HttpStatus.CREATED);
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
			result.setPhone(input.getPhone());
			result.setWebsite(input.getWebsite());
			organizationRepository.save(result);
		}

		return new ResponseEntity<>("success", HttpStatus.OK); 
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable String id) {
		organizationRepository.deleteById(id);
		return new ResponseEntity<City>(HttpStatus.NO_CONTENT); 
	}
	
	@RequestMapping(value="/search", method = RequestMethod.POST)
	public Collection<Organization> search(@RequestBody String text) {
		String query = text.replaceAll("\\s+", " | ");
		System.out.println(query);
		return organizationRepository.search(query);
	}
	
	@RequestMapping(value="/analyze", method = RequestMethod.POST)
	public List<Map<String,Object>> analyze(@RequestBody String param) {
		return organizationRepository.analyze(param);
	}
}