package com.samsam.repository;

import org.springframework.data.repository.CrudRepository;

import com.samsam.vo.TagVO;

public interface TagRepository 
	extends CrudRepository<TagVO, Integer>{

}
