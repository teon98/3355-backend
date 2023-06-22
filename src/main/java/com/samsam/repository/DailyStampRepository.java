package com.samsam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.samsam.vo.DailyStampVO;
import com.samsam.vo.UserVO;
import com.samsam.vo.WorkVO;

//출석체크 레포
public interface DailyStampRepository extends CrudRepository<DailyStampVO, Integer> {
	@Query(value = "select count(*) from dailystamps where user_no = ?1", nativeQuery = true)
	public int findByUser(int userNo);//몇번출석햇나
	
	@Query(value = "select * from dailystamps where daily_date like %?1% and user_no = ?2", nativeQuery = true)
	public DailyStampVO findByDailyDateContaining(String Date,int userNo);
}
