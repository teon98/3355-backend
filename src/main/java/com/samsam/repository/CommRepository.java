package com.samsam.repository;

import org.springframework.data.repository.CrudRepository;

import com.samsam.vo.CommentVO;

public interface CommRepository 
	extends CrudRepository<CommentVO, Integer>{

}
