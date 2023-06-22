package com.samsam.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.samsam.repository.CardcustomRepository;
import com.samsam.repository.DailyStampRepository;
import com.samsam.repository.ProfileRepository;
import com.samsam.repository.UserRepository;
import com.samsam.repository.WorkRepository;
import com.samsam.vo.CardcustomVO;
import com.samsam.vo.DailyStampVO;
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
	
	//커스텀 정보
	@GetMapping(value="/getcustom.sam")
	public CardcustomVO getCustom(int userNo) {
		CardcustomVO card = cusRepo.findByUserNo(userNo);
		System.out.println(card);
		return card;
	}
	
	// 출석일수---달 지정 안해줫다 내일하자
	@GetMapping(value = "/stampday.sam")
	public int UserStamp(@RequestParam int userNo) {
		int stamp = dayRepo.findByUser(userNo);
	
		System.out.println(stamp);
		return stamp;
	}
	
	//출석체크
	@PostMapping(value = "/stamp.sam")
	public String StampPlus(@RequestParam int userNo) {
		String msg = "";
		Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dayFormat = new SimpleDateFormat("yy/MM/dd");
        String date = dayFormat.format(calendar.getTime());
        if(dayRepo.findByDailyDateContaining(date,userNo)==null) {
        	UserVO user = userRepo.findById(userNo).get();
        	DailyStampVO stamp = DailyStampVO.builder().user(user).build();
        	dayRepo.save(stamp);
        	msg = "성공";
        }else {
        	msg="이미 출석체크를 하셧습니다.";
        }
		
		return msg;
	}

	// 운동일수
	@GetMapping(value = "/workday.sam")
	public int UserLogin(@RequestParam int userNo) {
		 int wcount=0;
		Calendar calendar = Calendar.getInstance();
        SimpleDateFormat monthFormat = new SimpleDateFormat("yy/MM");
        String date = monthFormat.format(calendar.getTime());
        wcount= workRepo.findByWorkDateContaining2(date,userNo);
        
		return wcount;
	}
	
	

}
