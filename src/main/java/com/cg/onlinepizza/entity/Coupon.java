package com.cg.onlinepizza.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Coupon {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int couponId;
	private String couponName;
	private String couponType;
	private int discount;
	private int amount;
	private String couponDescription;
	
    

	public Coupon() {
		// TODO Auto-generated constructor stub
	}

	public Coupon(String couponName, String couponType, int discount, int amount,
			String couponDescription) {
		super();
		this.couponName = couponName;
		this.couponType = couponType;
		this.discount = discount;
		this.amount = amount;
		this.couponDescription = couponDescription;
	}

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

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
