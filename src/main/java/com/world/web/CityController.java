package com.world.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.world.domain.City;
import com.world.repository.CityRepository;

@Controller
@Transactional
public class CityController {

	@Autowired
	private CityRepository cityRepository;
	
	@RequestMapping(value="/city/all", method = RequestMethod.GET)
	public String getAllCities() {
		System.out.println("getAllCities");
		List<City> list = cityRepository.findAll();
		
		System.out.println(Arrays.toString(list.toArray()));
		
		return "index";
	} 
}