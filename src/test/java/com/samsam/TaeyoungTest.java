package com.samsam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.samsam.repository.CommRepository;
import com.samsam.repository.FollowRepository;
import com.samsam.repository.GoodRepository;
import com.samsam.repository.PostRepository;
import com.samsam.repository.PostTagRepository;
import com.samsam.repository.ProfileRepository;
import com.samsam.repository.TagRepository;
import com.samsam.repository.UserRepository;
import com.samsam.vo.CommentVO;
import com.samsam.vo.FollowId;
import com.samsam.vo.FollowVO;
import com.samsam.vo.GoodVO;
import com.samsam.vo.PostTagVO;
import com.samsam.vo.PostVO;
import com.samsam.vo.ProfileVO;
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
	@Autowired
	ProfileRepository profileRepo;
	@Autowired
	GoodRepository goodRepo;
	
	@Test
	void test20() {
		List<TagVO> tagList = tagRepo.findAllHighTen();
		
		for(TagVO tag : tagList) {
			System.out.println(tag);
		}
	}
	
//	@Test
//	void test19() {
//		PostVO post = postRepo.findById(26).get();
//		List<CommentVO> comments= commRepo.findByPost(post);
//		
//		for(CommentVO comment: comments) {
//			System.out.println(comment.getCommContent());
//		}
//	}
	
	//@Test
	void test18() {
		//팔로우 한 사람인지 아닌지 알아내기
		String userNickname = "깡불호";
		
		UserVO user = userRepo.findByUserNickname(userNickname);
		
		int count1 = followRepo.findByEachOther(1, user.getUserNo());
		int count2 = followRepo.findByEachOther(user.getUserNo(), 1);
		
		System.out.println("내가 팔로우 중인가?" + count1);
		System.out.println("그 사람이 나를 팔로우 중인가?" + count2);
		System.out.println("서로가 팔로우 중인가?" + (count1 == count2));
		
		
	}
	
	//@Test
	void test17() {
		List<Object> result = new ArrayList<>();
		
		//1. userNickname으로 post를 get 해와햐함.
		String userNickname = "TEON";
		
		UserVO user = userRepo.findByUserNickname(userNickname);
		List<PostVO> myPostList = postRepo.findByUserOrderByPostDateDesc(user);
		
		for(PostVO post : myPostList) {
			System.out.println(post.getPostNo());
			System.out.println(goodRepo.findByGoodsCount(post.getPostNo()));
		}
		
	}
	
	//@Test
	void test16() {
		//pathvariable로 들어온 user닉네임을 찾는다.
		String userNickname = "깡불호";
		UserVO user = userRepo.findByUserNickname(userNickname);
		
		//필요 정보
		//user이미지, userlevel, usernickname, userAbout
		
		ProfileVO profile = profileRepo.findByUser(user);
		System.out.println(profile.toString());
	}
	
	//@Test
	void test15() {
		List<Object> result = new ArrayList<>();
		
		UserVO user = userRepo.findById(1).get();
		//포스트 찾기
		List<PostVO> myPostList = postRepo.findByUserOrderByPostDateDesc(user);
		
		for(PostVO post : myPostList) {
			System.out.println(post.getPostNo());
			System.out.println(goodRepo.findByGoodsCount(post.getPostNo()));
		}
		
		
	}
	//@Test
	void test13() {
		//내가 팔로우 하는 사람들 + 내 게시물만 전체 조회하기 + 근데 좋아요도 같이 불러오는
		UserVO user = userRepo.findById(1).get();
		
		//내가 팔로우 하는 사람들 찾기
		List<Integer> myFoloowerList = followRepo.findByFollowStart(user.getUserNo());
		myFoloowerList.add(user.getUserNo());//[1,2,3]
		
		List<UserVO> myFoloowerUserList = new ArrayList<UserVO>();
		
		//사람들 번호로 User객체 만들어 저장
		for(int f_num: myFoloowerList) {
			UserVO f_user = userRepo.findById(f_num).get();
			myFoloowerUserList.add(f_user);
		}
		
		//postRepo
		List<PostVO> followerPosts = postRepo.findByUserInOrderByPostDateDesc(myFoloowerUserList);
		for(PostVO post: followerPosts) {
			System.out.println(post.getPostNo());
			//좋아요도... 찾아야 하는
			System.out.println(goodRepo.findByGoodsCount(post.getPostNo()));
		}
		
	}
	
	//@Test
	void test14() {
		PostVO post = postRepo.findById(5).get();
		UserVO user = userRepo.findById(1).get();
		//좋아요 누르면 저장
		GoodVO good = GoodVO.builder()
				.post(post)
				.user(user)
				.build();
		goodRepo.save(good);
	}
	
	//Test
	void test12() {
		//posttag(mapping table) insert
		
		PostVO post = postRepo.findById(1).get();
		TagVO tag = tagRepo.findById(1).get();
		
		PostTagVO posttag = PostTagVO.builder()
									.post(post)
									.tag(tag)
									.build();
		posttagRepo.save(posttag);
	}
	
	//@Test
	void test11() {
		String[] tagList = new String[] {"test1", "test2", "test3", "test5"};
		
		
		for(String tag: tagList) {
			TagVO currentTag = tagRepo.findByTagContent(tag);
			if(currentTag!=null) { //null이 아니면 존재
				currentTag.setTagCount(currentTag.getTagCount() + 1); //tag 개수 증가
			}else { //null이면 아예 없다는 뜻이니까
				currentTag = TagVO.builder()
						.tagContent(tag)
						.tagCount(1)
						.build();
			}
			tagRepo.save(currentTag);
		}
		
	}
	
	//@Test
	void test10() {
		//post 등록 태그도 같이 등록된다.
		//로그인한 사용자 찾기
		UserVO user = userRepo.findById(1).get();
		
		//1.Post 테이블에 먼저 save

		
		//2.Tag 테이블에 그다음 save
		TagVO tag = TagVO.builder()
				.tagContent("#수영")
				.tagCount(0)
				.build();
		tagRepo.save(tag);
		//2-1. 기존에 있는 tag면 count가 plus
		//3.PostTag 테이블에 save
		
		
		
		
	}
	
	//@Test
	void test9() {
		//userNo로 profileDB에서 select해오기
		UserVO user = userRepo.findById(1).get();
		
		ProfileVO profile = profileRepo.findByUser(user);
		System.out.println(user.getUserNickname());
		System.out.println(profile);
	}
	
	//@Test
	void test8() {
		List<Object> result = new ArrayList<>();
		List<Integer> followings =  followRepo.findByFollowStart(1);
		List<UserVO> followinglist = new ArrayList<UserVO>();
		
		followings.forEach((id) ->{
			UserVO user = userRepo.findById(id).get();
			followinglist.add(user);
		});
		
		
		
		for(UserVO user : followinglist) {
			System.out.println(user.getUserNo()+"번 불러옴");
			
			HashMap<String, String> users = new HashMap<>();
			
			if(user.getProfile() == null) {
				users.put("profileImg", null);
			}else {
				users.put("profileImg", user.getProfile().getProfileImg());
			}
			users.put("nickname", user.getUserNickname());
			
			result.add(users);
		}
		
		System.out.println(result);
	}
	
	//@Test
	void test7() {
		//userRepo.deleteById(116);
		//내가 Follow하는 사람들 목록 가져오기
 
		List<Integer> followings =  followRepo.findByFollowStart(1);
		followings.forEach((item)->{
			System.out.println(item);
		});	
		
		
		List<UserVO> followinglist = new ArrayList<UserVO>();
		followings.forEach((id) ->{
			UserVO user = userRepo.findById(id).get();
			followinglist.add(user);
		});
		
		for(UserVO user : followinglist) {
			System.out.println(user.getUserNo());
			if(user.getProfile() == null) {
				System.out.println(user.getProfile());
			}else {
				System.out.println(user.getProfile().getProfileImg());
			}
			System.out.println(user.getUserNickname());
			System.out.println("-------------");
		}
		
//		UserVO[] myfollowinglist = new UserVO[myfollowers.size()];
//		System.out.println(myfollowinglist);
		
//		myfollowers.forEach((id)->{
//			System.out.println(id + "+" + i);
//			arr[i] = userRepo.findById(id).get();
//			
//			System.out.println(i + ":" + arr[i].getUserNickname());
//			i++;
//		});
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
//	void test4() {
//		//특정 post의 모든 댓글 조회하기
//		PostVO post = postRepo.findById(5).get();
//		commRepo.findByPost(post).forEach(comm ->{
//			System.out.println(comm);
//		});
//		
//	}
	
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
				.tagContent("#오운완")
				.tagCount(1)
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
