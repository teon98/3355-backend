package com.samsam.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.samsam.vo.CardVO;
import com.samsam.vo.DepositVO;

public interface DepositRepository extends CrudRepository<DepositVO, Integer> {

	public List<DepositVO> findByCardOrderByDepositDateDesc(CardVO card);
}
