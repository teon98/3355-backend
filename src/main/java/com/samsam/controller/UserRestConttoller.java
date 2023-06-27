package com.samsam.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.samsam.repository.CardRepository;
import com.samsam.repository.CardcustomRepository;
import com.samsam.repository.ProfileRepository;
import com.samsam.repository.UserRepository;
import com.samsam.vo.CardVO;
import com.samsam.vo.CardcustomVO;
import com.samsam.vo.ProfileVO;
import com.samsam.vo.UserVO;

@RestController
@RequestMapping("/user")
public class UserRestConttoller {

	@Autowired
	UserRepository userRepo;
	@Autowired
	ProfileRepository proRepo;
	@Autowired
	CardRepository cardRepo;
	@Autowired
	JavaMailSender javaMailSender;
	@Autowired
	CardcustomRepository customRepo;
	
	
	// 이메일 중복체크
	@GetMapping(value = "/emailDup.sam/{userEmail}")
	public String EmailDup(@PathVariable String userEmail) {
		String message = "";
//		return userRepo.findByUserEmail(userEmail)==null?"not found":"OK";

		if (userRepo.findByUserEmail(userEmail) == null) {
			message = "성공";
		} else {
			message = "실패";
		}
		return message;
	}

	// 별명 중복체크
	@GetMapping(value = "/nicknameDup.sam/{userNickname}")
	public String NicknameDup(@PathVariable String userNickname) {
		String message = "";
//		return userRepo.findByUserEmail(userEmail)==null?"not found":"OK";

		if (userRepo.findByUserNickname(userNickname) == null) {
			message = "성공";
		} else {
			
			message = "실패";
		}
		return message;
	}

	// 유저 회원가입
	@PostMapping(value = "/insert.sam", consumes = "application/json")
	public Integer UserRegisterPost(@RequestBody UserVO user) {
		
	
		UserVO newuser = userRepo.save(user);
		ProfileVO profile = ProfileVO.builder().user(newuser).build();

		proRepo.save(profile);
		return newuser.getUserNo();
	}

	// oauth회원가입1
	@PostMapping(value = "/signGoogle.sam", consumes = "application/json")
	public Integer GoogleSignUP(@RequestBody UserVO user) {
		Integer message = 0;

		if (userRepo.findByUserEmail(user.getUserEmail()) == null) {
			// UserVO user1 = userRepo.findByUserEmail(user.getUserEmail());

			userRepo.save(user);
			message = 1;
		} else {
			message = 0;
		}

		return message;
	}

	// oauth회원가입2
	@PostMapping(value = "/signGoogleInsert.sam", consumes = "application/json")
	public Integer GoogleSignUPInsert(@RequestBody UserVO user) {

		UserVO user1= userRepo.findByUserEmail(user.getUserEmail());
		user1.setUserPass(user.getUserPass());
		user1.setUserBirth(user.getUserBirth());
		user1.setUserGender(user.getUserGender());
		user1.setUserNickname(user.getUserNickname());
		
		UserVO newuser = userRepo.save(user1);
		ProfileVO profile = ProfileVO.builder().user(newuser).build();

		proRepo.save(profile);
		return newuser.getUserNo();

	}

	// oauth이메일 가져오기
	@GetMapping(value = "/oauthEmail.sam")
	public String oauthEmail() {
		UserVO newuser = userRepo.findByUserBirthIsNull();
		System.out.println(newuser.getUserEmail());
		return newuser.getUserEmail();
	}

	//유저 이메일 가져오기
	@GetMapping("/getNickname.sam")
	public String getEmail(@RequestParam int userNo) {
		UserVO user = userRepo.findById(userNo).get();
		return user.getUserNickname(); 
	}
	
	// 카드 생성
	@PostMapping(value = "/insertCard.sam", consumes = "application/json")
	public String CardRegisterPost(@RequestBody CardVO card, @RequestParam Integer userNo) {

		System.out.println(card);

		UserVO user = userRepo.findById(userNo).get();
		CardVO savedCard = cardRepo.save(card);

		// 카드 시퀀스(1000~9999)로 카드 번호 생성
		String rst = "3355";
		rst += "-" + savedCard.getCardSeq();
		Random random = new Random();
		rst += "-" + (random.nextInt(9000) + 1000);
		rst += "-" + (random.nextInt(9000) + 1000);
		savedCard.setCardCode(rst);
		savedCard.setUser(user);
		cardRepo.save(savedCard);
		
		CardcustomVO custom = CardcustomVO.builder().user(user).build();
		customRepo.save(custom);
		
		return "성공^^";
	}

	// 로그인
	@GetMapping(value = "/login.sam") 
	public int UserLogin(@RequestParam String userEmail, @RequestParam String userPass) {
		int userNo = 0;
		
	
		if (userRepo.findByUserEmailAndUserPass(userEmail, userPass) != null) {
			userNo = userRepo.findByUserEmailAndUserPass(userEmail, userPass).getUserNo();
		} else {
			userNo = 0;
		}
		return userNo;
	}
	// 비밀번호 찾기
	@PutMapping(value = "/findPass.sam") 
	public Integer FindPass(@RequestParam String userEmail, @RequestParam String userNickname) {
		Integer userNo = 0;
		if (userRepo.findByUserEmailAndUserNickname(userEmail, userNickname) != null) {
			UserVO user = userRepo.findByUserEmailAndUserNickname(userEmail, userNickname);
			Random random = new Random();
			String userPass = (random.nextInt(9000) + 1000) + "";
			user.setUserPass(userPass);

			userRepo.save(user);

			SimpleMailMessage message2 = new SimpleMailMessage();
			message2.setFrom("shinhan3355@gmail.com");
			message2.setTo(userEmail);
			message2.setSubject("임시 비밀번호");
			message2.setText(userPass);

			javaMailSender.send(message2);

			System.out.println("성공");

			userNo = user.getUserNo();
		} else {
			userNo = 0;
		}

		return userNo;
	}

	// 비밀번호 변경
	@PutMapping(value = "/PassChange.sam")
	public String PassChange(@RequestParam int userNo,@RequestParam String tempPass, @RequestParam String userPass) {
		String message = "";
		String pass = userRepo.findById(userNo).get().getUserPass();
		
		if (pass.equals(tempPass)) {
			UserVO user = userRepo.findByUserPass(tempPass);
			user.setUserPass(userPass);
			userRepo.save(user);
			message = "성공";
		} else {
			message = "실패";
		}

		return message;
	}
	
	//회원 탈퇴 
	@DeleteMapping(value = "/delete.sam")
	public int deleteUser(@RequestParam int userNo) {
		int user_no =userNo;
		UserVO user = userRepo.findById(userNo).get();
		userRepo.delete(user);
		
	
			user_no = 0;
		
		
		return user_no;
	}
}
