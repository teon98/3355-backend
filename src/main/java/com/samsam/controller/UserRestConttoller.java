package com.samsam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samsam.repository.UserRepository;
import com.samsam.vo.UserVO;

@RestController
@RequestMapping("/user")
public class UserRestConttoller {

	@Autowired
	UserRepository userRepo;

	@GetMapping(value = "/emailDup.sam/{userEmail}")//이메일 중복체크
	public String EmailDup(@PathVariable String userEmail) {
		String message = "";
//		return userRepo.findByUserEmail(userEmail)==null?"not found":"OK";

		if (userRepo.findByUserEmail(userEmail) == null) {
			message = "사용 가능한 이메일입니다.";
		} else {
			message = "이미 사용중인 이메일입니다.";
		}
		return message;
	}
	
	@GetMapping(value = "/nicknameDup.sam/{userNickname}")//별명 중복체크
	public String NicknameDup(@PathVariable String userNickname) {
		String message = "";
//		return userRepo.findByUserEmail(userEmail)==null?"not found":"OK";

		if (userRepo.findByUserEmail(userNickname) == null) {
			message = "사용 가능한 별명입니다.";
		} else {
			message = "이미 사용중인 별명입니다.";
		}
		return message;
	}

	@PostMapping(value = "/insert.sam", consumes = "application/json") // 유저 회원가입
	public String UserRegisterPost(@RequestBody UserVO user) {

		UserVO newuser = userRepo.save(user);

		return "성공";
	}

}
