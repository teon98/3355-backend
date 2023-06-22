package com.samsam.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.samsam.service.CommunityService;

@RestController
@RequestMapping("/commu")
public class CommunityRestController {
	
	@Autowired
	CommunityService communityService;
	
	@GetMapping(value = "followingProfileImgList")
	//커뮤니티 홈에서 내가 팔로우하는 사람들의 프로필 목록 보여주기
	public List<Object> followingProfileImgList(@RequestParam int userNo){
		return communityService.followingProfileImgList(userNo);
	}
	
	@GetMapping(value = "userProfile")
	public HashMap<String, String> UserPofile(int userNo) {
		return communityService.UserPofile(userNo);
	}
	
}