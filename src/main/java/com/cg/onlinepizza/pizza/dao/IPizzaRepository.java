package com.cg.onlinepizza.pizza.dao;

import java.util.List;

import com.cg.onlinepizza.exceptions.InvalidMinCostException;
import com.cg.onlinepizza.exceptions.PizzaIdNotFoundException;
import com.cg.onlinepizza.pizza.dto.Pizza;

public interface IPizzaRepository {
	Pizza addPizza(Pizza pizza);

	Pizza updatePizza(Pizza pizza);

	Pizza deletePizza(int pizzaId) throws PizzaIdNotFoundException;

	Pizza viewPizza(int pizzaId) throws PizzaIdNotFoundException;

	List<Pizza> viewPizzaList();

	List<Pizza> viewPizzaList(double minCost, double maxCost)throws InvalidMinCostException;

}
