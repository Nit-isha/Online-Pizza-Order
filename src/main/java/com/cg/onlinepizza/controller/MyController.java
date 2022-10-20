package com.cg.onlinepizza.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.onlinepizza.coupon.dto.CouponDto;
import com.cg.onlinepizza.coupon.service.ICouponService;
import com.cg.onlinepizza.customer.dto.CustomerDto;
import com.cg.onlinepizza.customer.service.ICustomerService;
import com.cg.onlinepizza.exceptions.CouponAlreadyExistException;
import com.cg.onlinepizza.exceptions.CouponIdNotFoundException;
import com.cg.onlinepizza.exceptions.CustomerIdNotFoundException;
import com.cg.onlinepizza.exceptions.InvalidMinCostException;
import com.cg.onlinepizza.exceptions.NoOrdersFoundException;
import com.cg.onlinepizza.exceptions.OrderCancelDeclinedException;
import com.cg.onlinepizza.exceptions.OrderIdNotFoundException;
import com.cg.onlinepizza.exceptions.OrderUpdateDeclinedException;
import com.cg.onlinepizza.exceptions.PizzaAlreadyExistException;
import com.cg.onlinepizza.exceptions.PizzaIdNotFoundException;
import com.cg.onlinepizza.pizza.dto.PizzaDto;
import com.cg.onlinepizza.pizza.service.IPizzaService;
import com.cg.onlinepizza.pizzaorder.dto.PizzaOrderDto;
import com.cg.onlinepizza.pizzaorder.service.IPizzaOrderService;
import com.fasterxml.jackson.annotation.JsonFormat;

@RestController
public class MyController {
	@Autowired
	private IPizzaService pizzaService;
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private ICouponService couponService;
	@Autowired
	private IPizzaOrderService pizzaOrderService;
	
	/*-----------------  Pizza Service Controllers  -----------------*/
	
	
	/*Get Pizza List [Both Admin and User can access]*/
	@GetMapping(path = "/menu", produces = {"application/json","application/xml"})
	public ResponseEntity<List<PizzaDto>> getPizzaList() {
		List<PizzaDto> pizzaList = pizzaService.viewPizzaList();
		return new ResponseEntity<List<PizzaDto>>(pizzaList, HttpStatus.OK);
	}

	/*Add Pizza to DB [Only Admin can access]*/
	@PostMapping(path = "/addpizza",produces = {"application/json","application/xml"},
			consumes = {"application/json","application/xml"})
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<PizzaDto> addPizza(@RequestBody PizzaDto pizzaDto) throws PizzaAlreadyExistException{
		return new ResponseEntity<PizzaDto>(pizzaService.addPizza(pizzaDto), HttpStatus.OK);
	}

	/*Get Pizza By ID [Both Admin and User can access]*/
	@GetMapping(path = "/menu/{pizzaId}", produces = {"application/json","application/xml"})
	public ResponseEntity<PizzaDto> viewPizzaById(@PathVariable int pizzaId) throws PizzaIdNotFoundException{
		return new ResponseEntity<PizzaDto>(pizzaService.viewPizza(pizzaId), HttpStatus.OK);
	}
	
	/*Delete Pizza from DB [Only Admin can access]*/
	@DeleteMapping(path = "/menu/{pizzaId}", produces = {"application/json","application/xml"})
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<PizzaDto> deletePizzaById(@PathVariable int pizzaId) throws PizzaIdNotFoundException{
		return new ResponseEntity<PizzaDto>(pizzaService.deletePizza(pizzaId), HttpStatus.OK);
	}
	
	/*Update Pizza [Only Admin can access]*/
	@PutMapping(path = "/menu/{pizzaId}",produces = {"application/json","application/xml"},
			consumes = {"application/json","application/xml"})
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<PizzaDto> updatePizza(@PathVariable int pizzaId,@RequestBody PizzaDto pizzaDto) throws PizzaIdNotFoundException{
		return new ResponseEntity<PizzaDto>(pizzaService.updatePizza(pizzaId,pizzaDto), HttpStatus.OK);
	}
	
	/*Filter Pizza by Cost Range [Both Admin and User can access]*/
	@GetMapping( value = "/menu/search", produces = {"application/json","application/xml"})
	public ResponseEntity<List<PizzaDto>> filterPizzaListByRange(@RequestParam("min") 
	double minCost,@RequestParam("max") double maxCost) throws InvalidMinCostException {
		List<PizzaDto> pizzaList = pizzaService.viewPizzaList(minCost,maxCost);
		return new ResponseEntity<List<PizzaDto>>(pizzaList, HttpStatus.OK);
	}
	
	/*-----------------  Customer Service Controllers  -----------------*/
	
	
	/*Update Customer [User can access]*/
   @PutMapping(path = "/customer/update",produces = {"application/json","application/xml"},consumes = {"application/json","application/xml"})
   public ResponseEntity<CustomerDto> updateCustomer(Principal currentCustomer,@RequestBody CustomerDto customerDto) throws CustomerIdNotFoundException{
      return new ResponseEntity<CustomerDto>(customerService.updateCustomer(currentCustomer, customerDto), HttpStatus.OK);
    }
    
   /*View Customer list [Only admin can access]*/
   @GetMapping(path = "/customer", produces = {"application/json","application/xml"})
   @PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<List<CustomerDto>> getCustomerList() {
		List<CustomerDto> customerList = customerService.viewCustomers();
		return new ResponseEntity<List<CustomerDto>>(customerList, HttpStatus.OK);
	}
   
   /*Get Customer By ID [Only admin can access]*/
	@GetMapping(path = "/customer/{custId}", produces = {"application/json","application/xml"})
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<CustomerDto> viewCustomerById(@PathVariable int custId) throws CustomerIdNotFoundException{
		return new ResponseEntity<CustomerDto>(customerService.viewCustomer(custId), HttpStatus.OK);
	}
	
	/*Get Customer Details [Only user can access]*/
	@GetMapping(path = "/customer/about", produces = {"application/json","application/xml"})
	public ResponseEntity<CustomerDto> aboutCustomer(Principal currentCustomer) {
		return new ResponseEntity<CustomerDto>(customerService.aboutCustomer(currentCustomer), HttpStatus.OK);
	}
	
	/*Delete Customer from DB [Only Admin can access]*/
	@DeleteMapping(path = "/customer/{custId}", produces = {"application/json","application/xml"})
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<CustomerDto> deleteCustomerById(@PathVariable int custId) throws CustomerIdNotFoundException{
		return new ResponseEntity<CustomerDto>(customerService.deleteCustomer(custId), HttpStatus.OK);
	}
	
	/*-----------------  Coupon Service Controllers  -----------------*/
	
	
	/*Add Coupon to DB [Only Admin can access]*/
	
	@PostMapping(path = "/coupon",produces = {"application/json","application/xml"},
			consumes = {"application/json","application/xml"})
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<CouponDto> addCoupon(@Valid @RequestBody CouponDto couponDto) throws CouponAlreadyExistException {
		return new ResponseEntity<CouponDto>(couponService.addCoupons(couponDto), HttpStatus.OK);
	}
	
	/*Delete Coupon from DB [Only Admin can access]*/
	@DeleteMapping(path = "/coupon/{couponId}", produces = {"application/json","application/xml"})
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<CouponDto> deleteCouponById(@PathVariable int couponId) throws CouponIdNotFoundException{
		return new ResponseEntity<CouponDto>(couponService.deleteCoupons(couponId), HttpStatus.OK);
	}
	
	/*Get Coupon List [Both Admin and User can access]*/
	@GetMapping(path = "/coupon", produces = {"application/json","application/xml"})
	public ResponseEntity<List<CouponDto>> getCouponList() {
		List<CouponDto> couponList = couponService.viewCoupons();
		return new ResponseEntity<List<CouponDto>>(couponList, HttpStatus.OK);
	}
	
	/*Update Coupon [Only Admin can access]*/
	@PutMapping(path = "/coupon/{couponId}",produces = {"application/json","application/xml"},
			consumes = {"application/json","application/xml"})
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<CouponDto> editCoupon(@PathVariable int couponId,@RequestBody CouponDto couponDto) throws CouponIdNotFoundException{
		return new ResponseEntity<CouponDto>(couponService.editCoupons(couponId,couponDto), HttpStatus.OK);
	}
	
	/*Get Coupon By ID [Both Admin and User can access]*/
	@GetMapping(path = "/coupon/{couponId}", produces = {"application/json","application/xml"})
	public ResponseEntity<CouponDto> viewCouponId(@PathVariable int couponId) throws CouponIdNotFoundException{
		return new ResponseEntity<CouponDto>(couponService.viewCouponId(couponId), HttpStatus.OK);
	}
	
	
	/*-----------------  Pizza Order Service Controllers  -----------------*/
	
	
	/*Get Customer order history [Only User can access]*/
	@GetMapping(path="/orders", produces = {"application/json","application/xml"})
	public ResponseEntity<List<PizzaOrderDto>> viewCustomerOrderHistory(Principal currentCustomer){
		return new ResponseEntity<List<PizzaOrderDto>>(pizzaOrderService.viewCustomerOrdersList(currentCustomer), HttpStatus.OK);
	}
	
	/*Get All Orders [Only Admin can access]*/
	@GetMapping(path="/allorders", produces = {"application/json","application/xml"})
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<List<PizzaOrderDto>> viewAllOrders(){
		return new ResponseEntity<List<PizzaOrderDto>>(pizzaOrderService.viewOrdersList(), HttpStatus.OK);
	}
	
	/*View any pizza by ID [Only Admin can access]*/
	@GetMapping(path="/allorders/{orderId}", produces = {"application/json","application/xml"})
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<PizzaOrderDto> viewPizzaOrder(@PathVariable int orderId) throws OrderIdNotFoundException {
		return new ResponseEntity<PizzaOrderDto>(pizzaOrderService.viewPizzaOrder(orderId), HttpStatus.OK);
	}
	
	/*Book Pizza Order [Only User can access]*/
	@PostMapping(path="/orders/neworder", produces = {"application/json","application/xml"},
			consumes = {"application/json","application/xml"})
	public ResponseEntity<PizzaOrderDto> bookPizzaOrder(Principal currentCustomer, @RequestBody PizzaOrderDto pizzaOrderDto) {
		return new ResponseEntity<PizzaOrderDto>(pizzaOrderService.bookPizzaOrder(currentCustomer,pizzaOrderDto), HttpStatus.OK);
	}
	
	/*Update Pizza Order [Only User can access]*/
	@PutMapping(path="/orders/{orderId}", produces = {"application/json","application/xml"},
			consumes = {"application/json","application/xml"})
	public ResponseEntity<PizzaOrderDto> updatePizzaOrder(@PathVariable int orderId, Principal currentCustomer, @RequestBody PizzaOrderDto pizzaOrderDto) throws OrderIdNotFoundException, OrderUpdateDeclinedException {
		return new ResponseEntity<PizzaOrderDto>(pizzaOrderService.updatePizzaOrder(currentCustomer,orderId, pizzaOrderDto), HttpStatus.OK);
	}
	
	/*View Customer pizza order by ID [Only User can access]*/
	@GetMapping(path="/orders/{orderId}", produces = {"application/json","application/xml"})
	public ResponseEntity<PizzaOrderDto> viewCustomerPizzaOrderById(@PathVariable int orderId, Principal currentCustomer) throws OrderIdNotFoundException {
		return new ResponseEntity<PizzaOrderDto>(pizzaOrderService.viewCustomerPizzaOrderById(currentCustomer,orderId), HttpStatus.OK);
	}
	
	/*Cancel Pizza Order within 15 minutes [Both Admin and User can access]*/
	@DeleteMapping(path="/orders/{orderId}", produces = {"application/json","application/xml"})
	public ResponseEntity<PizzaOrderDto> cancelPizzaOrder(Principal currentCustomer,@PathVariable int orderId) throws OrderIdNotFoundException, OrderCancelDeclinedException {
		return new ResponseEntity<PizzaOrderDto>(pizzaOrderService.cancelPizzaOrder(currentCustomer,orderId), HttpStatus.OK);
	}
	
	/*Filter All Orders By Date [Only Admin can access]*/
	@GetMapping(path="/allorders/ordersbydate", produces = {"application/json","application/xml"}, consumes = {"application/json","application/xml"})
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<List<PizzaOrderDto>> viewAllOrdersByDate(@RequestParam(value="date") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date) throws NoOrdersFoundException {
		return new ResponseEntity<List<PizzaOrderDto>>(pizzaOrderService.viewAllOrdersByDate(date), HttpStatus.OK);
		//localhost:8081/allorders/ordersbydate?date=2022-10-19
	}
	
	/*Filter All Orders By Date [Only Customer can access]*/
		@GetMapping(path="/orders/ordersbydate", produces = {"application/json","application/xml"}, consumes = {"application/json","application/xml"})
	public ResponseEntity<List<PizzaOrderDto>> viewCustomerOrdersByDate(Principal currentCustomer,@RequestParam(value="date") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date) throws NoOrdersFoundException {
	return new ResponseEntity<List<PizzaOrderDto>>(pizzaOrderService.viewCustomerOrdersByDate(currentCustomer,date), HttpStatus.OK);
	//localhost:8081/orders/ordersbydate?date=2022-10-19
	}
	
	
}

