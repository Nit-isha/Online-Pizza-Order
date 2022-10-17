package com.cg.onlinepizza.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.cg.onlinepizza.customer.dto.CustomerDto;
import com.cg.onlinepizza.customer.service.ICustomerService;
import com.cg.onlinepizza.exceptions.CustomerIdNotFoundException;
import com.cg.onlinepizza.exceptions.InvalidMinCostException;
import com.cg.onlinepizza.exceptions.PizzaAlreadyExistException;
import com.cg.onlinepizza.exceptions.PizzaIdNotFoundException;
import com.cg.onlinepizza.pizza.dto.PizzaDto;
import com.cg.onlinepizza.pizza.service.IPizzaService;

@RestController
public class MyController {
	@Autowired
	private IPizzaService pizzaService;
	@Autowired
	private ICustomerService customerService;
	
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
//	@PreAuthorize("hasAuthority('admin')")
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
//	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<PizzaDto> deletePizzaById(@PathVariable int pizzaId) throws PizzaIdNotFoundException{
		return new ResponseEntity<PizzaDto>(pizzaService.deletePizza(pizzaId), HttpStatus.OK);
	}
	
	/*Update Pizza [Only Admin can access]*/
	@PutMapping(path = "/menu/{pizzaId}",produces = {"application/json","application/xml"},
			consumes = {"application/json","application/xml"})
//	@PreAuthorize("hasAuthority('admin')")
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
	
	/*Update Customer [user and admin can access]*/
   @PutMapping(path = "/customer/{custId}",produces = {"application/json","application/xml"},consumes = {"application/json","application/xml"})
  public ResponseEntity<CustomerDto> updateCustomer(@PathVariable int custId,@RequestBody CustomerDto customerDto) throws CustomerIdNotFoundException{
      return new ResponseEntity<CustomerDto>(customerService.updateCustomer(custId,customerDto), HttpStatus.OK);
    }
    
   // View customer list.. only admin can have access
   @GetMapping(path = "/customer", produces = {"application/json","application/xml"})
	public ResponseEntity<List<CustomerDto>> getCustomerList() {
		List<CustomerDto> customerList = customerService.viewCustomers();
		return new ResponseEntity<List<CustomerDto>>(customerList, HttpStatus.OK);
	}
   
   //Get Customer By ID, Admin can access
	@GetMapping(path = "/customer/{custId}", produces = {"application/json","application/xml"})
	public ResponseEntity<CustomerDto> viewCustomerById(@PathVariable int custId) throws CustomerIdNotFoundException{
		return new ResponseEntity<CustomerDto>(customerService.viewCustomer(custId), HttpStatus.OK);
	}
	
	/*Delete Customer from DB [Only Admin can access]*/
	@DeleteMapping(path = "/customer/{custId}", produces = {"application/json","application/xml"})
	public ResponseEntity<CustomerDto> deleteCustomerById(@PathVariable int custId) throws CustomerIdNotFoundException{
		return new ResponseEntity<CustomerDto>(customerService.deleteCustomer(custId), HttpStatus.OK);
	}
}
