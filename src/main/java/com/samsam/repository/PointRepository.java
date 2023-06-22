package com.samsam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.samsam.vo.CardVO;
import com.samsam.vo.PointVO;

public interface PointRepository extends CrudRepository<PointVO, Integer> {

	public PointVO findByCard(CardVO card);
	public List<PointVO> findByCardOrderByPointDateDesc(CardVO card);
	public PointVO findByWithdrawNo(Integer withdrawNo);
	
	@Query(value = "select * from points "
			+ "where TO_CHAR(point_date, 'YY/MM/DD HH24:MI') = ?1 "
			+ "and card_card_seq = ?2 and point_save < 0", nativeQuery = true)
	public PointVO findByDate(String date, Integer cardSeq);
}
