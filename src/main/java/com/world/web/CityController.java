package com.world.web;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.world.domain.City;
import com.world.repository.CityRepository;

@RestController
@Transactional
@RequestMapping(value="/cities")
public class CityController {

	@Autowired
	private CityRepository cityRepository;

	@RequestMapping(value="/", method = RequestMethod.GET)
	public Collection<City> get() {
		return cityRepository.findAll();
	}

	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public City read(@PathVariable String id) {
		return cityRepository.findById(id);
	}

	@RequestMapping(value="/", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody City input, BindingResult bindingResult) {
		cityRepository.save(input);
		HttpHeaders headers = new HttpHeaders();
		//TODO: id holder key
        //headers.setLocation(ucBuilder.path("/cities/{id}").buildAndExpand(777).toUri());
		return new ResponseEntity<>("headers", HttpStatus.CREATED);
	} 
	
	@RequestMapping(value="/batch", method = RequestMethod.POST)
	public ResponseEntity<?> addAll(@RequestBody List<City> list) {
		cityRepository.saveAll(list);
		return new ResponseEntity<>("success", HttpStatus.OK);
	} 

	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable String id, @RequestBody City input) {
		City result = cityRepository.findById(id);

		if (result == null) {
			return new ResponseEntity<String>("City with id " + id + " not found.", HttpStatus.NOT_FOUND);
		} else {
			result.setName(input.getName());
			result.setPopulation(input.getPopulation());
			result.setSquare(input.getSquare());
			cityRepository.save(result);
		}

		return new ResponseEntity<City>(result, HttpStatus.OK); 
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable String id) {
		cityRepository.deleteById(id);
		return new ResponseEntity<City>(HttpStatus.NO_CONTENT); 
	}
}