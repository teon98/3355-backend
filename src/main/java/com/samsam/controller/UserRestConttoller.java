package com.samsam.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.samsam.repository.CardRepository;
import com.samsam.repository.ProfileRepository;
import com.samsam.repository.UserRepository;
import com.samsam.vo.CardVO;
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

	@GetMapping(value = "/emailDup.sam/{userEmail}") // 이메일 중복체크
	public String EmailDup(@PathVariable String userEmail) {
		String message = "";
//		return userRepo.findByUserEmail(userEmail)==null?"not found":"OK";

		if (userRepo.findByUserEmail(userEmail) == null) {
			message = "사용 가능한 이메일입니다.";
		} else {
			message = "이미 사용중인 이메일입니다.";
		}
		return message;
	}

	@GetMapping(value = "/nicknameDup.sam/{userNickname}") // 별명 중복체크
	public String NicknameDup(@PathVariable String userNickname) {
		String message = "";
//		return userRepo.findByUserEmail(userEmail)==null?"not found":"OK";

		if (userRepo.findByUserEmail(userNickname) == null) {
			message = "사용 가능한 별명입니다.";
		} else {
			message = "이미 사용중인 별명입니다.";
		}
		return message;
	}

	@PostMapping(value = "/insert.sam", consumes = "application/json") // 유저 회원가입
	public Integer UserRegisterPost(@RequestBody UserVO user) {

		UserVO newuser = userRepo.save(user);
		ProfileVO profile = ProfileVO.builder().user(newuser).build();

		proRepo.save(profile);
		return newuser.getUserNo();
	}

	@PostMapping(value = "/insertCard.sam", consumes = "application/json") // 카드 생성
	public String CardRegisterPost(@RequestBody CardVO card, @RequestParam Integer userNo) {
		System.out.println(card);
//		
//		UserVO user = userRepo.findById(userNo).get();
//		CardVO card1 =CardVO.builder().cardPass(Integer.parseInt(cardPass)).user(user).build();
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

		return "성공^^";
	}

	@GetMapping(value = "/login.sam") // 로그인
	public int UserLogin(@RequestParam String userEmail, @RequestParam String userPass) {
		int userNo = 0;
		if (userRepo.findByUserEmailAndUserPass(userEmail, userPass) != null) {
			userNo = userRepo.findByUserEmailAndUserPass(userEmail, userPass).getUserNo();
		} else {
			userNo = 0;
		}
		return userNo;
	}

	@PutMapping(value = "/findPass.sam") // 비밀번호 찾기
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
	public String PassChange(@RequestParam String tempPass, @RequestParam String userPass) {
		String message = "";

		if (userRepo.findByUserPass(tempPass) == null) {
			message = "실패";
		} else {
			UserVO user = userRepo.findByUserPass(tempPass);
			user.setUserPass(userPass);
			userRepo.save(user);
			message = "성공";
		}

		return message;
	}
}
