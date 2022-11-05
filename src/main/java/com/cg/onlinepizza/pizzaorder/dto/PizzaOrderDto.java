package com.cg.onlinepizza.pizzaorder.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PizzaOrderDto {
	private int bookingOrderId;
	private LocalDateTime orderDate;
	private String transactionMode;
	private int quantity;
	private String size;
	private double totalCost;
	private String couponName;
	private String orderType;
	private List<Integer> pizzaIdList;
	private int custId;
    
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
    public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	public String getOrderType() {
        return orderType;
    }
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
	public List<Integer> getPizzaIdList() {
		return pizzaIdList;
	}
	public void setPizzaIdList(List<Integer> pizzaIdList) {
		this.pizzaIdList = pizzaIdList;
	}
	public int getCustId() { 
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
}
