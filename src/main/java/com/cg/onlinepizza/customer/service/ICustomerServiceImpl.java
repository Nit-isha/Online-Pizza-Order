package com.cg.onlinepizza.customer.service;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	
	@Autowired
	private PasswordEncoder bcryptEncoder;

	/*Update customers*/
	public CustomerDto updateCustomer(String currentCustomer, CustomerDto customer) throws CustomerIdNotFoundException, CustomerAlreadyExistException{
		
		Optional<Customer> optional = iCustomerRepository.findById(iCustomerRepository.findByUsername(currentCustomer).get().getId());
		
		List<CustomerDto> customerDatabase = viewCustomers();
		List<String> usernameList = customerDatabase.stream().map(CustomerDto::getUsername).collect(Collectors.toList());
		usernameList.remove(currentCustomer);
		if(optional.isPresent()) {
			if(usernameList.contains(currentCustomer)) {
				throw new CustomerAlreadyExistException();
			}
			else {
			Customer updatedCustomer = dtoToEntity(customer);
			updatedCustomer.setId(optional.get().getId());
			updatedCustomer.setPassword(bcryptEncoder.encode(customer.getPassword()));
			updatedCustomer.setRole("user");
			iCustomerRepository.save(updatedCustomer);
			return customer;
			}
		}
		else {
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
	public CustomerDto viewCustomer1(int customerId) throws CustomerIdNotFoundException {

		Optional<Customer> optional = iCustomerRepository.findById(customerId);
		if(optional.isPresent()) {
			return entityToDto(optional.get());
		}else {
			throw new CustomerIdNotFoundException();
		}
	}

	/*View customer own details*/
	public CustomerDto aboutCustomer1(Principal currentCustomer) {
		String currUsername= currentCustomer.getName();
		Optional<Customer> optional = iCustomerRepository.findByUsername(currUsername);
		return entityToDto(optional.get());
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

