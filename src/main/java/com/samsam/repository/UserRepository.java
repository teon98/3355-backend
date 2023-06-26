package com.samsam.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.samsam.vo.UserVO;

@Repository
public interface UserRepository extends CrudRepository<UserVO, Integer> {
	
	public UserVO findByUserEmail(String userEmail);
	public UserVO findByUserNickname(String userNickname);
	public UserVO findByUserEmailAndUserPass(String userEmail,String userPass);
	public UserVO findByUserEmailAndUserNickname(String userEmail,String userNickname);
	public UserVO findByUserBirthIsNull();
	public UserVO findByUserPass(String userPass);
}
