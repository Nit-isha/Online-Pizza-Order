package com.cg.onlinepizza.customer.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cg.onlinepizza.entity.Customer;

@Repository
public interface ICustomerRepository extends CrudRepository<Customer, Integer>{
	
	@Query(value="select c from Customer c where c.username=:uname")
	public Optional<Customer> findByUsername(@Param("uname") String userName);
	

	@Query("select c from Customer c where c.id =:cid")
	Customer getCustomerById(@Param("cid") int cid);
	
	@Query(value="select c from Customer c where c.customerName=:name")
	public Optional<Customer> findCustomerByName(@Param("name") String name);
}