package com.samsam;

import java.util.List;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.samsam.repository.CommRepository;
import com.samsam.repository.PostRepository;
import com.samsam.repository.UserRepository;
import com.samsam.vo.CommentVO;
import com.samsam.vo.PostVO;
import com.samsam.vo.UserVO;

@SpringBootTest
public class TaeyoungTest {
	
	@Autowired
	PostRepository postRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	CommRepository commRepo;
	
	
	//@Test
	void test5() {
		//특정 user가 자신의 모든 post 조회하기
		UserVO user = userRepo.findById(2).get();
		postRepo.findByUser(user).forEach(post ->{
			System.out.println(post);
		});
	}
	
	//@Test
	void test4() {
		//특정 post의 모든 댓글 조회하기
		PostVO post = postRepo.findById(5).get();
		commRepo.findByPost(post).forEach(comm ->{
			System.out.println(comm);
		});
		
	}
	
	//@Test
	void test3() {
		//1개의 post에 댓글이 들어가는가
		UserVO user = userRepo.findById(1).get();
		PostVO post = postRepo.findById(5).get();
		
		CommentVO comm = CommentVO.builder()
				.commContent("두번째 댓글입니다")
				.post(post)
				.commuser(user)
				.build();
		
		commRepo.save(comm);
	}
	
	
	//@Test
	void test2() {
		//user한명에게 여러개의 post가 들어가는가
		UserVO user = userRepo.findById(2).get();
		
		IntStream.range(1, 5).forEach(i->{
			PostVO post = PostVO.builder()
					.postImg("이미지" + i + ".png")
					.user(user)
					.build();
			postRepo.save(post);
		});
	}
	
	//@Test
	void test1() {
		//user만들기
		UserVO u1 = UserVO.builder()
				.userNickname("지만2")
				.userEmail("지만@지만2")
				.userPass("12342222")
				.userBirth(980420)
				.userGender(1)
				.build();
		userRepo.save(u1);
	}

}
