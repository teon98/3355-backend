package com.samsam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.samsam.repository.CommRepository;
import com.samsam.repository.FollowRepository;
import com.samsam.repository.PostRepository;
import com.samsam.repository.PostTagRepository;
import com.samsam.repository.TagRepository;
import com.samsam.repository.UserRepository;
import com.samsam.vo.CommentVO;
import com.samsam.vo.FollowId;
import com.samsam.vo.FollowVO;
import com.samsam.vo.PostTagVO;
import com.samsam.vo.PostVO;
import com.samsam.vo.TagVO;
import com.samsam.vo.UserVO;

@SpringBootTest
public class TaeyoungTest {
	
	@Autowired
	PostRepository postRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	CommRepository commRepo;
	@Autowired
	TagRepository tagRepo;
	@Autowired
	PostTagRepository posttagRepo;
	@Autowired
	FollowRepository followRepo;
	int i=0;
	@Test
	void test7() {
		//userRepo.deleteById(116);
		//내가 Follow하는 사람들 목록 가져오기
 
		List<Integer> myfollowers =  followRepo.findByFollowStart(1);
		myfollowers.forEach((item)->{
			System.out.println(item);});	
		
		
		UserVO[] arr = new UserVO[myfollowers.size()];
		
		myfollowers.forEach((id)->{
			System.out.println(id + "+" + i);
			arr[i] = userRepo.findById(id).get();
			
			System.out.println(i + ":" + arr[i].getUserNickname());
			i++;
		});
	}
	
	//@Test
	void test6() {
		//특정 user가 여러 유저 Follow 하기
		UserVO user1 = userRepo.findById(1).get();
		UserVO user2 = userRepo.findById(116).get();
		
		FollowId fid = FollowId.builder()
				.followStart(user1)
				.followEnd(user2)
				.build();
		
		FollowVO f = FollowVO.builder()
				.follow(fid)
				.build();
		
		//frepo.save(f);
	}
	
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
		UserVO user = userRepo.findById(1).get();
		
		IntStream.range(1, 5).forEach(i->{	
			PostVO post = PostVO.builder()
					.postImg("이미지" + i + ".png")
					.user(user)
					.build();
			postRepo.save(post);
		});
	}
	
	//@Test
	void testTag() {
		//tag 생성
		TagVO tag = TagVO.builder()
				.tagContent("#수영")
				.tagCount(0)
				.build();
		tagRepo.save(tag);
	}
	
	//@Test
	void test1() {
		//user만들기
		UserVO u1 = UserVO.builder()
				.userNickname("지만1")
				.userEmail("지만@1111")
				.userPass("11")
				.userBirth(980914)
				.userGender(2)
				.build();
		userRepo.save(u1);
	}

}
