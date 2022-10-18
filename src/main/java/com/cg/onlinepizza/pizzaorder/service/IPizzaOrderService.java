package com.cg.onlinepizza.pizzaorder.service;

import java.time.LocalDate;
import java.util.List;
import com.cg.onlinepizza.pizzaorder.dto.PizzaOrderDto;

public interface IPizzaOrderService {
	
	PizzaOrderDto bookPizzaOrder(PizzaOrderDto order);
	
	PizzaOrderDto updatePizzaOrder(PizzaOrderDto order);
	
	PizzaOrderDto cancelPizzaOrder(int pizzaOrderId);
	
	PizzaOrderDto viewPizzaOrder(int pizzaOrderId);
	
	List<PizzaOrderDto> viewOrdersList();		//displays all orders to admin
	
	List<PizzaOrderDto> viewCustomerOrdersList();	//Displays customer's order history
	
	PizzaOrderDto viewOrdersByDate(LocalDate date);
	
	List<PizzaOrderDto> calculateTotal(String size, int quantity);
}