package com.cg.onlinepizza.pizzaorder.service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.onlinepizza.coupon.dao.ICouponRepository;
import com.cg.onlinepizza.customer.dao.ICustomerRepository;
import com.cg.onlinepizza.entity.Customer;
import com.cg.onlinepizza.entity.Pizza;
import com.cg.onlinepizza.entity.PizzaOrder;
import com.cg.onlinepizza.exceptions.OrderIdNotFoundException;
import com.cg.onlinepizza.pizza.dao.IPizzaRepository;
import com.cg.onlinepizza.pizza.dto.PizzaDto;
import com.cg.onlinepizza.pizzaorder.dao.IPizzaOrderRepository;
import com.cg.onlinepizza.pizzaorder.dto.PizzaOrderDto;
import com.cg.onlinepizza.secure.model.User;
import com.cg.onlinepizza.secure.repository.UserRepository;

@Component
public class IPizzaOrderServiceImpl implements IPizzaOrderService {

	@Autowired
	private IPizzaOrderRepository iPizzaOrderRepository;
	@Autowired
	private ICouponRepository iCouponRepository;
	@Autowired
	private IPizzaRepository iPizzaRepository;
	@Autowired
	private ICustomerRepository iCustomerRepository;
	@Autowired
	private UserRepository iUserRepository;
	
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
	public PizzaOrderDto bookPizzaOrder(Principal currentCustomer, PizzaOrderDto order) {
		PizzaOrder orderEntity = new PizzaOrder();
		orderEntity.setBookingOrderId(order.getBookingOrderId());
		orderEntity.setOrderDate(order.getOrderDate());
		orderEntity.setTransactionMode(order.getTransactionMode());
		orderEntity.setSize(order.getSize());
		orderEntity.setOrderType(order.getOrderType());
		orderEntity.setCoupon(iCouponRepository.getCouponByName(order.getCouponName()));
		
		/*//get current customer username
		String custUsername = currentCustomer.getName();
		//find the user by username
		User user= iUserRepository.findByUsername(custUsername);
		//find customer by userid/custid
		orderEntity.setCustomer(iCustomerRepository.getCustomerById(user.getId()));*/
		
		List<Pizza> orderPizzas = iPizzaRepository.getPizzaListById(order.getPizzaIdList());
		
		orderEntity.setCustomer(getCustomer(currentCustomer));
		
		orderEntity.setPizza(orderPizzas);
		orderEntity.setQuantity(orderPizzas.size());
		orderEntity.setTotalCost(calcTotal(orderPizzas));
		
		iPizzaOrderRepository.save(orderEntity);
		return entityToDto(orderEntity);
	}

	@Override
	public PizzaOrderDto updatePizzaOrder(Principal currentCustomer,int orderId,  PizzaOrderDto order) throws OrderIdNotFoundException {
		List<PizzaOrderDto> orderHistory= viewCustomerOrdersList(currentCustomer);
		
		Optional<PizzaOrderDto> optionalOrderDto = orderHistory.stream().filter(p->p.getBookingOrderId() == orderId).findFirst();
		if(optionalOrderDto.isPresent()) {
			
			PizzaOrder updateEntity= dtoToEntity(order);
			updateEntity.setBookingOrderId(optionalOrderDto.get().getBookingOrderId());
			List<Pizza> updatedPizzaList =  updateEntity.getPizza();
			updateEntity.setQuantity(updatedPizzaList.size());
			updateEntity.setTotalCost(calcTotal(updatedPizzaList));
			updateEntity.setCustomer(getCustomer(currentCustomer));
			
			iPizzaOrderRepository.save(updateEntity);
			
			return entityToDto(updateEntity);
		}
		else {
			throw new OrderIdNotFoundException();
		}
		
	}

	@Override
	public PizzaOrderDto cancelPizzaOrder(int pizzaOrderId) {
		// TODO Auto-generated method stub
		return null;
	}

	//Admin access only
	@Override
	public PizzaOrderDto viewPizzaOrder( int pizzaOrderId) throws OrderIdNotFoundException {
		List<PizzaOrderDto> allOrders = viewOrdersList();
		Optional<PizzaOrderDto> optionalOrderDto = allOrders.stream().filter(p->p.getBookingOrderId() == pizzaOrderId).findFirst();
		if(optionalOrderDto.isPresent()) {
			return optionalOrderDto.get();
		}
		else {
			throw new OrderIdNotFoundException();
		}
	}
	//Admin access only
	@Override
	public PizzaOrderDto viewCustomerPizzaOrderById( Principal currentCustomer, int pizzaOrderId) throws OrderIdNotFoundException {
		List<PizzaOrderDto> orderHistory= viewCustomerOrdersList(currentCustomer);
		
		Optional<PizzaOrderDto> optionalOrderDto = orderHistory.stream().filter(p->p.getBookingOrderId() == pizzaOrderId).findFirst();
		if(optionalOrderDto.isPresent()) {
			return optionalOrderDto.get();
		}
		else {
			throw new OrderIdNotFoundException();
		}
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
	public List<PizzaOrderDto> viewCustomerOrdersList(Principal currentCustomer) {
		String custUsername = currentCustomer.getName();
		User user= iUserRepository.findByUsername(custUsername);
		int custId = user.getId();
		List<PizzaOrder> orderHistory= iPizzaOrderRepository.getCustomerPizzaOrderHistory(custId);
		List<PizzaOrderDto> orderHistoryDto = new ArrayList<>();
		for(PizzaOrder order : orderHistory) {
			orderHistoryDto.add(entityToDto(order));
			System.out.println(order);
		}
		return orderHistoryDto;
	}
	
	/*PizzaOrderDto to Pizza Entity Class Conversion*/
	public PizzaOrder dtoToEntity(PizzaOrderDto pizzaOrder) {
		PizzaOrder p = new PizzaOrder();
		p.setBookingOrderId(pizzaOrder.getBookingOrderId());
		p.setCoupon(iCouponRepository.getCouponByName(pizzaOrder.getCouponName()));
		p.setCustomer(iCustomerRepository.getCustomerById(pizzaOrder.getCustId()));
		p.setOrderDate(pizzaOrder.getOrderDate());
		p.setOrderType(pizzaOrder.getOrderType());
		p.setPizza(iPizzaRepository.getPizzaListById(pizzaOrder.getPizzaIdList()));
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
	
	public double calcTotal(List<Pizza> orderPizzas) { 
		double totalCost = orderPizzas.stream().mapToDouble(p->p.getPizzaCost()).sum();
		return totalCost;
	}
	public Customer getCustomer(Principal currentCustomer) {
		String custUsername = currentCustomer.getName();
		User user= iUserRepository.findByUsername(custUsername);
		Customer cust = iCustomerRepository.getCustomerById(user.getId());
		return cust;
	}
	
	
	
}
