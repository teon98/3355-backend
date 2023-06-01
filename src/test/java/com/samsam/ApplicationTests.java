package com.samsam;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.samsam.repository.ProfileRepository;
import com.samsam.repository.UserRepository;
import com.samsam.vo.ProfileVO;
import com.samsam.vo.UserVO;

@SpringBootTest
class ApplicationTests {
	
	@Autowired
	UserRepository urepo;
	@Autowired
	ProfileRepository prepo;
	
	@Test
	void test3() {
		ProfileVO p = ProfileVO.builder()
				.profileAbout("안녕하세요3")
				.build();
		
		ProfileVO p2 = prepo.save(p);
		
		UserVO user = urepo.findById(1).orElse(null);
		System.out.println(user);
		
		p2.setUser(user);
		prepo.save(p2);
	}
	
	//@Test
	void test2() {
		UserVO u1 = UserVO.builder()
				.userNickname("지만1")
				.userEmail("지만@지만1")
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

	
	//@Test
	void test1() {
//		TestVO t1 = TestVO.builder().testID("980421").testName("지만1").build();
//		repo.save(t1);
	}

}
