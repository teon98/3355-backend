package com.samsam.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.samsam.repository.CardRepository;
import com.samsam.repository.CardcustomRepository;
import com.samsam.repository.DailyStampRepository;
import com.samsam.repository.PointRepository;
import com.samsam.repository.ProfileRepository;
import com.samsam.repository.UserRepository;
import com.samsam.repository.WorkRepository;
import com.samsam.vo.CardVO;
import com.samsam.vo.CardcustomVO;
import com.samsam.vo.DailyStampVO;
import com.samsam.vo.PointVO;
import com.samsam.vo.UserVO;

@RestController
@RequestMapping("/my")
public class MyPageRestController {

	@Autowired
	UserRepository userRepo;
	@Autowired
	ProfileRepository proRepo;
	@Autowired
	WorkRepository workRepo;
	@Autowired
	DailyStampRepository dayRepo;
	@Autowired
	CardcustomRepository cusRepo;
	@Autowired
	CardRepository cardRepo;
	@Autowired
	PointRepository pointRepo;

	// 커스텀 카드 입력
	@PostMapping(value = "/postcustom.sam")
	public String postCustom(@RequestParam int userNo, @RequestParam String customColor1,
			@RequestParam String customColor2, @RequestParam String customLettering,
			@RequestParam String customColor3) {
		String msg = "";
		CardcustomVO card = cusRepo.findByUserNo(userNo);
		card.setCustomColor1(customColor1);
		card.setCustomColor2(customColor2);
		card.setCustomLettering(customLettering);
		card.setCustomColor3(customColor3);

		CardcustomVO card2 = cusRepo.save(card);
		if (card2.getCustomColor1().equals(card.getCustomColor1())) {
			msg = "성공";
		} else {
			msg = "실패";
		}

		return msg;
	}

	// 커스텀 정보
	@GetMapping(value = "/getcustom.sam")
	public CardcustomVO getCustom(@RequestParam int userNo) {
		CardcustomVO card = cusRepo.findByUserNo(userNo);
		System.out.println(card);
		return card;
	}

	// 출석일수---달 지정 안해줫다 내일하자
	@GetMapping(value = "/stampday.sam")
	public int UserStamp(@RequestParam int userNo) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dayFormat = new SimpleDateFormat("yy/MM/");
		String date = dayFormat.format(calendar.getTime());
		int stamp = dayRepo.findByUserAndDate(userNo, date); // 유저 번호와 연/월줘서 검색

		System.out.println(stamp);
		return stamp;
	}

	// 출석체크
	@PostMapping(value = "/stamp.sam")
	public String StampPlus(@RequestParam int userNo) {
		String msg = "";
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dayFormat = new SimpleDateFormat("yy/MM/dd");
		String date = dayFormat.format(calendar.getTime());
		if (dayRepo.findByDailyDateContaining(date, userNo) == null) {
			UserVO user = userRepo.findById(userNo).get();
			DailyStampVO stamp = DailyStampVO.builder().user(user).build();
			dayRepo.save(stamp);

			int pointIncome = 50;
			user = userRepo.findById(userNo).get();
			CardVO card = cardRepo.findByUser(user);

			int current = card.getPointBalance();
			card.setPointBalance(current + pointIncome);
			CardVO savedCard = cardRepo.save(card);

			PointVO point = PointVO.builder().pointSave(pointIncome).pointHistory(savedCard.getPointBalance())
					.pointMemo("출석체크").card(savedCard).build();
			pointRepo.save(point);

			msg = "성공";
		} else {
			msg = "이미 출석체크를 하셨습니다.";
		}

		return msg;
	}

	// 운동일수
	@GetMapping(value = "/workday.sam")
	public int UserLogin(@RequestParam int userNo) {
		int wcount = 0;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat monthFormat = new SimpleDateFormat("yy/MM");
		String date = monthFormat.format(calendar.getTime());
		wcount = workRepo.findByWorkDateContaining2(date, userNo);

		return wcount;
	}

}
