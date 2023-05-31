package com.samsam.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.samsam.vo.UserVO;

@Repository
public interface UserRepository extends CrudRepository<UserVO, Integer>{

}
