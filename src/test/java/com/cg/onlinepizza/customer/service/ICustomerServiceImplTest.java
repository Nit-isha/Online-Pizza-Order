package com.cg.onlinepizza.customer.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.cg.onlinepizza.customer.dao.ICustomerRepository;
import com.cg.onlinepizza.customer.dto.CustomerDto;
import com.cg.onlinepizza.entity.Customer;
import com.cg.onlinepizza.exceptions.CustomerAlreadyExistException;
import com.cg.onlinepizza.exceptions.CustomerIdNotFoundException;

//@RunWith(SpringRunner.class)
//@SpringBootTest
class ICustomerServiceImplTest {
	@MockBean
    private ICustomerRepository iCustomerRepository ;
    @MockBean
    private Customer customer;
    
    @Autowired
    private ICustomerService iCustomerService;
    
    private static List<Customer> customerList = new ArrayList<>();
    
    @BeforeAll
    static void setUp() {
        Customer obj1 = new Customer();
        obj1.setCustomerName("Harit");obj1.setCustomerMobile("9660040281");obj1.setCustomerEmail("harit@gmail.com");
        obj1.setCustomerAddress("alwar");obj1.setId(101);
        Customer obj2 = new Customer();
        obj2.setCustomerName("Tapesh");obj2.setCustomerMobile("6960040281");obj2.setCustomerEmail("tapesh@gmail.com");
        obj2.setCustomerAddress("shimla");obj2.setId(102);
        customerList.add(obj1);
        customerList.add(obj2);
    }
    
	@Test
	void testAddCustomer() throws CustomerAlreadyExistException {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateCustomer() throws CustomerIdNotFoundException {
		when(iCustomerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
    	CustomerDto  customerDto = iCustomerService.updateCustomer(customer.getId(), entityToDto(customer));
    	assertEquals(entityToDto(customer).getCustomerName(), customerDto.getCustomerName());
	}

	@Test
	void testDeleteCustomer() {
		fail("Not yet implemented");
	}

	@Test
	void testViewCustomers() {
		fail("Not yet implemented");
	}

	@Test
	void testViewCustomer() {
		fail("Not yet implemented");
	}
	public Customer dtoToEntity(CustomerDto customer) {
		Customer c = new ModelMapper().map(customer,Customer.class);
        return c;
    }
    public CustomerDto entityToDto(Customer customer) {
    	CustomerDto c = new ModelMapper().map(customer,CustomerDto.class);
        return c;
    }

}
