package com.cg.onlinepizza.coupon.service;

import java.util.ArrayList;
import java.util.List;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.cg.onlinepizza.coupon.dao.ICouponRepository;
import com.cg.onlinepizza.coupon.dto.CouponDto;
import com.cg.onlinepizza.entity.Coupon;
import com.cg.onlinepizza.entity.Pizza;
import com.cg.onlinepizza.exceptions.CouponAlreadyExistException;
import com.cg.onlinepizza.exceptions.CouponIdNotFoundException;
import com.cg.onlinepizza.exceptions.InvalidCouponOperationException;
import com.cg.onlinepizza.exceptions.PizzaIdNotFoundException;
import com.cg.onlinepizza.pizza.dto.PizzaDto;

@Component
public class ICouponServiceImpl implements ICouponService {
	@Autowired
	private ICouponRepository iCouponRepository;
	@Override
	public CouponDto addCoupons(CouponDto coupon) throws CouponAlreadyExistException {
		Optional<Coupon> optional = iCouponRepository.findById(coupon.getCouponId());
		if(optional.isPresent()){
			throw new CouponAlreadyExistException();
		}
		iCouponRepository.save(dtoToEntity(coupon));
		return coupon;
	}
	

	@Override
	public CouponDto editCoupons(int couponId, CouponDto coupon) throws  CouponIdNotFoundException {
		Optional<Coupon> optional = iCouponRepository.findById(couponId);
		if(optional.isPresent()) {
			Coupon couponEntity = dtoToEntity(coupon);
			couponEntity.setCouponId(optional.get().getCouponId());
			iCouponRepository.save(couponEntity);
			return coupon;
		}else {
			throw new CouponIdNotFoundException();
		}
	}

	
	@Override
	public CouponDto deleteCoupons(int couponId) throws CouponIdNotFoundException {
		Optional<Coupon> optional = iCouponRepository.findById(couponId);
		if(optional.isPresent()) {
			iCouponRepository.deleteById(couponId);
			return  entityToDto(optional.get());
		}else {
			throw new CouponIdNotFoundException();
		}
	}

	
	
	@Override
	public List<CouponDto> viewCoupons() {
		List<Coupon> couponList=new ArrayList<>();
		Iterable<Coupon> list =  iCouponRepository.findAll();
		list.forEach(p -> couponList.add(p));
		
		List<CouponDto> couponDtoList = new ArrayList<>();
		for(Coupon coupon: couponList) {
			couponDtoList.add(entityToDto(coupon));
		}
		return couponDtoList;
		
		
	}
	/*Find Coupon By ID Method*/
	@Override
	public CouponDto viewCouponId(int couponId) throws CouponIdNotFoundException {
		Optional<Coupon> optional = iCouponRepository.findById(couponId);
		if(optional.isPresent()) {
			return entityToDto(optional.get());
		}else {
			throw new CouponIdNotFoundException();
		}
	}
	
	
	//Coupon Entity to CouponDto Class Conversion//
	public CouponDto entityToDto(Coupon coupon) {
		CouponDto c = new CouponDto();
		c.setCouponId(coupon.getCouponId());
		c.setCouponName(coupon.getCouponName());
		c.setCouponType(coupon.getCouponType());
		c.setCouponDescription(coupon.getCouponDescription());
		return c;
	}
	/*CoupanDto to Coupon Entity Class Conversion*/
	public Coupon dtoToEntity(CouponDto coupon) {
		Coupon c = new Coupon();
		c.setCouponId(coupon.getCouponId());
		c.setCouponName(coupon.getCouponName());
		c.setCouponType(coupon.getCouponType());
		c.setCouponDescription(coupon.getCouponDescription());
		return c;
	}


	
	


}
