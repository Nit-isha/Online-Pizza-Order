package com.cg.onlinepizza.customer.service;

import java.util.List;

import com.cg.onlinepizza.customer.dto.CustomerDto;
import com.cg.onlinepizza.exceptions.CustomerIdNotFoundException;

public interface ICustomerService {
	CustomerDto addCustomer(CustomerDto customer);

	CustomerDto updateCustomer(CustomerDto customer);

	CustomerDto deleteCustomer(int customerId) throws CustomerIdNotFoundException;

	List<CustomerDto> viewCustomers();

	CustomerDto viewCustomer(int customerId) throws CustomerIdNotFoundException;
}
