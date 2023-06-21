package com.samsam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.samsam.vo.AlarmVO;

public interface AlarmRepository extends CrudRepository<AlarmVO, Integer> {
	
	@Query(value = "select count(*) from alarms where user_no = ?1 and alarm_status = ?2 ORDER BY alarm_date DESC", nativeQuery = true)
	public int findUnreadCnt(Integer user, Integer status);
	
	@Query(value = "select * from alarms where user_no = ?1 and alarm_status = ?2 ORDER BY alarm_date DESC", nativeQuery = true)
	public List<AlarmVO> findUnreadAlarms(Integer user, Integer status);
	
	@Query(value = "SELECT * FROM (SELECT * FROM alarms WHERE user_no = ?1 and alarm_status = ?2 ORDER BY alarm_date DESC) WHERE ROWNUM <= 5", nativeQuery = true)
	public List<AlarmVO> findReadAlarms(Integer user, Integer status);
}
