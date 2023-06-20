package com.samsam.service;

import java.io.IOException;
import java.util.HashMap;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.samsam.repository.ProfileRepository;
import com.samsam.repository.UserRepository;
import com.samsam.vo.ProfileVO;
import com.samsam.vo.UserVO;

@Service
@Transactional
public class ProfileService {
	
	@Autowired
	private S3Uploader s3uploader;
	@Autowired
	ProfileRepository profileRepo;
	@Autowired
	UserRepository userRepo;
	
	
	//userNO로 해당 user의 profile select
	public HashMap<String, Object> getProfile(int userNo) {
		UserVO user = userRepo.findById(userNo).get();
		
		ProfileVO profile = profileRepo.findByUser(user);
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("userNickname", user.getUserNickname());
		result.put("profileVO", profile);
		return result;
	}
	
	
	//S3에 프로필 이미지 업로드 + about수정
	public Integer uploadImg(MultipartFile image, int userNo, String profileAbout) throws IOException {
		System.out.println("Upload S3 Images with profile");
		ProfileVO profile = null;
		UserVO user = userRepo.findById(userNo).get();
		profile = profileRepo.findByUser(user);
		if(image!=null && !image.isEmpty()) {
			String storedFileName = s3uploader.upload(image, "profile");
			profile.setProfileImg(storedFileName);	
		}
		if(!profileAbout.isEmpty()) {
			profile.setProfileAbout(profileAbout);
		}
		
		ProfileVO saveProfile = profileRepo.save(profile);
		return saveProfile.getProfileId();
	}
	
	
}