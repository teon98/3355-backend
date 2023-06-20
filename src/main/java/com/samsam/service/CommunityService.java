package com.samsam.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsam.repository.FollowRepository;
import com.samsam.repository.ProfileRepository;
import com.samsam.repository.UserRepository;
import com.samsam.vo.ProfileVO;
import com.samsam.vo.UserVO;

@Service
@Transactional
public class CommunityService {
	@Autowired
	ProfileRepository profileRepo;
	
	@Autowired
	FollowRepository followRepo;
	
	@Autowired
	UserRepository userRepo;
	
	//로그인한 User 정보 보여주기
	public String UserPofile(int userNo) {
		UserVO user = userRepo.findById(userNo).get();
		ProfileVO profile = profileRepo.findByUser(user);
		
		return profile.getProfileImg();
	}
	
	//커뮤니티 홈에서 내가 팔로우하는 사람들의 프로필 목록 보여주기
	public List<Object> followingProfileImgList(int userNo){
		
		List<Object> result = new ArrayList<>();
		List<Integer> followings =  followRepo.findByFollowStart(userNo);
		List<UserVO> followinglist = new ArrayList<UserVO>();
		
		followings.forEach((id) ->{
			UserVO user = userRepo.findById(id).get();
			followinglist.add(user);
		});
		
		for(UserVO user : followinglist) {
			HashMap<String, String> users = new HashMap<>();
			if(user.getProfile() == null) {
				users.put("profileImg", null);
			}else {
				users.put("profileImg", user.getProfile().getProfileImg());
			}
			users.put("nickname", user.getUserNickname());
			
			result.add(users);
		}
		
		return result;
	}
}
