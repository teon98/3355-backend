package com.samsam.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.samsam.service.ProfileService;

@RestController
@RequestMapping("/profile")
public class ProfileRestController {
	
	@Autowired
	ProfileService profileservice;
	
	//userNO로 해당 user의 profile select
	@GetMapping
	public HashMap<String, Object> getProfile(@RequestParam("userNo") int userNo) {
		return profileservice.getProfile(userNo);
	}

	//프로필 이미지만 업로드
	@PatchMapping(value="/s3upload")
	public ResponseEntity<?> uploadImg(
			@RequestParam(required = false) MultipartFile profileImg, 
			@RequestParam int userNo,
			@RequestParam String profileAbout
			){
		int profile_id = 0;
		try {
			profile_id = profileservice.uploadImg(profileImg, userNo, profileAbout);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		return ResponseEntity.ok(profile_id);
	}
}