package com.cg.onlinepizza.pizzaorder.service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cg.onlinepizza.coupon.dao.ICouponRepository;
import com.cg.onlinepizza.customer.dao.ICustomerRepository;
import com.cg.onlinepizza.entity.Customer;
import com.cg.onlinepizza.entity.Pizza;
import com.cg.onlinepizza.entity.PizzaOrder;
import com.cg.onlinepizza.exceptions.NoOrdersFoundException;
import com.cg.onlinepizza.exceptions.OrderCancelDeclinedException;
import com.cg.onlinepizza.exceptions.OrderIdNotFoundException;
import com.cg.onlinepizza.exceptions.OrderUpdateDeclinedException;
import com.cg.onlinepizza.pizza.dao.IPizzaRepository;
import com.cg.onlinepizza.pizzaorder.dao.IPizzaOrderRepository;
import com.cg.onlinepizza.pizzaorder.dto.PizzaOrderDto;
import com.cg.onlinepizza.secure.model.User;
import com.cg.onlinepizza.secure.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonFormat;

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
	
	/*--- ADMIN : View order list ---*/
	@Override
	public List<PizzaOrderDto> viewOrdersList() {
		List<PizzaOrder> pizzaOrderList = new ArrayList<>();
		
		Iterable<PizzaOrder> list = iPizzaOrderRepository.findAll();
		list.forEach(p -> pizzaOrderList.add(p));
		
		List<PizzaOrderDto> pizzaOrderDtoList = new ArrayList<>();
		for(PizzaOrder pizzaOrder : pizzaOrderList) {
			pizzaOrderDtoList.add(entityToDto(pizzaOrder));
		}
		return pizzaOrderDtoList;
	}
	
	/*--- Book Pizza Order ---*/
	@Override
	public PizzaOrderDto bookPizzaOrder(String currentCustomer, PizzaOrderDto order) {
		String couponName = order.getCouponName();
		PizzaOrder orderEntity = new PizzaOrder();
		orderEntity.setBookingOrderId(order.getBookingOrderId());
		orderEntity.setTransactionMode(order.getTransactionMode());
		orderEntity.setSize(order.getSize());
		orderEntity.setOrderType(order.getOrderType());
		orderEntity.setCoupon(iCouponRepository.getCouponByName(couponName));
		
		orderEntity.setOrderDate(LocalDateTime.now());
		orderEntity.setCustomer(iCustomerRepository.findByUsername(currentCustomer).get());
		
		List<Integer> pizzaIds = order.getPizzaIdList();
		List<Pizza> orderPizzas = new ArrayList<>();
		for(int id : pizzaIds) {
			orderPizzas.add(iPizzaRepository.findById(id).get());
		}
		orderEntity.setPizza(orderPizzas);
		orderEntity.setQuantity(pizzaIds.size());
		orderEntity.setTotalCost(calcTotal(couponName, orderPizzas));
		
		iPizzaOrderRepository.save(orderEntity);
		return entityToDto(orderEntity);
	}
	
	/*--- Update Pizza order ---*/
	@Override
	public PizzaOrderDto updatePizzaOrder(String currentCustomer, int orderId, PizzaOrderDto order) throws OrderIdNotFoundException, OrderUpdateDeclinedException {
		LocalDateTime currTime = LocalDateTime.now();
		PizzaOrderDto orderDto = viewCustomerPizzaOrderById(currentCustomer, orderId);
		LocalDateTime bookingTime = orderDto.getOrderDate();
		
		List<PizzaOrderDto> orderHistory = viewCustomerOrdersList(currentCustomer);
		
		Optional<PizzaOrderDto> optionalOrderDto = orderHistory.stream().filter(p -> p.getBookingOrderId() == orderId).findFirst();
		if(optionalOrderDto.isPresent()) {
			if(timeDiffValidation(bookingTime, currTime)) {
				PizzaOrder updateEntity = dtoToEntity(order);
				String couponName = order.getCouponName();
				updateEntity.setBookingOrderId(optionalOrderDto.get().getBookingOrderId());
				
				List<Integer> updatedPizzaIds = order.getPizzaIdList();
				List<Pizza> updatedPizzaList = new ArrayList<>();
				for(int id : updatedPizzaIds) {
					updatedPizzaList.add(iPizzaRepository.findById(id).get());
				}
				updateEntity.setPizza(updatedPizzaList);
				
				updateEntity.setQuantity(updatedPizzaIds.size());
				updateEntity.setTotalCost(calcTotal(couponName, updatedPizzaList));
				updateEntity.setCustomer(iCustomerRepository.findByUsername(currentCustomer).get());
				updateEntity.setOrderDate(LocalDateTime.now());
				
				iPizzaOrderRepository.save(updateEntity);
				return entityToDto(updateEntity);
			}
			else {
				throw new OrderUpdateDeclinedException();
			}
		}
		else {
			throw new OrderIdNotFoundException();
		}
	}
	
	/*--- Cancel Pizza order within 15 minutes ---*/
	@Override
	public PizzaOrderDto cancelPizzaOrder(String currentCustomer, int bookingOrderId) throws OrderIdNotFoundException, OrderCancelDeclinedException {
		
		LocalDateTime currTime = LocalDateTime.now();
		PizzaOrderDto order = viewCustomerPizzaOrderById(currentCustomer, bookingOrderId);
		LocalDateTime bookingTime = order.getOrderDate();
		
		if(timeDiffValidation(bookingTime, currTime)) {
			iPizzaOrderRepository.delete(dtoToEntity(order));
		}
		else {
			throw new OrderCancelDeclinedException();
		}
		return null;
	}

	/*--- ADMIN : View Pizza order by ID ---*/
	@Override
	public PizzaOrderDto viewPizzaOrder(int pizzaOrderId) throws OrderIdNotFoundException {
		List<PizzaOrderDto> allOrders = viewOrdersList();
		Optional<PizzaOrderDto> optionalOrderDto = allOrders.stream().filter(p -> p.getBookingOrderId() == pizzaOrderId).findFirst();
		if(optionalOrderDto.isPresent()) {
			return optionalOrderDto.get();
		}
		else {
			throw new OrderIdNotFoundException();
		}
	}
	
	/*--- View pizza order by ID ---*/
	@Override
	public PizzaOrderDto viewCustomerPizzaOrderById(String currentCustomer, int pizzaOrderId) throws OrderIdNotFoundException {
		List<PizzaOrderDto> orderHistory = viewCustomerOrdersList(currentCustomer);
		Optional<PizzaOrderDto> optionalOrderDto = orderHistory.stream().filter(p -> p.getBookingOrderId() == pizzaOrderId).findFirst();
		if(optionalOrderDto.isPresent()) {
			return optionalOrderDto.get();
		}
		else {
			throw new OrderIdNotFoundException();
		}
	}

	/*--- Filter all orders by particular date ---*/
	@Override
	public List<PizzaOrderDto> viewAllOrdersByDate(LocalDate date) throws NoOrdersFoundException {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		List<PizzaOrderDto> allOrders = viewOrdersList();
		List<PizzaOrderDto> orderBydateList=allOrders.stream().filter(o -> o.getOrderDate().format(dtf).equals(date.format(dtf))).collect(Collectors.toList());
		
		if(orderBydateList.isEmpty()) {
			throw new NoOrdersFoundException();
		}
	    return orderBydateList;
	}
	
	/*--- Filter customer orders by particular date ---*/
	@Override
	public List<PizzaOrderDto> viewCustomerOrdersByDate(String currentCustomer, LocalDate date) throws NoOrdersFoundException{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		List<PizzaOrderDto> orderHistory = viewCustomerOrdersList(currentCustomer);
		List<PizzaOrderDto> orderBydateList=orderHistory.stream().filter(o -> o.getOrderDate().format(dtf).equals(date.format(dtf))).toList();
		if(orderBydateList.isEmpty()) {
			throw new NoOrdersFoundException();
		}
		return orderBydateList;
	}
	
	
	/*--- View customer orders list  ---*/
	@Override
	public List<PizzaOrderDto> viewCustomerOrdersList(String custUsername) {
		User user = iUserRepository.findByUsername(custUsername);
		int custId = user.getId();
		List<PizzaOrder> orderHistory = iPizzaOrderRepository.getCustomerPizzaOrderHistory(custId);
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
	
	/*Calculate total*/
	public double calcTotal(String couponName, List<Pizza> orderPizzas) { 
		
		double totalCost = orderPizzas.stream().mapToDouble(p->p.getPizzaCost()).sum();
		if(couponName.startsWith("FLAT") && couponName.contains("M")) {
			int indexOfM = couponName.indexOf('M');
			int discount = Integer.parseInt(couponName.substring(4, indexOfM));
			int minValue = Integer.parseInt(couponName.substring(indexOfM + 1 ));
			if(totalCost >= minValue) {
				totalCost -= discount;
			}
		}
		else if(couponName.startsWith("PIZZA") && couponName.contains("M")) {
			int indexOfM = couponName.indexOf('M');
			int percent = Integer.parseInt(couponName.substring(5, indexOfM));
			int maxValue = Integer.parseInt(couponName.substring(indexOfM + 1 ));
			double discount = (percent * totalCost)/100; 
			if(discount > maxValue) {
				discount = maxValue;
			}
			totalCost -= discount;
		}	
		return totalCost;
	}
	
	/*Get Customer from TOKEN --> For current session*/
	public Customer getCustomer(Principal currentCustomer) {
		String custUsername = currentCustomer.getName();
		User user = iUserRepository.findByUsername(custUsername);
		Customer cust = iCustomerRepository.getCustomerById(user.getId());
		return cust;
	}
	
	/*Time difference validation of 15 minutes*/
	public Boolean timeDiffValidation(LocalDateTime bookingTime, LocalDateTime currentTime) {
		Boolean flag = true;
		Duration timeDiff = Duration.between(bookingTime, currentTime);
		if(timeDiff.toMinutes() > 15) {
			flag = false;
		}
		return flag;	
	}

	
}
