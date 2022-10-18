package com.cg.onlinepizza.pizzaorder.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	
	//Returns all the existing orders in database
	@Override
	public List<PizzaOrderDto> viewOrdersList() {
		List<PizzaOrder> pizzaOrderList = new ArrayList<>();
		
		Iterable<PizzaOrder> list =  iPizzaOrderRepository.findAll();
		list.forEach(p -> pizzaOrderList.add(p));
		
		List<PizzaOrderDto> pizzaOrderDtoList = new ArrayList<>();
		for(PizzaOrder pizzaOrder: pizzaOrderList) {
			pizzaOrderDtoList.add(entityToDto(pizzaOrder));
		}
		return pizzaOrderDtoList;
	}
	
	@Override
	public PizzaOrderDto bookPizzaOrder(PizzaOrderDto order) {
		
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
		p.setPizza(iPizzaOrderRepository.getPizzaById(pizzaOrder.getPizzaIdList()));
		p.setQuantity(pizzaOrder.getQuantity());
		p.setSize(pizzaOrder.getSize());
		p.setTotalCost(pizzaOrder.getTotalCost());
		p.setTransactionMode(pizzaOrder.getTransactionMode());
		return p;
	}
	
	/*Pizza Entity to PizzaDto Class Conversion*/
	public PizzaOrderDto entityToDto(PizzaOrder pizzaOrder) {
		PizzaOrderDto p = new PizzaOrderDto();
		p.setBookingOrderId(pizzaOrder.getBookingOrderId());
		p.setCouponName(pizzaOrder.getCoupon().getCouponName());
		p.setCustId(pizzaOrder.getCustomer().getId());
		p.setOrderDate(pizzaOrder.getOrderDate());
		p.setOrderType(pizzaOrder.getOrderType());
		p.setQuantity(pizzaOrder.getQuantity());
		p.setSize(pizzaOrder.getSize());
		p.setTotalCost(pizzaOrder.getTotalCost());
		p.setTransactionMode(pizzaOrder.getTransactionMode());
		List<Integer> pizzaIdList = new ArrayList<>();
		pizzaIdList = pizzaOrder.getPizza().stream().map(t->t.getPizzaId()).collect(Collectors.toList());
		p.setPizzaIdList(pizzaIdList);
		return p;
	}

}
