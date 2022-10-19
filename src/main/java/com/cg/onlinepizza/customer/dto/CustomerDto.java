package com.cg.onlinepizza.customer.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CustomerDto {
	@NotBlank
	private String customerName;
	/*Mobile Number length must be equal to 10.*/
	@Pattern(regexp="^[6-9][0-9]{9}", message="length must be 10")
	private String customerMobile;
	/*Email Validation using regex*/
	@Email(regexp = "[a-z0-9]+@[a-z0-9.-]+\\.[a-z]{2,3}")
	private String customerEmail;
	@NotBlank
	private String customerAddress;
	private String username;
	private String password;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
