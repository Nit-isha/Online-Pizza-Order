package com.cg.onlinepizza.OnlinePizzaOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.criteria.internal.expression.SearchedCaseExpression.WhenClause;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.onlinepizza.entity.Pizza;
import com.cg.onlinepizza.exceptions.PizzaAlreadyExistException;
import com.cg.onlinepizza.pizza.dao.IPizzaRepository;
import com.cg.onlinepizza.pizza.dto.PizzaDto;
import com.cg.onlinepizza.pizza.service.IPizzaServiceImpl;

@SpringBootTest
class OnlinePizzaOrderApplicationTests {

	// Testing Pizza Service Implementation
	@Autowired
	private IPizzaServiceImpl pizzaService;
	@MockBean
	private IPizzaRepository pizzaRepository;
	@MockBean
	private Pizza pizza;
	private static List<Pizza> listPizzaDummy = new ArrayList();

	static void setUp() {
		Pizza p1 = new Pizza();
		p1.setPizzaId(102);
		p1.setPizzaType("Veg");
		p1.setPizzaName("Farm House");
		p1.setPizzaDescription("Delightful combination of onion, capsicum, tomato & grilled mushroom");
		p1.setPizzaCost(469);
		Pizza p2 = new Pizza();
		p2.setPizzaId(103);
		p2.setPizzaType("Veg");
		p2.setPizzaName("Peppy Paneer");
		p2.setPizzaDescription("Flavorful trio of juicy paneer, crisp capsicum with spicy red paprika");
		p2.setPizzaCost(512);
		listPizzaDummy.add(p1); listPizzaDummy.add(p2);
	}

	@Test
	void testViewPizzaList() {
		// precondition
		when(pizzaRepository.findAll()).thenReturn(listPizzaDummy);
		List<PizzaDto> listDto = pizzaService.viewPizzaList();
		List<Pizza> listPizza = new ArrayList<>();
		for(PizzaDto p: listDto) {
			listPizza.add(dtoToEntity(p));
		}
		assertEquals(listPizzaDummy, listPizza);
	}
	@Test
	void testAddPizza() throws PizzaAlreadyExistException {
		// precondition
//		Pizza p1 = new Pizza();
//		p1.setPizzaId(102);
//		p1.setPizzaType("Veg");
//		p1.setPizzaName("Farm House");
//		p1.setPizzaDescription("Delightful combination of onion, capsicum, tomato & grilled mushroom");
//		p1.setPizzaCost(469);
		when(pizzaRepository.save(Mockito.any(Pizza.class))).thenReturn(pizza);
		PizzaDto p2 = pizzaService.addPizza(entityToDto(pizza));
		assertEquals(pizza.getPizzaId(), p2.getPizzaId());
	}

	public Pizza dtoToEntity(PizzaDto pizza) {
		Pizza p = new Pizza();
		p.setPizzaId(pizza.getPizzaId());
		p.setPizzaName(pizza.getPizzaName());
		p.setPizzaType(pizza.getPizzaType());
		p.setPizzaDescription(pizza.getPizzaDescription());
		p.setPizzaCost(pizza.getPizzaCost());
		return p;
	}
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
