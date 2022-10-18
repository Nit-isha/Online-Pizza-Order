package com.cg.onlinepizza.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public class PizzaOrder {
	@Id
	private int bookingOrderId;
	private LocalDate orderDate;
	private String transactionMode;
	private int quantity;
	private String size;
	private double totalCost;
	private String orderType;
	@ManyToOne
	@JoinColumn(name = "custId")
	private Customer customer;
	
	@OneToMany
	@JoinColumn(name = "booking_id")
	private List<Pizza> pizza;
	
	@OneToOne
	@JoinColumn(name = "coupon_id")
	private Coupon coupon;
	
}

