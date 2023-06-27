package com.samsam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.samsam.vo.FollowId;
import com.samsam.vo.FollowVO;
import com.samsam.vo.UserVO;

//복합키 레포
public interface FollowRepository extends CrudRepository<FollowVO, FollowId>{
	
	@Query(value = "select follow_end from follows where follow_start = ?1", nativeQuery = true)
	List<Integer> findByFollowStart(Integer uid);
	
	@Query(value = "select count(*) from follows where follow_start =?1 and follow_end=?2", nativeQuery = true)
	int findByEachOther(Integer uid1, Integer uid2);
}
