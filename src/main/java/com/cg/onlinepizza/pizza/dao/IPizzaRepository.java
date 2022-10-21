package com.cg.onlinepizza.pizza.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cg.onlinepizza.entity.Pizza;
import com.cg.onlinepizza.entity.PizzaOrder;

@Repository
public interface IPizzaRepository extends CrudRepository<Pizza, Integer>{
	
	@Query("select p from Pizza p where p.pizzaCost > :min and p.pizzaCost < :max")
	List<Pizza> filterPizzaByPrice(@Param("min") double minCost, @Param("max") double maxCost);
	
		//List<Pizza> pizzaList = new ArrayList<>();
		@Query("select p from Pizza p where p.pizzaId in :pList")
		List<Pizza> getPizzaListById(@Param("pList") List<Integer> pizzaIdList );
		
		/*@Query("select p from Pizza where p.bookingId= :bid")
		List<Pizza> getPizzaListByOrderId(@Param("bid")int bookingId);*/
		
		@Query(value = "select pizza_id from pizza", nativeQuery = true)
		List<Integer> getPizzaIdList();
		
		@Query(value = "select pizza_name from pizza", nativeQuery = true)
		List<String> getPizzaNameList();
}
