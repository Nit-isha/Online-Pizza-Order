package com.cg.onlinepizza.order.dto;

import java.time.LocalDate;

import com.cg.onlinepizza.coupan.dto.Coupan;
import com.cg.onlinepizza.pizza.dto.Pizza;

public class PizzaOrder {

	private int bookingOrderId;
	private LocalDate dateOfOrder;
	private String transactionMode;
	private int quantity;
	private String size;//medium,regular,large
	private double totalCost;
	private Pizza pizza;
	private Order order;
	private Coupan coupan;
	public int getBookingOrderId() {
		return bookingOrderId;
	}
	public void setBookingOrderId(int bookingOrderId) {
		this.bookingOrderId = bookingOrderId;
	}
	public LocalDate getDateOfOrder() {
		return dateOfOrder;
	}
	public void setDateOfOrder(LocalDate dateOfOrder) {
		this.dateOfOrder = dateOfOrder;
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
	public Pizza getPizza() {
		return pizza;
	}
	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Coupan getCoupan() {
		return coupan;
	}
	public void setCoupan(Coupan coupan) {
		this.coupan = coupan;
	} 
	
}
