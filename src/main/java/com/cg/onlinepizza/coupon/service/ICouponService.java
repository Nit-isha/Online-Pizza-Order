package com.cg.onlinepizza.coupon.service;

import java.util.List;


import com.cg.onlinepizza.coupon.dto.CouponDto;
import com.cg.onlinepizza.exceptions.CouponAlreadyExistException;
import com.cg.onlinepizza.exceptions.CouponIdNotFoundException;

import com.cg.onlinepizza.exceptions.CouponTypeNotFoundException;
import com.cg.onlinepizza.exceptions.InvalidCouponOperationException;


public interface ICouponService {

	CouponDto addCoupons(CouponDto coupon) throws CouponAlreadyExistException;

	CouponDto editCoupons(int couponId, CouponDto coupon)throws CouponIdNotFoundException, CouponAlreadyExistException;

	CouponDto deleteCoupons(int couponId)throws CouponIdNotFoundException;

	List<CouponDto> viewCoupons() ;
	
	CouponDto viewCouponByName(String couponName);
	
	CouponDto viewCouponById(int couponId) throws CouponIdNotFoundException;
	
	Double couponValidation(String couponName,double subTotal) throws CouponTypeNotFoundException;
}
