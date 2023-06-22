package com.samsam.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.samsam.vo.CardcustomVO;

public interface CardcustomRepository extends CrudRepository<CardcustomVO, Integer> {
	
	@Query(value = "SELECT * FROM (SELECT * FROM cardcustoms WHERE user_no = ?1 ORDER BY custom_no DESC) WHERE ROWNUM < 2", nativeQuery = true)
	public CardcustomVO findByUserNo(int userNo);
}
