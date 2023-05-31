package com.samsam.repository;

import org.springframework.data.repository.CrudRepository;

import com.samsam.vo.DepositVO;

public interface DepositRepository extends CrudRepository<DepositVO, Integer> {

}
