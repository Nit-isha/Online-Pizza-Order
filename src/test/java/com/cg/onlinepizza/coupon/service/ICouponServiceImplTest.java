package com.cg.onlinepizza.coupon.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.cg.onlinepizza.coupon.dao.ICouponRepository;
import com.cg.onlinepizza.coupon.dto.CouponDto;
import com.cg.onlinepizza.entity.Coupon;
import com.cg.onlinepizza.entity.Pizza;
import com.cg.onlinepizza.exceptions.CouponAlreadyExistException;
import com.cg.onlinepizza.exceptions.CouponIdNotFoundException;
import com.cg.onlinepizza.pizza.dto.PizzaDto;

@RunWith(SpringRunner.class)
@SpringBootTest
class ICouponServiceImplTest {
	@MockBean
	private ICouponRepository iCouponRepository;
	@MockBean
	private Coupon coupon;
	
	@Autowired 
	private ICouponService iCouponService;
	
	private static  List<Coupon>couponList = new ArrayList<>();
	
	@BeforeAll
    static void setUp() {
        Coupon obj1 =new Coupon();
        obj1.setCouponId(1000);
        obj1.setCouponName("Domino4");
        obj1.setCouponType("30% OFF");
        obj1.setCouponDescription("Get flat 30% OFF up to Rs.60 on your orders during weekend. Minimum purchase value – Rs.200.");
        Coupon obj2 = new Coupon();
        obj2.setCouponId(1001);
        obj2.setCouponName("Domino5");
        obj2.setCouponType("40% OFF");
        obj2.setCouponDescription("Get flat 40% OFF up to Rs.40 on your orders during weekend. Minimum purchase value – Rs.300.");
        couponList.add(obj1);
        couponList.add(obj2);
    }
	
	
	
	
	@Test
	void testAddCoupons() throws CouponAlreadyExistException {
		when(iCouponRepository.save(Mockito.any(Coupon.class))).thenReturn(coupon);
   		CouponDto p2 = iCouponService.addCoupons(entityToDto(coupon));
   		assertEquals(coupon.getCouponId(), p2.getCouponId());
	}

	@Test
	void testEditCoupons() throws CouponIdNotFoundException, CouponAlreadyExistException {
		when(iCouponRepository.findById(coupon.getCouponId())).thenReturn(Optional.of(coupon));
		//when(iCouponRepository.save(Mockito.any(Coupon.class))).thenReturn(coupon);
   		CouponDto p2 = iCouponService.editCoupons(coupon.getCouponId(),entityToDto(coupon));
   		assertEquals(coupon.getCouponId(), p2.getCouponId());
	}

	@Test
	void testDeleteCoupons() throws CouponIdNotFoundException {
		when(iCouponRepository.findById(coupon.getCouponId())).thenReturn(Optional.of(coupon));
   		CouponDto p2 = iCouponService.deleteCoupons(coupon.getCouponId());
   		assertEquals(coupon.getCouponId(), p2.getCouponId());
	}

	@Test
	void testViewCoupons() {
		when(iCouponRepository.findAll()).thenReturn(couponList);
   		List<CouponDto> p2 = iCouponService.viewCoupons();
   		assertEquals(couponList.size(), p2.size());
	}

	@Test
	void testViewCouponId() throws CouponIdNotFoundException {
		when(iCouponRepository.findById(coupon.getCouponId())).thenReturn(Optional.of(coupon));
   		CouponDto p2 = iCouponService.viewCouponId(coupon.getCouponId());
   		assertEquals(coupon.getCouponId(), p2.getCouponId());
	}

	
	public Coupon dtoToEntity(CouponDto coupon) {
    	Coupon c= new ModelMapper().map(coupon,Coupon.class);
    	
        return c;
    }
    public CouponDto entityToDto(Coupon coupon ) {
    	CouponDto c= new ModelMapper().map(coupon ,CouponDto.class);
    	
        return c;
    }
}
