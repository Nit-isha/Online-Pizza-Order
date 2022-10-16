package com.cg.onlinepizza.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.onlinepizza.entity.Pizza;
import com.cg.onlinepizza.pizza.dto.PizzaDto;
import com.cg.onlinepizza.pizza.service.IPizzaService;

@RestController
public class MyController {
	@Autowired
	private IPizzaService pizzaService;
	
	@GetMapping(path = "/menu", produces = {"application/json","application/xml"})
	public ResponseEntity<List<Pizza>> getPizzaList() {
		List<Pizza> pizzaList = pizzaService.viewPizzaList();
		return new ResponseEntity<List<Pizza>>(pizzaList, HttpStatus.OK);
	}
}
