package com.cg.onlinepizza.order.service;

import java.util.List;

import com.cg.onlinepizza.exceptions.InvalidSizeException;
import com.cg.onlinepizza.exceptions.OrderIdNotFoundException;
import com.cg.onlinepizza.order.dto.PizzaOrder;

public interface IPizzaOrderService {
	PizzaOrder bookPizzaOrder(PizzaOrder order);

	PizzaOrder updatePizzaOrder(PizzaOrder order);

	PizzaOrder cancelPizzaOrder(int orderId) throws OrderIdNotFoundException;

	PizzaOrder viewPizzaOrder(int orderId) throws OrderIdNotFoundException;

	List<PizzaOrder> caluculateTotal(String size, int quantity) throws InvalidSizeException;
}


