package com.samsam.repository;

import org.springframework.data.repository.CrudRepository;

import com.samsam.vo.CardVO;

public interface CardRepository extends CrudRepository<CardVO, Integer>{

}
