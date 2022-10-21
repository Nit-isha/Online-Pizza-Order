package com.cg.onlinepizza.customer.service;

import java.security.Principal;
import java.util.List;

import com.cg.onlinepizza.customer.dto.CustomerDto;
import com.cg.onlinepizza.exceptions.CustomerAlreadyExistException;
import com.cg.onlinepizza.exceptions.CustomerIdNotFoundException;

public interface ICustomerService {

	CustomerDto updateCustomer(String currentCustomer, CustomerDto customer)throws CustomerIdNotFoundException, CustomerAlreadyExistException;

	CustomerDto deleteCustomer(int customerId) throws CustomerIdNotFoundException;

	List<CustomerDto> viewCustomers();

	CustomerDto viewCustomer(int customerId) throws CustomerIdNotFoundException;
	
	CustomerDto aboutCustomer(Principal currentCustomer);
}
