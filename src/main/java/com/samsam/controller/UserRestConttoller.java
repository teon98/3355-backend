package com.samsam.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samsam.vo.UserVO;

@RestController
@RequestMapping("/user")
public class UserRestConttoller {

	
	@PostMapping("/user/insert.sam")
	public UserVO UserRegisterPost() {
		UserVO newuser = UserVO.builder().build();
		 
		
		return newuser;
	}
	
}
