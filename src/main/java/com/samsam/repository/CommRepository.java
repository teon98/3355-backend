package com.samsam.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.samsam.vo.CommentVO;
import com.samsam.vo.PostVO;

public interface CommRepository extends CrudRepository<CommentVO, Integer> {
	
	public List<CommentVO> findByPostOrderByCommNoDesc(PostVO post);
}
