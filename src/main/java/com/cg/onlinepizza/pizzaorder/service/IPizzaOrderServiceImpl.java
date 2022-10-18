package com.cg.onlinepizza.pizzaorder.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.onlinepizza.entity.Pizza;
import com.cg.onlinepizza.entity.PizzaOrder;
import com.cg.onlinepizza.pizza.dto.PizzaDto;
import com.cg.onlinepizza.pizzaorder.dao.IPizzaOrderRepository;
import com.cg.onlinepizza.pizzaorder.dto.PizzaOrderDto;

@Component
public class IPizzaOrderServiceImpl implements IPizzaOrderService {

	@Autowired
	private IPizzaOrderRepository iPizzaOrderRepository;
	
	@Override
	public List<PizzaOrderDto> viewOrdersList() {
		List<PizzaOrderDto> fullOrderHistory = new ArrayList<>();
		Iterable<PizzaOrder> list= iPizzaOrderRepository.findAll();
		list.forEach(e->fullOrderHistory.add(e));
		return null;
	}
	
	@Override
	public PizzaOrderDto bookPizzaOrder(PizzaOrderDto order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PizzaOrderDto updatePizzaOrder(PizzaOrderDto order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PizzaOrderDto cancelPizzaOrder(int pizzaOrderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PizzaOrderDto viewPizzaOrder(int pizzaOrderId) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public PizzaOrderDto viewOrdersByDate(LocalDate date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PizzaOrderDto> calculateTotal(String size, int quantity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PizzaOrderDto> viewCustomerOrdersList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*PizzaOrderDto to Pizza Entity Class Conversion*/
	public PizzaOrder dtoToEntity(PizzaOrderDto pizzaOrder) {
		PizzaOrder p = new PizzaOrder();
		p.setBookingOrderId(pizzaOrder.getBookingOrderId());
		p.setCoupon(iPizzaOrderRepository.getCouponByName(pizzaOrder.getCouponName()));
		p.setCustomer(iPizzaOrderRepository.getCustomerById(pizzaOrder.getCustId()));
		p.setOrderDate(pizzaOrder.getOrderDate());
		p.setOrderType(pizzaOrder.getOrderType());
		p.setPizza(iPizzaOrderRepository.getPizzaById(pizzaOrder.getPizzaId()));
		return p;
	}
	
	/*Pizza Entity to PizzaDto Class Conversion*/
	public PizzaDto entityToDto(Pizza pizza) {
		PizzaDto p = new PizzaDto();
		p.setPizzaId(pizza.getPizzaId());
		p.setPizzaName(pizza.getPizzaName());
		p.setPizzaType(pizza.getPizzaType());
		p.setPizzaDescription(pizza.getPizzaDescription());
		p.setPizzaCost(pizza.getPizzaCost());
		return p;
	}

}
