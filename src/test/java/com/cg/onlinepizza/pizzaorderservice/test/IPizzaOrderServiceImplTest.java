package com.cg.onlinepizza.pizzaorderservice.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.cg.onlinepizza.entity.Coupon;
import com.cg.onlinepizza.entity.Customer;
import com.cg.onlinepizza.entity.PizzaOrder;
import com.cg.onlinepizza.pizzaorder.dao.IPizzaOrderRepository;
import com.cg.onlinepizza.pizzaorder.dto.PizzaOrderDto;
import com.cg.onlinepizza.pizzaorder.service.IPizzaOrderService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IPizzaOrderServiceImplTest {

	@Autowired
	private IPizzaOrderService pizzaOrderService;
	@MockBean
	private IPizzaOrderRepository pizzaOrderRepository;
	@MockBean
	private static Coupon coupon;
	@MockBean
	private static Customer customer;
	@MockBean
	private static LocalDateTime localDateTime;
	static List<PizzaOrder> list = new ArrayList<>();
	
	@BeforeAll
	static void setUp() {
		 PizzaOrder p1 = new PizzaOrder();
		 p1.setBookingOrderId(101);
		 p1.setCoupon(coupon);
		 p1.setCustomer(customer);
		 p1.setOrderDate(localDateTime);
		 p1.setOrderType("takeaway");
		 p1.setQuantity(0);
		 p1.setSize("Large");
		 p1.setTotalCost(300);
		 p1.setTransactionMode("UPI");
		 list.add(p1);
	}
	@Test
	void viewOrdersListTest() {
		when(pizzaOrderRepository.findAll()).thenReturn(list);
		List<PizzaOrderDto> responseList = pizzaOrderService.viewOrdersList();
		assertEquals(list.size(), responseList.size());
	}
//	@Test
//	void bookPizzaOrderTest() {
//		
//	}
//	@Test
//	void updatePizzaOrderTest() {
//		
//	}
//	@Test
//	void cancelPizzaOrderTest() {
//		
//	}
//	@Test
//	void viewPizzaOrderTest() {
//		
//	}
//	@Test
//	void viewCustomerPizzaOrderByIdTest() {
//		
//	}
//	@Test
//	void viewCustomerOrderByDate() {
//		
//	}
//	@Test
//	void viewAllOrdersByDateTest() {
//		
//	}
//	@Test
//	void viewCustomersOrdersListTest() {
//		
//	}
}
