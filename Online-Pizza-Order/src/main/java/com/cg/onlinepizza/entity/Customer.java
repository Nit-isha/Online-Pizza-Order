package com.cg.onlinepizza.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Customer extends User{

	private String customerName;
	private long customerMobile;
	private String customerEmail;
	private String customerAddress;
	@OneToMany(mappedBy = "customer")
	private List<PizzaOrder> pizzaOrder;
	
	
	public Customer(String customerName, long customerMobile,
			String customerEmail, String customerAddress, String userName, String password,String role) {
		super(userName,password,role);
		this.customerName = customerName;
		this.customerMobile = customerMobile;
		this.customerEmail = customerEmail;
		this.customerAddress = customerAddress;
	}

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
