package com.samsam.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.samsam.vo.UserVO;
import com.samsam.vo.WorkVO;

//오운완 레포
public interface WorkRepository extends CrudRepository<WorkVO, Integer>{
	public List<WorkVO> findByUser(UserVO user);
}
