package com.samsam.repository;

import org.springframework.data.repository.CrudRepository;

import com.samsam.vo.PointVO;

public interface PointRepository extends CrudRepository<PointVO, Integer> {

}
