package com.cg.onlinepizza.pizza.service;

import java.util.List;

import com.cg.onlinepizza.entity.Pizza;
import com.cg.onlinepizza.exceptions.InvalidMinCostException;
import com.cg.onlinepizza.exceptions.PizzaIdNotFoundException;
import com.cg.onlinepizza.pizza.dto.PizzaDto;

public interface IPizzaService {
	PizzaDto addPizza(PizzaDto pizza);

	PizzaDto updatePizza(PizzaDto pizza);

	PizzaDto deletePizza(int pizzaId) throws PizzaIdNotFoundException;

	PizzaDto viewPizza(int pizzaId) throws PizzaIdNotFoundException;

	List<Pizza> viewPizzaList();

	List<PizzaDto> viewPizzaList(double minCost, double maxCost)throws InvalidMinCostException;
}
