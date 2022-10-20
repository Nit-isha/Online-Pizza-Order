package com.cg.onlinepizza.coupon.dto;

import javax.validation.constraints.Pattern;

public class CouponDto {
	private int couponId;
	@Pattern(regexp="^(FLAT)+[1-9]{1}+[0-9]{1,2}+(MN)+[1-9]{1}+[0-9]{1-2}$", message="length must be 10")
	private String couponName;
	private String couponType;
	private String couponDescription;
	
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
	

	
	

}
