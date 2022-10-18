package com.cg.onlinepizza.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import com.cg.onlinepizza.secure.model.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public class Customer extends User{
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
}
