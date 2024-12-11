package com.cg.onlinepizza.coupon.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.onlinepizza.coupon.dto.CouponDto;
import com.cg.onlinepizza.entity.Coupon;
import com.cg.onlinepizza.exceptions.CouponIdNotFoundException;
import com.cg.onlinepizza.exceptions.InvalidCouponOperationException;
@Repository
public interface ICouponRepository extends CrudRepository<Coupon,Integer> {
	@Query("select c from Coupon c where c.couponName  =:cname")
	Coupon getCouponByName(@Param("cname") String couponName);
}
