package com.cg.onlinepizza.customer.service;

import java.util.*;
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
        // TODO Auto-generated method stub
        /*
        int custId = iCustomerRepository.usernameToId(customer.getUsername());
        Optional<Customer> optional = iCustomerRepository.findById(custId);
        if(optional.isPresent()) {
            throw new CustomerAlreadyExistException();
        }
        iCustomerRepository.save(dtoToEntity(customer));
        return customer;
        */
        return null;
    }
    
    // for both admin and user
    @Override
    public CustomerDto updateCustomer(int customerId, CustomerDto customer) throws CustomerIdNotFoundException {
        Optional<Customer> optional = iCustomerRepository.findById(customerId);
        if(optional.isPresent()) {
            Customer customerEntity = dtoToEntity(customer);
            customerEntity.setId(optional.get().getId());
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CustomerDto viewCustomer(int customerId) throws CustomerIdNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }
    /*PizzaDto to Pizza Entity Class Conversion*/
    public Customer dtoToEntity(CustomerDto customer) {
        Customer c = new Customer();
        c.setCustomerName(customer.getCustomerName());
        c.setCustomerEmail(customer.getCustomerEmail());
        c.setCustomerMobile(customer.getCustomerMobile());
        c.setCustomerAddress(customer.getCustomerAddress());
        return c;
    }
    
    /*Pizza Entity to PizzaDto Class Conversion*/
    public CustomerDto entityToDto(Customer customer) {
        CustomerDto c = new CustomerDto();
        c.setCustomerName(customer.getCustomerName());
        c.setCustomerEmail(customer.getCustomerEmail());
        c.setCustomerMobile(customer.getCustomerMobile());
        c.setCustomerAddress(customer.getCustomerAddress());
        return c;
    }
} 

