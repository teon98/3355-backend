package com.samsam.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.samsam.vo.PostVO;
import com.samsam.vo.UserVO;

public interface PostRepository extends CrudRepository<PostVO, Integer> {
	
	public List<PostVO> findByUser(UserVO user);
	public List<PostVO> findByUserInOrderByPostDateDesc(List<UserVO> users);
	// 게시물 불러올시 최신순으로 불러오기 위해 DESC추가
	public List<PostVO> findByUserOrderByPostDateDesc(UserVO user);
	// 모든 게시물 불러와서 내림차순이요~
	public List<PostVO> findAllByOrderByPostDateDesc();

}
