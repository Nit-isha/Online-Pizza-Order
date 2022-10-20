package com.cg.onlinepizza.customer.service;

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
    public CustomerDto updateCustomer(int customerId, CustomerDto customer) throws CustomerIdNotFoundException {
        Optional<Customer> optional = iCustomerRepository.findById(customerId);
        if(optional.isPresent()) {
        	Customer updatedCustomer = dtoToEntity(customer);
        	updatedCustomer.setId(optional.get().getId());
        	iCustomerRepository.save(updatedCustomer);
        	return customer;
        }else {
        	throw new CustomerIdNotFoundException();
        }
        
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
    
    /*PizzaDto to Pizza Entity Class Conversion*/
    public Customer dtoToEntity(CustomerDto customer) {
    	Customer c = new ModelMapper().map(customer,Customer.class);
    	
        return c;
    }
    
    /*Pizza Entity to PizzaDto Class Conversion*/
    public CustomerDto entityToDto(Customer customer) {
    	CustomerDto c = new ModelMapper().map(customer,CustomerDto.class);
  
        return c;
    }
   
} 

