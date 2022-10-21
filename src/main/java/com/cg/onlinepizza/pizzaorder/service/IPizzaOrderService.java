package com.cg.onlinepizza.pizzaorder.service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import com.cg.onlinepizza.entity.Pizza;
import com.cg.onlinepizza.exceptions.NoOrdersFoundException;
import com.cg.onlinepizza.exceptions.OrderCancelDeclinedException;
import com.cg.onlinepizza.exceptions.OrderIdNotFoundException;
import com.cg.onlinepizza.exceptions.OrderUpdateDeclinedException;
import com.cg.onlinepizza.pizzaorder.dto.PizzaOrderDto;
import com.fasterxml.jackson.annotation.JsonFormat;

public interface IPizzaOrderService {
	
	PizzaOrderDto bookPizzaOrder(String currentCustomer, PizzaOrderDto order);
	
	PizzaOrderDto updatePizzaOrder(String currentCustomer, int orderId,PizzaOrderDto order) throws OrderIdNotFoundException, OrderUpdateDeclinedException;
	
	PizzaOrderDto cancelPizzaOrder(String currentCustomer, int bookingOrderId)throws OrderIdNotFoundException, OrderCancelDeclinedException ;
	
	PizzaOrderDto viewPizzaOrder(int pizzaOrderId) throws OrderIdNotFoundException;
	
	List<PizzaOrderDto> viewOrdersList();		//displays all orders to admin
	
	List<PizzaOrderDto> viewCustomerOrdersList(String currentCustomer);	//Displays customer's order history
	
	List<PizzaOrderDto> viewAllOrdersByDate(LocalDate date) throws NoOrdersFoundException;
	
	List<PizzaOrderDto> viewCustomerOrdersByDate(String currentCustomer, LocalDate date)throws NoOrdersFoundException;
	
	PizzaOrderDto viewCustomerPizzaOrderById( String currentCustomer, int pizzaOrderId) throws OrderIdNotFoundException;
}
