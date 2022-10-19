package com.cg.onlinepizza.pizzaorder.service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import com.cg.onlinepizza.exceptions.OrderCancelDeclinedException;
import com.cg.onlinepizza.exceptions.OrderIdNotFoundException;
import com.cg.onlinepizza.pizzaorder.dto.PizzaOrderDto;

public interface IPizzaOrderService {
	
	PizzaOrderDto bookPizzaOrder(Principal currentCustomer, PizzaOrderDto order);
	
	PizzaOrderDto updatePizzaOrder(Principal currentCustomer, int orderId,PizzaOrderDto order) throws OrderIdNotFoundException;
	
	PizzaOrderDto cancelPizzaOrder(Principal currentCustomer, int bookingOrderId)throws OrderIdNotFoundException, OrderCancelDeclinedException ;
	
	PizzaOrderDto viewPizzaOrder(int pizzaOrderId) throws OrderIdNotFoundException;
	
	List<PizzaOrderDto> viewOrdersList();		//displays all orders to admin
	
	List<PizzaOrderDto> viewCustomerOrdersList(Principal currentCustomer);	//Displays customer's order history
	
	PizzaOrderDto viewOrdersByDate(LocalDate date);
	
	List<PizzaOrderDto> calculateTotal(String size, int quantity);
	
	PizzaOrderDto viewCustomerPizzaOrderById( Principal currentCustomer, int pizzaOrderId) throws OrderIdNotFoundException;
}
