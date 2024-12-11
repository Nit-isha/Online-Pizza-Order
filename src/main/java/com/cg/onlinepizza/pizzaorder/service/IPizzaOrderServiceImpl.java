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
import com.cg.onlinepizza.coupon.dto.CouponDto;
import com.cg.onlinepizza.customer.dao.ICustomerRepository;
import com.cg.onlinepizza.entity.Coupon;
import com.cg.onlinepizza.entity.Customer;
import com.cg.onlinepizza.entity.Pizza;
import com.cg.onlinepizza.entity.PizzaOrder;
import com.cg.onlinepizza.exceptions.NoOrdersFoundException;
import com.cg.onlinepizza.exceptions.OrderCancelDeclinedException;
import com.cg.onlinepizza.exceptions.OrderIdNotFoundException;
import com.cg.onlinepizza.exceptions.OrderUpdateDeclinedException;
import com.cg.onlinepizza.exceptions.PizzaIdNotFoundException;
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
	public PizzaOrderDto bookPizzaOrder(String currentCustomer, PizzaOrderDto order) throws PizzaIdNotFoundException {
		String couponName = order.getCouponName();
		PizzaOrder orderEntity = new PizzaOrder();
		orderEntity.setBookingOrderId(order.getBookingOrderId());
		orderEntity.setTransactionMode(order.getTransactionMode());
		//orderEntity.setSize(order.getSize());
		orderEntity.setOrderType(order.getOrderType());
		try {
		orderEntity.setCoupon(iCouponRepository.getCouponByName(couponName));}
		catch(Exception ignored) {}
		
		orderEntity.setOrderDate(LocalDateTime.now());
		orderEntity.setCustomer(iCustomerRepository.findByUsername(currentCustomer).get());
		
		//List<Integer> pizzaIds = order.getPizzaIdList();
		
//		List<Pizza> orderPizzas = new ArrayList<>();
//		
//		List<Integer> cataloguePizzaIds = iPizzaRepository.getPizzaIdList();
//		for(int id : pizzaIds) {
//			if(!cataloguePizzaIds.contains(id)) {
//				throw new PizzaIdNotFoundException();
//			}
//			
//			orderPizzas.add(iPizzaRepository.findById(id).get());
//		}
		orderEntity.setPizza(order.getPizzaList());
		orderEntity.setQuantity(order.getPizzaList().size());
		orderEntity.setTotalCost(calcTotal(iCouponRepository.getCouponByName(couponName), order.getPizzaList()));
		
		iPizzaOrderRepository.save(orderEntity);
		return entityToDto(orderEntity);
	}
	
	/*--- Update Pizza order ---*/
	@Override
	public PizzaOrderDto updatePizzaOrder(String currentCustomer, int orderId, PizzaOrderDto order) throws OrderIdNotFoundException, OrderUpdateDeclinedException, PizzaIdNotFoundException {
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
				
				List<Integer> cataloguePizzaIds = iPizzaRepository.getPizzaIdList();
//				List<Integer> updatedPizzaIds = order.getPizzaIdList();
//				List<Pizza> updatedPizzaList = new ArrayList<>();
//				for(int id : updatedPizzaIds) {
//					if(!cataloguePizzaIds.contains(id)) {
//						throw new PizzaIdNotFoundException();
//					}
//					updatedPizzaList.add(iPizzaRepository.findById(id).get());
//				}
				updateEntity.setPizza(order.getPizzaList());
				
				updateEntity.setQuantity(order.getPizzaList().size());
				updateEntity.setTotalCost(calcTotal(iCouponRepository.getCouponByName(couponName), order.getPizzaList()));
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
		List<PizzaOrderDto> orderBydateList=orderHistory.stream().filter(o -> o.getOrderDate().format(dtf).equals(date.format(dtf))).collect(Collectors.toList());
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
		try {
		p.setCoupon(iCouponRepository.getCouponByName(pizzaOrder.getCouponName()));}
		catch(Exception ignored) {}
		p.setCustomer(iCustomerRepository.getCustomerById(pizzaOrder.getCustId()));
		p.setOrderDate(pizzaOrder.getOrderDate());
		p.setOrderType(pizzaOrder.getOrderType());
		p.setPizza(pizzaOrder.getPizzaList());
		p.setQuantity(pizzaOrder.getQuantity());
		//p.setSize(pizzaOrder.getSize());
		p.setTotalCost(pizzaOrder.getTotalCost());
		p.setTransactionMode(pizzaOrder.getTransactionMode());
		
		return p;
	}
	
	/*Pizza Entity to PizzaDto Class Conversion*/
	public PizzaOrderDto entityToDto(PizzaOrder pizzaOrder) {
		
		PizzaOrderDto p = new PizzaOrderDto();
		p.setBookingOrderId(pizzaOrder.getBookingOrderId());
		try {
		p.setCouponName(pizzaOrder.getCoupon().getCouponName());}
		catch(Exception ignored) {}
		p.setCustId(pizzaOrder.getCustomer().getId());
		p.setOrderDate(pizzaOrder.getOrderDate());
		p.setOrderType(pizzaOrder.getOrderType());
		p.setQuantity(pizzaOrder.getQuantity());
		//p.setSize(pizzaOrder.getSize());
		p.setTotalCost(pizzaOrder.getTotalCost());
		p.setTransactionMode(pizzaOrder.getTransactionMode());
//		List<Integer> pizzaIdList = new ArrayList<>();
//		pizzaIdList = pizzaOrder.getPizza().stream().map(t->t.getPizzaId()).collect(Collectors.toList());
//		p.setPizzaIdList(pizzaIdList);
		p.setPizzaList(pizzaOrder.getPizza());
		return p;
	}
	
	/*Calculate total*/
	public double calcTotal(Coupon coupon, List<Pizza> orderPizzas) { 
		
		double totalCost = orderPizzas.stream().mapToDouble(p->p.getPizzaCost()).sum();
		try {
		if(coupon.getCouponType().equals("FLAT")) {
			if(totalCost >= coupon.getAmount()) {
				totalCost -= coupon.getDiscount();
			}
		}
		else if(coupon.getCouponType().equals("PERCENTAGE")) {
		
			double discountAmt = (coupon.getDiscount() * totalCost)/100; 
			if(discountAmt > coupon.getAmount()) {
				discountAmt = coupon.getAmount();
			}
			totalCost -= discountAmt;
		}	
		}
		catch(Exception igonred){
			
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
