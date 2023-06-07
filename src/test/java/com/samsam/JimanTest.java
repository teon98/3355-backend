package com.samsam;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.samsam.repository.AlarmRepository;
import com.samsam.repository.DailyStampRepository;
import com.samsam.repository.FollowRepository;
import com.samsam.repository.ProfileRepository;
import com.samsam.repository.UserRepository;
import com.samsam.repository.WorkRepository;
import com.samsam.vo.AlarmVO;
import com.samsam.vo.DailyStampVO;
import com.samsam.vo.FollowId;
import com.samsam.vo.FollowVO;
import com.samsam.vo.ProfileVO;
import com.samsam.vo.UserLevelRole;
import com.samsam.vo.UserVO;
import com.samsam.vo.WorkVO;

@SpringBootTest
public class JimanTest {
	@Autowired
	UserRepository urepo;
	@Autowired
	ProfileRepository prepo;
	@Autowired
	WorkRepository wrepo;
	@Autowired
	DailyStampRepository drepo;
	@Autowired
	FollowRepository frepo;
	@Autowired
	AlarmRepository arepo;
	
	@Test//이메일로 유저 조회
	void test10() {
		String message = "";
		UserVO user =urepo.findByUserEmail("지만@지만");
		
		if(user.getUserEmail().length()>1) {
			message ="이미 사용중인 이메일입니다.";
		}else{
			message ="사용 가능한 이메일입니다.";
		}
		System.out.println(message);
		
	}
	
	//@Test//내 프로필 조회
	 void test9() {
		UserVO user1 = urepo.findById(1).get();
		ProfileVO pro =  prepo.findByUser(user1);
		System.out.println(pro.getUser());
	}
	
	//@Test//오운완 조회 
	void test8() {
		UserVO user1 = urepo.findById(1).get();
		wrepo.findByUser(user1).forEach(work ->{ 
			System.out.println(work);
		});
		
		
	}
	
	
	//@Test//알람 테이블 추가
	void test7() {
		AlarmVO a = AlarmVO.builder()
				.alarmCategory("입금")
				.alarmMsg("10000원 입금")
				.build();
		AlarmVO alarm = arepo.save(a);
		UserVO user1 = urepo.findById(1).get();
		alarm.setUser(user1);
		arepo.save(alarm);
	}
	
	//@Test//팔로우 테이블 추가
	void test6() {
		UserVO user1 = urepo.findById(1).get();
		UserVO user2 = urepo.findById(2).get();
		
		FollowId fid = FollowId.builder()
				.followStart(user1)
				.followEnd(user2)
				.build();
		
		FollowVO f = FollowVO.builder()
				.follow(fid)
				.build();
		
		frepo.save(f);
	}
	
	//@Test//출석체크 입력
	void test5() {
		DailyStampVO d = DailyStampVO.builder().build();
		DailyStampVO d2 = drepo.save(d);
		
		UserVO user = urepo.findById(1).get();
		
		d2.setUser(user);
		drepo.save(d2);
		
	}
	
	
	//@Test//오운완 입력
	void test4() {
		
		IntStream.rangeClosed(1, 5).forEach(i->{
			WorkVO w = WorkVO.builder().build();
			WorkVO w2 = wrepo.save(w);
			
			UserVO user = urepo.findById(2).get();
			w2.setUser(user);
			wrepo.save(w2);
		});
	
	}
	
	//@Test//프로필 생성
	void test3() {
		ProfileVO p = ProfileVO.builder()
				.profileAbout("안녕하세요3")
				.profileImg("기본사진.jpg")
				.build();
		
		ProfileVO p2 = prepo.save(p);
		
		UserVO user = urepo.findById(2).get();
		p2.setProfileLevel(UserLevelRole.GOLD1);
		p2.setUser(user);
		prepo.save(p2);
	}
	
	//@Test//유저 입력
	void test2() {
		UserVO u1 = UserVO.builder()
				.userNickname("지만2")
				.userEmail("지만@지만2")
				.userPass("1234")
				.userBirth(980420)
				.userGender(1)
				.build();
//		ProfileVO p1=ProfileVO.builder()
//				.user(u1)
//				.build();
		urepo.save(u1);
//		prepo.save(p1);
	}
}
