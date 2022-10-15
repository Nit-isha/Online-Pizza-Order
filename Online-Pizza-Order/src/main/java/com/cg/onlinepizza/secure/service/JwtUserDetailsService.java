package com.cg.onlinepizza.secure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.cg.onlinepizza.customer.dto.CustomerDto;
import com.cg.onlinepizza.entity.Customer;
import com.cg.onlinepizza.secure.model.User;
import com.cg.onlinepizza.secure.model.UserDto;
import com.cg.onlinepizza.secure.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<SimpleGrantedAuthority> roles = null;
		User user = userDao.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		roles = Arrays.asList(new SimpleGrantedAuthority(user.getRole()));
		System.out.println("Roles : "+roles);
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				roles);
		//return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
			//	new ArrayList<>());
	}
	

	public Customer save(CustomerDto customerDto) {
		Customer newCust = new Customer();
		newCust.setCustomerName(customerDto.getCustomerName());
		newCust.setCustomerMobile(customerDto.getCustomerMobile());
		newCust.setCustomerEmail(customerDto.getCustomerEmail());
		newCust.setCustomerAddress(customerDto.getCustomerAddress());
		newCust.setUsername(customerDto.getCustomerName());
		newCust.setPassword(customerDto.getPassword());
		newCust.setRole("user");

		return userDao.save(newCust);
	}
}