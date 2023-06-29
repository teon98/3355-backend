package com.samsam.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.samsam.service.CommunityService;
import com.samsam.vo.FollowVO;
import com.samsam.vo.ProfileVO;
import com.samsam.vo.TagVO;

@RestController
@RequestMapping("/commu")
public class CommunityRestController {
	
	@Autowired
	CommunityService communityService;
	
	@GetMapping(value="/tagListHighTen")
	public List<TagVO> tagListHighTen() {
		return communityService.tagListHighTen();
	}
	
	@DeleteMapping(value="/followcancel")
	public ResponseEntity<?> followCancel(
			@RequestParam int owner, @RequestParam int user) {
		System.out.println(owner + "  "+ user);
		communityService.followCancel(owner, user);
		return ResponseEntity.ok("CANCEL_COMPLETE");
	}
	
	@PostMapping(value="/followrequest")
	public ResponseEntity<?> followRequest(
			@RequestParam int owner, @RequestParam int user){
		communityService.followRequest(owner, user);
		return ResponseEntity.ok("OK");
	}
	
	@GetMapping(value="followEachother")
	public String followEachOther(
			@RequestParam int userNo,
			@RequestParam String userNickname) {
		return communityService.followEachOther(userNo, userNickname);
	}
	
	@GetMapping(value="profile")
	public ProfileVO profile(@RequestParam String userNickname){
		return communityService.profile(userNickname);
	}
	
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