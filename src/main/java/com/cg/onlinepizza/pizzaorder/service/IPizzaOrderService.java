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
	
	PizzaOrderDto bookPizzaOrder(Principal currentCustomer, PizzaOrderDto order);
	
	PizzaOrderDto updatePizzaOrder(Principal currentCustomer, int orderId,PizzaOrderDto order) throws OrderIdNotFoundException, OrderUpdateDeclinedException;
	
	PizzaOrderDto cancelPizzaOrder(Principal currentCustomer, int bookingOrderId)throws OrderIdNotFoundException, OrderCancelDeclinedException ;
	
	PizzaOrderDto viewPizzaOrder(int pizzaOrderId) throws OrderIdNotFoundException;
	
	List<PizzaOrderDto> viewOrdersList();		//displays all orders to admin
	
	List<PizzaOrderDto> viewCustomerOrdersList(Principal currentCustomer);	//Displays customer's order history
	
	List<PizzaOrderDto> viewAllOrdersByDate(LocalDate date) throws NoOrdersFoundException;
	
	List<PizzaOrderDto> viewCustomerOrdersByDate(Principal currentCustomer, LocalDate date)throws NoOrdersFoundException;
	
	List<PizzaOrderDto> calculateTotal(List<PizzaOrderDto> orderPizzas);
	
	PizzaOrderDto viewCustomerPizzaOrderById( Principal currentCustomer, int pizzaOrderId) throws OrderIdNotFoundException;
}
