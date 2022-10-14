package com.cg.onlinepizza.customer.dao;

import java.util.List;

import com.cg.onlinepizza.customer.dto.Customer;
import com.cg.onlinepizza.exceptions.CustomerIdNotFoundException;

public interface ICustomerRepository {
	Customer addCustomer(Customer customer);

	Customer updateCustomer(Customer customer);

	Customer deleteCustomer(int customerId) throws CustomerIdNotFoundException;

	List<Customer> viewCustomers();

	Customer viewCustomer(int customerId) throws CustomerIdNotFoundException;

}
