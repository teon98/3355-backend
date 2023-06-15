package com.samsam.repository;

import org.springframework.data.repository.CrudRepository;

import com.samsam.vo.AlarmVO;

public interface AlarmRepository extends CrudRepository<AlarmVO, Integer> {

}
