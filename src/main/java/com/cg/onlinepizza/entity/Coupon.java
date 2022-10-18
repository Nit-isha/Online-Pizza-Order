package com.cg.onlinepizza.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public class Coupon {
	@Id
	private int couponId;
	private String couponName;
	private String couponType;
	private String couponDescription;	
}
