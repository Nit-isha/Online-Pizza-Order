package com.cg.onlinepizza.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class PizzaOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookingOrderId;
	private LocalDateTime orderDate;
	private String transactionMode;
	private int quantity;
	private String size;
	private double totalCost;
	private String orderType;
	@ManyToOne
	@JoinColumn(name = "custId")
	private Customer customer;
	
	@ManyToMany
	@JoinColumn(name = "booking_id")
	private List<Pizza> pizza;
	
	@OneToOne
	@JoinColumn(name = "coupon_id")
	private Coupon coupon;
	
	public PizzaOrder() {
		
	}
	

    public int getBookingOrderId() {
        return bookingOrderId;
    }

    public void setBookingOrderId(int bookingOrderId) {
        this.bookingOrderId = bookingOrderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getTransactionMode() {
        return transactionMode;
    }

    public void setTransactionMode(String transactionMode) {
        this.transactionMode = transactionMode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Pizza> getPizza() {
        return pizza;
    }
    

    public void setPizza(List<Pizza> pizza) {
		this.pizza = pizza;
	}


	public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

}

