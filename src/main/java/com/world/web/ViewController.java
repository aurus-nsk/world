package com.world.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.world.repository.CityRepository;
import com.world.repository.StreetRepository;

@Controller
public class ViewController {

	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private StreetRepository streetRepository;
	
	@RequestMapping(value="/organization", method = RequestMethod.GET)
	public String view(Model model) {
		model.addAttribute("cities", cityRepository.findAll());
		model.addAttribute("streets", streetRepository.findAll());
		return "organization";
	}
}
