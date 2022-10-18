package com.cg.onlinepizza.pizzaorder.dto;

import java.time.LocalDate;
import java.util.List;

import com.cg.onlinepizza.entity.Coupon;
import com.cg.onlinepizza.entity.Pizza;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)

public class PizzaOrderDto {
	private int bookingOrderId;
	private LocalDate orderDate;
	private String transactionMode;
	private int quantity;
	private String size;
	private double totalCost;
	private int couponId;
	private String orderType;
	private List<Integer> pizzaId;
}
