package com.samsam.repository;

import org.springframework.data.repository.CrudRepository;

import com.samsam.vo.FollowId;
import com.samsam.vo.FollowVO;

//복합키 레포
public interface FollowRepository extends CrudRepository<FollowVO, FollowId> {

}
