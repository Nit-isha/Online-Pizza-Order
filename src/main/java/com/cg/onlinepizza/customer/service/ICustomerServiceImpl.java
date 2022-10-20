package com.cg.onlinepizza.customer.service;

import java.security.Principal;
import java.util.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cg.onlinepizza.customer.dao.ICustomerRepository;
import com.cg.onlinepizza.customer.dto.CustomerDto;
import com.cg.onlinepizza.entity.Customer;
import com.cg.onlinepizza.exceptions.CustomerAlreadyExistException;
import com.cg.onlinepizza.exceptions.CustomerIdNotFoundException;


@Component
public class ICustomerServiceImpl implements ICustomerService{
    @Autowired
    private ICustomerRepository iCustomerRepository;
  
     
    /*Update customers by ID*/
    public CustomerDto updateCustomer(Principal currentCustomer, CustomerDto customer){
        String currUsername= currentCustomer.getName();
    	Optional<Customer> optional = iCustomerRepository.findById(iCustomerRepository.findByUsername(currUsername).get().getId());
        
        	Customer updatedCustomer = dtoToEntity(customer);
        	updatedCustomer.setId(optional.get().getId());
        	iCustomerRepository.save(updatedCustomer);
        	return customer;
    }
    
    /*Delete customer by ID*/
    @Override
    public CustomerDto deleteCustomer(int customerId) throws CustomerIdNotFoundException {
        // TODO Auto-generated method stub
        Optional<Customer> optional = iCustomerRepository.findById(customerId);
        if(optional.isPresent()) {
            iCustomerRepository.deleteById(customerId);
            return entityToDto(optional.get());
        }else {
            throw new CustomerIdNotFoundException();
        }
    }

    /*View all customers*/
    @Override
    public List<CustomerDto> viewCustomers() {
    	List<Customer> customerList = new ArrayList<>();
		
		Iterable<Customer> list = iCustomerRepository.findAll();
		list.forEach(p -> customerList.add(p));
		
		List<CustomerDto> customerDtoList = new ArrayList<>();
		for(Customer customer:customerList) {
			customerDtoList.add(entityToDto(customer));
		}
		return customerDtoList;
    }

    /*View customer by ID*/
    @Override
    public CustomerDto viewCustomer(int customerId) throws CustomerIdNotFoundException {
       
    	Optional<Customer> optional = iCustomerRepository.findById(customerId);
        if(optional.isPresent()) {
        	return entityToDto(optional.get());
        }else {
            throw new CustomerIdNotFoundException();
        }
    }
    
    /*View customer own details*/
   	@Override
   	public CustomerDto aboutCustomer(Principal currentCustomer) {
   		String currUsername= currentCustomer.getName();
    	Optional<Customer> optional = iCustomerRepository.findByUsername(currUsername);
   		return entityToDto(optional.get());
   	}
    
   	
    /*CustomerDto to Customer Entity Class Conversion*/
    public Customer dtoToEntity(CustomerDto customer) {
    	Customer c = new ModelMapper().map(customer,Customer.class);
    	
        return c;
    }
    
    /*Customer Entity to CustomerDto Class Conversion*/
    public CustomerDto entityToDto(Customer customer) {
    	CustomerDto c = new ModelMapper().map(customer,CustomerDto.class);
    
        return c;
    }
    
   
   
} 

