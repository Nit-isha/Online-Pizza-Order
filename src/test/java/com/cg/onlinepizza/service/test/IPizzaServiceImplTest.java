package com.cg.onlinepizza.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.cg.onlinepizza.entity.Pizza;
import com.cg.onlinepizza.exceptions.PizzaIdNotFoundException;
import com.cg.onlinepizza.pizza.dao.IPizzaRepository;
import com.cg.onlinepizza.pizza.dto.PizzaDto;
import com.cg.onlinepizza.pizza.service.IPizzaService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IPizzaServiceImplTest {
    @MockBean
    private IPizzaRepository iPizzaRepository;
    
    @Autowired
    private IPizzaService iPizzaService;
    
    private static List<Pizza> pizzaList = new ArrayList<>();
    
    @BeforeAll
    static void setUp() {
        Pizza obj1 = new Pizza(101, "Veg", "Farm House", "Delightful combination of onion, capsicum, tomato & grilled mushroom", 469);
        Pizza obj2 = new Pizza(102, "Veg", "Peppy Paneer", "Flavorful trio of juicy paneer, crisp capsicum with spicy red paprika", 469);
        pizzaList.add(obj1);
        pizzaList.add(obj2);
    }
    
    @Test
    void testViewPizzaList() {
        when(iPizzaRepository.findAll()).thenReturn(pizzaList);
        List<PizzaDto> pList = iPizzaService.viewPizzaList();
        assertEquals(pizzaList.size(), pList.size());
    }
    
    @Test
    void testViewPizza() throws PizzaIdNotFoundException {
        Pizza pizza = new Pizza(101, "Veg", "Farm House", "Delightful combination of onion, capsicum, tomato & grilled mushroom", 469);
        when(iPizzaRepository.findById(101)).thenReturn(Optional.of(pizza));
        PizzaDto pizzaDtoObj = iPizzaService.viewPizza(101);
        assertEquals(pizza.getPizzaName(), pizzaDtoObj.getPizzaName());
        assertEquals(pizza.getPizzaType(), pizzaDtoObj.getPizzaType());
        assertEquals(pizza.getPizzaCost(), pizzaDtoObj.getPizzaCost());
    }
    
//    @Test
//    void testAddPizza() throws PizzaIdNotFoundException {
//        Pizza pizza = new Pizza(101, "Veg", "Farm House", "Delightful combination of onion, capsicum, tomato & grilled mushroom", 469);
//        when(iPizzaRepository.save(pizza)).thenReturn(pizza);
//        PizzaDto pizzaDtoObj = iPizzaService.addPizza(pizza);
//    }

}
