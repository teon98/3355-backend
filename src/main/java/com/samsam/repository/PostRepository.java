package com.samsam.repository;

import org.springframework.data.repository.CrudRepository;

import com.samsam.vo.PostVO;

public interface PostRepository 
	extends CrudRepository<PostVO, Integer>{

}
