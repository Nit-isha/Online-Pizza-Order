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
    
    @Override
    public CustomerDto addCustomer(CustomerDto customer) throws CustomerAlreadyExistException {

    	Optional<Customer> optional = iCustomerRepository.findByUsername(customer.getUsername());
        //Optional<Customer> optional = iCustomerRepository.findById(custId);
        if(optional.isPresent()) {
            throw new CustomerAlreadyExistException();
        }
        iCustomerRepository.save(dtoToEntity(customer));
        return customer;
       // return null;
    }
    
    // for both admin and user
    @Override
    public CustomerDto updateCustomer(int customerId, CustomerDto customer) throws CustomerIdNotFoundException {
        Optional<Customer> optional = iCustomerRepository.findById(customerId);
        if(optional.isPresent()) {
        	Customer customerEntity = optional.get();
           if(!customer.getCustomerName().isEmpty())
        	   customerEntity.setCustomerName(customer.getCustomerName());
           
           if(!customer.getCustomerEmail().isEmpty())
        	   customerEntity.setCustomerEmail(customer.getCustomerEmail());
           
           if(!customer.getCustomerMobile().isEmpty())
        	   customerEntity.setCustomerMobile(customer.getCustomerMobile());
           
           if(!customer.getCustomerAddress().isEmpty())
        	   customerEntity.setCustomerAddress(customer.getCustomerAddress());
           
            iCustomerRepository.save(customerEntity);
            return customer;
        }else {
            throw new CustomerIdNotFoundException();
        }
    }
    // for both admin and user
    @Override
    public CustomerDto deleteCustomer(int customerId) throws CustomerIdNotFoundException {
        // TODO Auto-generated method stub
        Optional<Customer> optional = iCustomerRepository.findById(customerId);
        if(optional.isPresent()) {
            iCustomerRepository.deleteById(customerId);;
            return entityToDto(optional.get());
        }else {
            throw new CustomerIdNotFoundException();
        }
    }

    @Override
    public List<CustomerDto> viewCustomers() {
    	List<Customer> customerList = new ArrayList<>();
		
		Iterable<Customer> list =  iCustomerRepository.findAll();
		list.forEach(p -> customerList.add(p));
		
		List<CustomerDto> customerDtoList = new ArrayList<>();
		for(Customer customer:customerList) {
			customerDtoList.add(entityToDto(customer));
		}
		return customerDtoList;
    }

    @Override
    public CustomerDto viewCustomer(int customerId) throws CustomerIdNotFoundException {
        // TODO Auto-generated method stub
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
    	/*
        Customer c = new Customer();
        c.setCustomerName(customer.getCustomerName());
        c.setCustomerEmail(customer.getCustomerEmail());
        c.setCustomerMobile(customer.getCustomerMobile());
        c.setCustomerAddress(customer.getCustomerAddress());
        */
        return c;
    }
    
    /*Pizza Entity to PizzaDto Class Conversion*/
    public CustomerDto entityToDto(Customer customer) {
    	CustomerDto c = new ModelMapper().map(customer,CustomerDto.class);
    	/*
        CustomerDto c = new CustomerDto();
        c.setCustomerName(customer.getCustomerName());
        c.setCustomerEmail(customer.getCustomerEmail());
        c.setCustomerMobile(customer.getCustomerMobile());
        c.setCustomerAddress(customer.getCustomerAddress());
        */
        return c;
    }
   
} 

