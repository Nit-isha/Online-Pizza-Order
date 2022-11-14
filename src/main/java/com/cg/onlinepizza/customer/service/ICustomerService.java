package com.cg.onlinepizza.customer.service;

import java.security.Principal;
import java.util.List;

import org.postgresql.util.PSQLException;

import com.cg.onlinepizza.customer.dto.CustomerDto;
import com.cg.onlinepizza.exceptions.CustomerAlreadyExistException;
import com.cg.onlinepizza.exceptions.CustomerIdNotFoundException;
import com.cg.onlinepizza.exceptions.CustomerNotFoundException;
import com.cg.onlinepizza.exceptions.UsernameAlreadyExistException;

public interface ICustomerService {

	CustomerDto updateCustomer(String currentCustomer, CustomerDto customer)throws CustomerAlreadyExistException, CustomerIdNotFoundException;
	
	CustomerDto updateCustomerDetails(String currentCustomer, CustomerDto customer);
	
	CustomerDto updateCustomerPassword(String currentCustomer, CustomerDto customer);
	
	CustomerDto updateCustomerUsername(String currentCustomer, CustomerDto customer) throws UsernameAlreadyExistException;

	CustomerDto deleteCustomer(int customerId) throws CustomerIdNotFoundException;

	List<CustomerDto> viewCustomers();

	CustomerDto viewCustomer(int customerId) throws CustomerIdNotFoundException;
	
	CustomerDto aboutCustomer(String currentCustomer);
	
	CustomerDto getCustomerByName(String name)throws CustomerNotFoundException;
}
