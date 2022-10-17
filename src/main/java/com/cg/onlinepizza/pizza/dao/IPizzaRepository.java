package com.cg.onlinepizza.pizza.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cg.onlinepizza.entity.Pizza;

@Repository
public interface IPizzaRepository extends CrudRepository<Pizza, Integer>{
	
	@Query("select p from Pizza p where p.pizzaCost > :min and p.pizzaCost < :max")
	List<Pizza> filterPizzaByPrice(@Param("min") double minCost, @Param("max") double maxCost);
}