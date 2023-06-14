package com.samsam.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.samsam.vo.CardVO;
import com.samsam.vo.PointVO;

public interface PointRepository extends CrudRepository<PointVO, Integer> {

	public PointVO findByCard(CardVO card);
	
	public List<PointVO> findByCardOrderByPointDateDesc(CardVO card);
}
