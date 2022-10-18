package com.cg.onlinepizza.pizzaorder.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.onlinepizza.entity.Coupon;
import com.cg.onlinepizza.entity.Customer;
import com.cg.onlinepizza.entity.Pizza;
import com.cg.onlinepizza.entity.PizzaOrder;


@Repository
public interface IPizzaOrderRepository extends CrudRepository <PizzaOrder, Integer> {
	@Query("select c from coupon c where c.couponName  =:cname")
	Coupon getCouponByName(@Param("cname") String couponName);
	
	@Query("select c from customer c where c.custId =:cid")
	Customer getCustomerById(@Param("cid") int cid);
	
	//List<Pizza> pizzaList = new ArrayList<>();
	@Query("select p from pizza p where p.pizzaId in :pList =:pid")
	Pizza getPizzaById(@Param() @Param("pid")int pid);
	
}