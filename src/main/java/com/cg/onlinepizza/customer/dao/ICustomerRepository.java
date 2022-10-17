package com.cg.onlinepizza.customer.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.cg.onlinepizza.entity.Customer;

@Repository
public interface ICustomerRepository extends CrudRepository<Customer, Integer>{
	
	
}