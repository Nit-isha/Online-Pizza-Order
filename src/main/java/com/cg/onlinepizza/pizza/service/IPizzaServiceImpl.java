package com.cg.onlinepizza.pizza.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.onlinepizza.entity.Pizza;
import com.cg.onlinepizza.exceptions.InvalidMinCostException;
import com.cg.onlinepizza.exceptions.PizzaIdNotFoundException;
import com.cg.onlinepizza.pizza.dao.IPizzaRepository;
import com.cg.onlinepizza.pizza.dto.PizzaDto;
@Component
public class IPizzaServiceImpl implements IPizzaService{
	@Autowired
	private IPizzaRepository iPizzaRepository;
	@Override
	public PizzaDto addPizza(PizzaDto pizza) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PizzaDto updatePizza(PizzaDto pizza) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PizzaDto deletePizza(int pizzaId) throws PizzaIdNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PizzaDto viewPizza(int pizzaId) throws PizzaIdNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pizza> viewPizzaList() {
		List<Pizza> pizzaList = new ArrayList<>();
		Iterable<Pizza> list =  iPizzaRepository.findAll();
		list.forEach(p->pizzaList.add(p));
		return pizzaList;
	}

	@Override
	public List<PizzaDto> viewPizzaList(double minCost, double maxCost) throws InvalidMinCostException {
		// TODO Auto-generated method stub
		return null;
	}

}
