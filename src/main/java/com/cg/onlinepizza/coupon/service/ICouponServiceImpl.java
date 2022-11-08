package com.cg.onlinepizza.coupon.service;

import java.util.ArrayList;
import java.util.List;


import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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
		
		List<CouponDto> couponsList= viewCoupons();
		Optional<CouponDto> couponMatchDto =  couponsList.stream().filter(c->c.getCouponName().equals(coupon.getCouponName())).findAny();
		
		if(couponMatchDto.isPresent()){
			throw new CouponAlreadyExistException();
		}
		
		
		iCouponRepository.save(dtoToEntity(coupon));
		return coupon;
	}
	

	@Override
	public CouponDto editCoupons(int couponId, CouponDto coupon) throws CouponIdNotFoundException, CouponAlreadyExistException {
		Optional<Coupon> optional = iCouponRepository.findById(couponId);
		
		List<CouponDto> couponsList= viewCoupons();
		Optional<CouponDto> couponNameMatchDto =  couponsList.stream().filter(c->c.getCouponName().equals(coupon.getCouponName())).findAny();
		if(optional.isPresent()) {
			if(couponNameMatchDto.isPresent()){
				throw new CouponAlreadyExistException();
			}
			Coupon couponEntity = dtoToEntity(coupon);
			couponEntity.setCouponId(optional.get().getCouponId());
			iCouponRepository.save(couponEntity);
			return entityToDto(couponEntity);
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
	public CouponDto viewCouponById(int couponId) throws CouponIdNotFoundException {
		Optional<Coupon> optional = iCouponRepository.findById(couponId);
		if(optional.isPresent()) {
			return entityToDto(optional.get());
		}else {
			throw new CouponIdNotFoundException();
		}
	}
	
	
	//Coupon Entity to CouponDto Class Conversion//
	public CouponDto entityToDto(Coupon coupon) {
		CouponDto c= new ModelMapper().map(coupon,CouponDto.class);
		return c;
	}
	/*CoupanDto to Coupon Entity Class Conversion*/
	public Coupon dtoToEntity(CouponDto coupon) {
		Coupon c= new ModelMapper().map(coupon,Coupon.class);
		return c;
	}


	
	


}
