package com.cg.onlinepizza.pizzaorder.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.onlinepizza.entity.PizzaOrder;

@Repository
public interface IPizzaOrderRepository extends CrudRepository <PizzaOrder, Integer> {
	@Query(value = "select * from pizza_order where cust_id =:custid", nativeQuery = true)
	List<PizzaOrder> getCustomerPizzaOrderHistory(@Param ("custid") int custid);	
}