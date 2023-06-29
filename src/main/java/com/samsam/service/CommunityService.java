package com.samsam.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsam.repository.FollowRepository;
import com.samsam.repository.ProfileRepository;
import com.samsam.repository.TagRepository;
import com.samsam.repository.UserRepository;
import com.samsam.vo.FollowId;
import com.samsam.vo.FollowVO;
import com.samsam.vo.PostVO;
import com.samsam.vo.ProfileVO;
import com.samsam.vo.TagVO;
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
	
	@Autowired
	TagRepository tagRepo;
	
	//tag리스트 불러오기
	public List<TagVO> tagListHighTen() {
		List<TagVO> tagList = tagRepo.findAllHighTen();
		
		return tagList;
	}
	
	//팔로우 취소하기
	public void followCancel(int owner, int user) {
		UserVO user1 = userRepo.findById(owner).get();
		UserVO user2 = userRepo.findById(user).get();
		
		FollowId fid = FollowId.builder()
				.followStart(user1)
				.followEnd(user2)
				.build();
		
		followRepo.deleteById(fid);
	}
	
	//팔로우 하기
	public FollowVO followRequest(int owner, int user) {
		UserVO user1 = userRepo.findById(owner).get();
		UserVO user2 = userRepo.findById(user).get();
		
		FollowId fid = FollowId.builder()
				.followStart(user1)
				.followEnd(user2)
				.build();
		
		FollowVO f = FollowVO.builder()
				.follow(fid)
				.build();
		
		return followRepo.save(f);
	}

	//서로 팔로우 중인지 여부 확인
	public String followEachOther(int userNo, String userNickname) {
		UserVO user = userRepo.findByUserNickname(userNickname);
		
		int count1 = followRepo.findByEachOther(userNo, user.getUserNo());
		int count2 = followRepo.findByEachOther(user.getUserNo(), userNo);
		
		System.out.println("내가 팔로우 중인가?" + count1);
		System.out.println("그 사람이 나를 팔로우 중인가?" + count2);
		System.out.println("서로가 팔로우 중인가?" + (count1 == count2));
		
		String result = "";
		
		if( count1 == 1 ) {
			result = "팔로우";
		} else if(count1 == 0) {
			if(count2 == 1) {
				result = "맞팔로우";
			}else {
				result ="팔로잉";
			}
		}
		
		return result;
	}
	
	//User 정보 보여주기(로그인한 UserX)
	public ProfileVO profile(String userNickname){
		HashMap<String, String> result = new HashMap<String, String>();
		
		UserVO user = userRepo.findByUserNickname(userNickname);
		ProfileVO profile = profileRepo.findByUser(user);
		
		return profile;
	}
	
	//로그인한 User 정보 보여주기
	public HashMap<String, String> UserPofile(int userNo) {
		HashMap<String, String> result = new HashMap<String, String>();
		UserVO user = userRepo.findById(userNo).get();
		ProfileVO profile = profileRepo.findByUser(user);
		
		result.put("nickname", user.getUserNickname());
		result.put("profile", profile.getProfileImg());
		
		
		return result;
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