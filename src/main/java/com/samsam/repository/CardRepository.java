package com.samsam.repository;

import org.springframework.data.repository.CrudRepository;

import com.samsam.vo.CardVO;
import com.samsam.vo.UserVO;

public interface CardRepository extends CrudRepository<CardVO, Integer> {

	public CardVO findByUser(UserVO user);
}
