package com.cg.onlinepizza.coupan.service;

import java.util.List;

import com.cg.onlinepizza.coupan.dto.Coupan;
import com.cg.onlinepizza.exceptions.CoupanIdNotFoundException;
import com.cg.onlinepizza.exceptions.InvalidCoupanOperationException;


public interface ICoupanService {

	Coupan addCoupans(Coupan coupan);

	Coupan editCoupans(Coupan coupan)throws InvalidCoupanOperationException;

	Coupan deleteCoupans(int coupanId)throws CoupanIdNotFoundException;

	List<Coupan> viewCoupans();
}
