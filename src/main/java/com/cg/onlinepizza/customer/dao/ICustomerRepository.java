package com.cg.onlinepizza.customer.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.onlinepizza.customer.dto.CustomerDto;
import com.cg.onlinepizza.entity.Customer;
import com.cg.onlinepizza.entity.Pizza;
import com.cg.onlinepizza.exceptions.CustomerIdNotFoundException;

@Repository
public interface ICustomerRepository extends CrudRepository<Customer, Integer>{
	
	
}