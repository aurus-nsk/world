package com.world.web;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
		return streetRepository.findById(Integer.parseInt(id));
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody Street input, UriComponentsBuilder ucBuilder) {
		int id = streetRepository.save(input);
		streetRepository.updateFts();
		HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/streets/{id}").buildAndExpand(id).toUri());
		return new ResponseEntity<HttpHeaders>(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/batch", method = RequestMethod.POST)
	public ResponseEntity<?> addAll(@RequestBody List<Street> list) {
		streetRepository.saveAll(list);
		streetRepository.updateFts();
		return new ResponseEntity<>("street batchUpdate - success", HttpStatus.OK);
	} 
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable String id, @RequestBody Street input) {
		Street result = streetRepository.findById(Integer.parseInt(id));

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
		streetRepository.deleteById(Integer.parseInt(id));
		return new ResponseEntity<City>(HttpStatus.NO_CONTENT); 
	}
	
	@RequestMapping(value="/findstreet", method = RequestMethod.GET)
	public Map<Integer, Map<String,Object>> findStreet(@RequestParam Map<String, String> request) {
		String query = (String) request.get("scope"); 
		query = query.replaceAll("\\s+", " | ");
		query = query + ":b";
		return streetRepository.findStreet(Integer.parseInt(request.get("from")), Integer.parseInt(request.get("to")), (String) request.get("city"), query);
	}
}