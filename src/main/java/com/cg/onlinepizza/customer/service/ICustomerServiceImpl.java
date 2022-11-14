package com.cg.onlinepizza.customer.service;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.cg.onlinepizza.customer.dao.ICustomerRepository;
import com.cg.onlinepizza.customer.dto.CustomerDto;
import com.cg.onlinepizza.entity.Customer;
import com.cg.onlinepizza.exceptions.CustomerAlreadyExistException;
import com.cg.onlinepizza.exceptions.CustomerIdNotFoundException;
import com.cg.onlinepizza.exceptions.CustomerNotFoundException;
import com.cg.onlinepizza.exceptions.UsernameAlreadyExistException;


@Component
public class ICustomerServiceImpl implements ICustomerService{

	@Autowired
	private ICustomerRepository iCustomerRepository;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;

	/*Update customers*/
	public CustomerDto updateCustomer(String currentCustomer, CustomerDto customer) throws  CustomerAlreadyExistException, CustomerIdNotFoundException{
		
		Optional<Customer> optional = iCustomerRepository.findById(iCustomerRepository.findByUsername(currentCustomer).get().getId());
		if(!optional.isPresent()) {
			throw new CustomerIdNotFoundException();
		}
		List<CustomerDto> customerDatabase = viewCustomers();
		List<String> usernameList = customerDatabase.stream().map(CustomerDto::getUsername).collect(Collectors.toList());
		usernameList.remove(currentCustomer);
		
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
	
	public CustomerDto updateCustomerDetails(String currentCustomer, CustomerDto customer){
        
        Optional<Customer> customerOld = iCustomerRepository.findByUsername(currentCustomer);
        
        Customer updatedCustomer = dtoToEntity(customer);
        updatedCustomer.setId(customerOld.get().getId());
        updatedCustomer.setUsername(currentCustomer);
//        updatedCustomer.setPassword(bcryptEncoder.encode(customerOld.get().getPassword()));
        updatedCustomer.setPassword(customerOld.get().getPassword());
        updatedCustomer.setRole("user");
        iCustomerRepository.save(updatedCustomer);
        return customer;
     }
	
	public CustomerDto updateCustomerPassword(String currentCustomer, CustomerDto customer){
	    
	    Optional<Customer> cust = iCustomerRepository.findByUsername(currentCustomer);
        cust.get().setPassword(bcryptEncoder.encode(customer.getPassword()));
        System.out.println(customer.getPassword());
	    iCustomerRepository.save(cust.get());
	    return entityToDto(cust.get());
	}
	
	public CustomerDto updateCustomerUsername(String currentCustomer, CustomerDto customer) throws UsernameAlreadyExistException{
	    
	    Optional<Customer> cust = iCustomerRepository.findByUsername(currentCustomer);
	    
	    List<CustomerDto> customerDatabase = viewCustomers();
        List<String> usernameList = customerDatabase.stream().map(CustomerDto::getUsername).collect(Collectors.toList());
        if(usernameList.contains(customer.getUsername())) {
            throw new UsernameAlreadyExistException();
        }
        else {
            System.out.println(customer.getUsername());
            cust.get().setUsername(customer.getUsername());
        }
	    iCustomerRepository.save(cust.get());
	    return entityToDto(cust.get());
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
   	public CustomerDto aboutCustomer(String currentCustomer) {
    	Optional<Customer> optional = iCustomerRepository.findByUsername(currentCustomer);
   		return entityToDto(optional.get());
   	}
    
   	// view customer by name
   	public CustomerDto getCustomerByName(String name) throws CustomerNotFoundException {
   		Optional<Customer> optional =  iCustomerRepository.findCustomerByName(name);
   		if(optional.isPresent()) {
        	return entityToDto(optional.get());
        }else {
            throw new CustomerNotFoundException();
        }
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

