package com.samsam.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.samsam.vo.GoodVO;

public interface GoodRepository extends CrudRepository<GoodVO, Integer> {
	@Query(value= "select count(*) from Goods where post_no = ?1", nativeQuery = true)
	int findByGoodsCount(int post_no);
	
}
