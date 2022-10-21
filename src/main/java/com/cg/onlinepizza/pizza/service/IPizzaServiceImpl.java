package com.cg.onlinepizza.pizza.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cg.onlinepizza.entity.Pizza;
import com.cg.onlinepizza.exceptions.InvalidMinCostException;
import com.cg.onlinepizza.exceptions.PizzaAlreadyExistException;
import com.cg.onlinepizza.exceptions.PizzaIdNotFoundException;
import com.cg.onlinepizza.pizza.dao.IPizzaRepository;
import com.cg.onlinepizza.pizza.dto.PizzaDto;

@Component
public class IPizzaServiceImpl implements IPizzaService{
	@Autowired
	private IPizzaRepository iPizzaRepository;

	/*Add Pizza Method*/
	@Override
	public PizzaDto addPizza(PizzaDto pizza) throws PizzaAlreadyExistException {
		Optional<Pizza> optional = iPizzaRepository.findById(pizza.getPizzaId());
		if(optional.isPresent()) {
			throw new PizzaAlreadyExistException();
		}
		iPizzaRepository.save(dtoToEntity(pizza));
		return pizza;
	}

	/*Update Pizza Method*/
	@Override
	public PizzaDto updatePizza(int pizzaId,PizzaDto pizza) throws PizzaIdNotFoundException {
		Optional<Pizza> optional = iPizzaRepository.findById(pizzaId);
		if(optional.isPresent()) {
			Pizza pizzaEntity = dtoToEntity(pizza);
			pizzaEntity.setPizzaId(optional.get().getPizzaId());
			iPizzaRepository.save(pizzaEntity);
			return entityToDto(pizzaEntity);
		}else {
			throw new PizzaIdNotFoundException();
		}
	}

	/*Delete Pizza Method*/
	@Override
	public PizzaDto deletePizza(int pizzaId) throws PizzaIdNotFoundException {
		Optional<Pizza> optional = iPizzaRepository.findById(pizzaId);
		if(optional.isPresent()) {
			iPizzaRepository.deleteById(pizzaId);
			return entityToDto(optional.get());
		}else {
			throw new PizzaIdNotFoundException();
		}	
	}

	/*Find Pizza By ID Method*/
	@Override
	public PizzaDto viewPizza(int pizzaId) throws PizzaIdNotFoundException {
		Optional<Pizza> optional = iPizzaRepository.findById(pizzaId);
		if(optional.isPresent()) {
			return entityToDto(optional.get());
		}else {
			throw new PizzaIdNotFoundException();
		}
	}

	/*View All Pizza Method*/
	@Override
	public List<PizzaDto> viewPizzaList() {
		List<Pizza> pizzaList = new ArrayList<>();

		Iterable<Pizza> list =  iPizzaRepository.findAll();
		list.forEach(p -> pizzaList.add(p));

		List<PizzaDto> pizzaDtoList = new ArrayList<>();
		for(Pizza pizza: pizzaList) {
			pizzaDtoList.add(entityToDto(pizza));
		}
		return pizzaDtoList;
	}

	/*Filter Pizza by Cost Range Method*/
	@Override
	public List<PizzaDto> viewPizzaList(double minCost, double maxCost) throws InvalidMinCostException {
		if(minCost<0 || minCost>maxCost) {
			throw new InvalidMinCostException();
		}
		List<Pizza> listPizza = iPizzaRepository.filterPizzaByPrice(minCost, maxCost);
		List<PizzaDto> pizzaDtoList = new ArrayList<>();
		for(Pizza pizza: listPizza) {
			pizzaDtoList.add(entityToDto(pizza));
		}
		return pizzaDtoList;
	}

	/*PizzaDto to Pizza Entity Class Conversion*/
	public Pizza dtoToEntity(PizzaDto pizza) {
		Pizza p = new Pizza();
		p.setPizzaId(pizza.getPizzaId());
		p.setPizzaName(pizza.getPizzaName());
		p.setPizzaType(pizza.getPizzaType());
		p.setPizzaDescription(pizza.getPizzaDescription());
		p.setPizzaCost(pizza.getPizzaCost());
		return p;
	}

	/*Pizza Entity to PizzaDto Class Conversion*/
	public PizzaDto entityToDto(Pizza pizza) {
		PizzaDto p = new PizzaDto();
		p.setPizzaId(pizza.getPizzaId());
		p.setPizzaName(pizza.getPizzaName());
		p.setPizzaType(pizza.getPizzaType());
		p.setPizzaDescription(pizza.getPizzaDescription());
		p.setPizzaCost(pizza.getPizzaCost());
		return p;
	}
}
