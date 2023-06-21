package com.samsam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.samsam.vo.UserVO;
import com.samsam.vo.WorkVO;

//오운완 레포
public interface WorkRepository extends CrudRepository<WorkVO, Integer> {
	public List<WorkVO> findByUser(UserVO user);

//	public List<WorkVO> findByWorkDateContaining(String formattedDate);
	// JPQL(JPL Query Language)...*지원 안됨
	@Query(value = "select * from works w where w.work_date like %?1%", nativeQuery = true)
	public List<WorkVO> findByWorkDateContaining(String workDate);
}
