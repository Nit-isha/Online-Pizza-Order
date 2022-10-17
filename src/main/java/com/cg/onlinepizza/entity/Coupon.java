package com.cg.onlinepizza.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Coupon {
	@Id
	private int couponId;
	private String couponName;
	private String couponType;
	private String couponDescription;
	private int couponPizzaId;
	
	public int getCouponId() {
		return couponId;
	}
	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	public String getCouponType() {
		return couponType;
	}
	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
	public String getCouponDescription() {
		return couponDescription;
	}
	public void setCouponDescription(String couponDescription) {
		this.couponDescription = couponDescription;
	}
	public int getCouponPizzaId() {
		return couponPizzaId;
	}
	public void setCouponPizzaId(int couponPizzaId) {
		this.couponPizzaId = couponPizzaId;
	}
	
}
