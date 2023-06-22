package com.samsam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.samsam.vo.CardVO;
import com.samsam.vo.WithdrawVO;

public interface WithdrawRepository extends CrudRepository<WithdrawVO, Integer> {

	public List<WithdrawVO> findByCardOrderByWithdrawDateDesc(CardVO card);
	
	@Query(value = "select * from withdraws "
			+ "where TO_CHAR(withdraw_date, 'YY/MM/DD HH24:MI:SS') = ?1 "
			+ "and card_card_seq = ?2", nativeQuery = true)
	public WithdrawVO findByDate(String date, Integer cardSeq);
}
