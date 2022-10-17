package com.cg.onlinepizza.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import com.cg.onlinepizza.secure.model.User;

@Entity
public class Customer extends User{
	@NotBlank
	private String customerName;
	//@Pattern(regexp="^[6-9][0-9]{9}",message="length must be 10")
	private long customerMobile;
	@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
	private String customerEmail;
	@NotBlank
	private String customerAddress;
	@OneToMany(mappedBy = "customer")
	private List<PizzaOrder> pizzaOrder;
//	public Customer(String userName, String password) {
//		super(userName,password);
//	}
//	public Customer(String customerName, long customerMobile,
//			String customerEmail, String customerAddress, String userName, String password) {
//		super(userName,password);
//		this.customerName = customerName;
//		this.customerMobile = customerMobile;
//		this.customerEmail = customerEmail;
//		this.customerAddress = customerAddress;
//	}

	public List<PizzaOrder> getPizzaOrder() {
		return pizzaOrder;
	}

	public void setPizzaOrder(List<PizzaOrder> pizzaOrder) {
		this.pizzaOrder = pizzaOrder;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public long getCustomerMobile() {
		return customerMobile;
	}
	public void setCustomerMobile(long customerMobile) {
		this.customerMobile = customerMobile;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
}
