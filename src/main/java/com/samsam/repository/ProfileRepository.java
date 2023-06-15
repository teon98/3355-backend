package com.samsam.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.samsam.vo.ProfileVO;
import com.samsam.vo.UserVO;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileVO, Integer> {
	
	public ProfileVO findByUser(UserVO user);
}
