package com.samsam.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.samsam.vo.PostVO;
import com.samsam.vo.UserVO;

public interface PostRepository extends CrudRepository<PostVO, Integer> {
	
	public List<PostVO> findByUser(UserVO user);
}
