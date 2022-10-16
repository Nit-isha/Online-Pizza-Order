package com.cg.onlinepizza.pizza.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cg.onlinepizza.entity.Pizza;
import com.cg.onlinepizza.exceptions.InvalidMinCostException;
import com.cg.onlinepizza.exceptions.PizzaIdNotFoundException;
import com.cg.onlinepizza.pizza.dto.PizzaDto;

@Repository
public interface IPizzaRepository extends CrudRepository<Pizza, Integer>{
	

}
