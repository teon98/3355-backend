package com.samsam.repository;

import org.springframework.data.repository.CrudRepository;

import com.samsam.vo.WithdrawVO;

public interface WithdrawRepository extends CrudRepository<WithdrawVO, Integer>{
	
}
