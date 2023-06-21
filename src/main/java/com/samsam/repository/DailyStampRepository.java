package com.samsam.repository;

import org.springframework.data.repository.CrudRepository;

import com.samsam.vo.DailyStampVO;

//출석체크 레포
public interface DailyStampRepository extends CrudRepository<DailyStampVO, Integer> {

}
