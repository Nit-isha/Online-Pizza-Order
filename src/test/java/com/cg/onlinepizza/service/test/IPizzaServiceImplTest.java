package com.cg.onlinepizza.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.cg.onlinepizza.entity.Pizza;
import com.cg.onlinepizza.entity.PizzaOrder;
import com.cg.onlinepizza.exceptions.InvalidMinCostException;
import com.cg.onlinepizza.exceptions.PizzaAlreadyExistException;
import com.cg.onlinepizza.exceptions.PizzaIdNotFoundException;
import com.cg.onlinepizza.pizza.dao.IPizzaRepository;
import com.cg.onlinepizza.pizza.dto.PizzaDto;
import com.cg.onlinepizza.pizza.service.IPizzaService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IPizzaServiceImplTest {
    @MockBean
    private IPizzaRepository iPizzaRepository;
    @MockBean
    private Pizza pizza;
    
    @Autowired
    private IPizzaService iPizzaService;
    
    private static List<Pizza> pizzaList = new ArrayList<>();
    
    @BeforeAll
    static void setUp() {
        Pizza obj1 = new Pizza("Veg", "Farm House","Medium", "Delightful combination of onion, capsicum, tomato & grilled mushroom", 469);
        Pizza obj2 = new Pizza("Veg", "Peppy Paneer","Medium", "Flavorful trio of juicy paneer, crisp capsicum with spicy red paprika", 345);
        pizzaList.add(obj1);
        pizzaList.add(obj2);
    }
    
    @Test
   	void testAddPizza() throws PizzaAlreadyExistException {
   		when(iPizzaRepository.save(Mockito.any(Pizza.class))).thenReturn(pizza);
   		PizzaDto p2 = iPizzaService.addPizza(entityToDto(pizza));
   		assertEquals(pizza.getPizzaId(), p2.getPizzaId());
   	}
    
    @Test
    void testUpdatePizza() throws PizzaIdNotFoundException, PizzaAlreadyExistException {
    	when(iPizzaRepository.findById(pizza.getPizzaId())).thenReturn(Optional.of(pizza));
    	PizzaDto  pizzaDto = iPizzaService.updatePizza(pizza.getPizzaId(), entityToDto(pizza));
    	assertEquals(entityToDto(pizza).getPizzaId(), pizzaDto.getPizzaId());
    	assertEquals(entityToDto(pizza).getPizzaName(), pizzaDto.getPizzaName());
    }
    
    @Test
    void testDeletePizza() throws PizzaIdNotFoundException {
    	when(iPizzaRepository.findById(pizza.getPizzaId())).thenReturn(Optional.of(pizza));
    	PizzaDto pizzaDto = iPizzaService.deletePizza(pizza.getPizzaId());
    	assertEquals(entityToDto(pizza).getPizzaId(), pizzaDto.getPizzaId());
    	assertEquals(entityToDto(pizza).getPizzaName(), pizzaDto.getPizzaName());
    }
    
    @Test
    void testViewPizza() throws PizzaIdNotFoundException {
      //  Pizza pizza = new Pizza(101, "Veg", "Farm House", "Delightful combination of onion, capsicum, tomato & grilled mushroom", 469);
        when(iPizzaRepository.findById(pizza.getPizzaId())).thenReturn(Optional.of(pizza));
        PizzaDto pizzaDtoObj = iPizzaService.viewPizza(pizza.getPizzaId());
        assertEquals(pizza.getPizzaName(), pizzaDtoObj.getPizzaName());
        assertEquals(pizza.getPizzaType(), pizzaDtoObj.getPizzaType());
        assertEquals(pizza.getPizzaCost(), pizzaDtoObj.getPizzaCost());
    }
    
    @Test
    void testViewPizzaList() {
        when(iPizzaRepository.findAll()).thenReturn(pizzaList);
        List<PizzaDto> pList = iPizzaService.viewPizzaList(); 
        assertEquals(pizzaList.size(), pList.size());
    }
    
    @Test
    void testViewPizzaListByRange() throws InvalidMinCostException {
    	double min = 300;
    	double max = 500;
    	when(iPizzaRepository.filterPizzaByPrice(min, max)).thenReturn(pizzaList);
    	List<PizzaDto> pList = iPizzaService.viewPizzaListByRange(min,max);
    	assertEquals(pizzaList.size(), pList.size());
    }
    
    public Pizza dtoToEntity(PizzaDto pizza) {
    	Pizza p= new ModelMapper().map(pizza,Pizza.class);
    	/*
        Pizza p = new Pizza();
        p.setPizzaId(pizza.getPizzaId());
        p.setPizzaName(pizza.getPizzaName());
        p.setPizzaType(pizza.getPizzaType());
        p.setPizzaDescription(pizza.getPizzaDescription());
        p.setPizzaCost(pizza.getPizzaCost());
        */
        return p;
    }
    public PizzaDto entityToDto(Pizza pizza) {
    	PizzaDto p= new ModelMapper().map(pizza,PizzaDto.class);
    	/*
        PizzaDto p = new PizzaDto();
        p.setPizzaId(pizza.getPizzaId());
        p.setPizzaName(pizza.getPizzaName());
        p.setPizzaType(pizza.getPizzaType());
        p.setPizzaDescription(pizza.getPizzaDescription());
        p.setPizzaCost(pizza.getPizzaCost());
        */
        return p;
    }
}
