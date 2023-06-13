package com.samsam.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.samsam.vo.CardVO;
import com.samsam.vo.WithdrawVO;

public interface WithdrawRepository extends CrudRepository<WithdrawVO, Integer>{

	
	public List<WithdrawVO> findByCardOrderByWithdrawDateDesc(CardVO card);
}
