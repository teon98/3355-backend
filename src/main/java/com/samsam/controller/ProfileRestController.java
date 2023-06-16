package com.samsam.controller;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.samsam.service.ProfileService;
import com.samsam.service.S3Uploader;
import com.samsam.vo.ProfileVO;

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
	@ResponseBody
	@PostMapping(value="/s3upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String uploadImg(
			@RequestParam("profileImg")MultipartFile image, 
			@RequestParam("userNo") int userNo
			){
		int profile_id = 0;
		try {
			profile_id = profileservice.uploadImg(image, userNo);
		} catch (IOException e) {
			return "이미지 등록 실패";
		}
		return profile_id + "이미지 등록 성공";
	}
}
