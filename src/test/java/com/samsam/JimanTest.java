package com.samsam;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.samsam.repository.AlarmRepository;
import com.samsam.repository.CardcustomRepository;
import com.samsam.repository.DailyStampRepository;
import com.samsam.repository.FollowRepository;
import com.samsam.repository.ProfileRepository;
import com.samsam.repository.UserRepository;
import com.samsam.repository.WorkRepository;
import com.samsam.vo.AlarmVO;
import com.samsam.vo.CardcustomVO;
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
	
	
	@Autowired
	JavaMailSender javaMailSender;
	
	int wcount=0;
	
	
	@Test//카드커스텀생성
	void test14() {
		UserVO user = urepo.findById(12).get();
		CardcustomVO card = CardcustomVO.builder().user(user).customColor1("red").build();
		
	}
	
	//@Test//오늘날짜 찍어보기
	void test13() {
		int levelup=0;
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	     System.out.println("오늘의 날짜: " + timestamp);
	     
	     //오늘 날짜 뽑기
	     SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
	     String day =  dayFormat.format(timestamp);
	    	
	     
	     //오늘 날짜가 1일이면
	     if(day.equals("01")) {
//	    	//지난 달 구하기
//		     SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
//		     int month = Integer.parseInt(monthFormat.format(timestamp))-1;
	    	 
	    	 //지난 달 구하기
		   	  Calendar calendar = Calendar.getInstance();
	          calendar.add(Calendar.MONTH, -1); // 현재 날짜에서 1달 전으로 설정

	          SimpleDateFormat monthFormat = new SimpleDateFormat("yy/MM");
	          String lastMonth = monthFormat.format(calendar.getTime());
	          
		 
		     //지난 달에 운동한 날짜
		     wrepo.findByWorkDateContaining(lastMonth,1).forEach(w->{
		    	 wcount++;
		     });
		     
		     if(wcount > 20 ){
		    	 	levelup = 1;
		     }else if(wcount <10){
		    	  levelup = -1;
		     }
		     System.out.println(levelup);
		     //유저 등급 가져오기 
		     UserVO user =  urepo.findById(1).get();
		     ProfileVO pro = prepo.findByUser(user);
		     String l = pro.getProfileLevel()+"";
			
		   
			//유저 등급 설정
		     String level =l.substring(0,1);
			String lastLevel = l.substring(l.length()-1);
			System.out.println(level);
			System.out.println(lastLevel);
			
			switch (level) {
			case "B":
					switch (lastLevel) {
						case "1":
								if(levelup == 1) {
									ProfileVO prof = prepo.findByUser(user);
									prof.setProfileLevel(UserLevelRole.BRONZE2);
									prepo.save(prof);
								}
							break;
						case "2":
							if(levelup == 1) {
								ProfileVO prof = prepo.findByUser(user);
								prof.setProfileLevel(UserLevelRole.BRONZE3);
								prepo.save(prof);
							}else if(levelup == -1) {
								ProfileVO prof = prepo.findByUser(user);
								prof.setProfileLevel(UserLevelRole.BRONZE1);
								prepo.save(prof);
							}
							break;
						case "3":
							if(levelup == 1) {
								ProfileVO prof = prepo.findByUser(user);
								prof.setProfileLevel(UserLevelRole.BRONZE4);
								prepo.save(prof);
							}else if(levelup == -1) {
								ProfileVO prof = prepo.findByUser(user);
								prof.setProfileLevel(UserLevelRole.BRONZE2);
								prepo.save(prof);
							}
							break;
						case "4":
							if(levelup == 1) {
								ProfileVO prof = prepo.findByUser(user);
								prof.setProfileLevel(UserLevelRole.SILVER1);
								prepo.save(prof);
							}else if(levelup == -1) {
								ProfileVO prof = prepo.findByUser(user);
								prof.setProfileLevel(UserLevelRole.BRONZE3);
								prepo.save(prof);
							}
							break;
	
						default:
							break;
					}
				break;
			case "S":
				switch (lastLevel) {
				case "1":
						if(levelup == 1) {
							ProfileVO prof = prepo.findByUser(user);
							prof.setProfileLevel(UserLevelRole.SILVER2);
							prepo.save(prof);
						}else if(levelup == -1) {
							ProfileVO prof = prepo.findByUser(user);
							prof.setProfileLevel(UserLevelRole.BRONZE4);
							prepo.save(prof);
						}
					break;
				case "2":
					if(levelup == 1) {
						ProfileVO prof = prepo.findByUser(user);
						prof.setProfileLevel(UserLevelRole.SILVER3);
						prepo.save(prof);
					}else if(levelup == -1) {
						ProfileVO prof = prepo.findByUser(user);
						prof.setProfileLevel(UserLevelRole.SILVER1);
						prepo.save(prof);
					}
					break;
				case "3":
					if(levelup == 1) {
						ProfileVO prof = prepo.findByUser(user);
						prof.setProfileLevel(UserLevelRole.SILVER4);
						prepo.save(prof);
					}else if(levelup == -1) {
						ProfileVO prof = prepo.findByUser(user);
						prof.setProfileLevel(UserLevelRole.SILVER2);
						prepo.save(prof);
					}
					break;
				case "4":
					if(levelup == 1) {
						ProfileVO prof = prepo.findByUser(user);
						prof.setProfileLevel(UserLevelRole.GOLD1);
						prepo.save(prof);
					}else if(levelup == -1) {
						ProfileVO prof = prepo.findByUser(user);
						prof.setProfileLevel(UserLevelRole.SILVER3);
						prepo.save(prof);
					}
					break;

				default:
					break;
			}
				break;
			case "G":
				switch (lastLevel) {
				case "1":
						if(levelup == 1) {
							ProfileVO prof = prepo.findByUser(user);
							prof.setProfileLevel(UserLevelRole.GOLD2);
							prepo.save(prof);
						}else if(levelup == -1) {
							ProfileVO prof = prepo.findByUser(user);
							prof.setProfileLevel(UserLevelRole.SILVER4);
							prepo.save(prof);
						}
					break;
				case "2":
					if(levelup == 1) {
						ProfileVO prof = prepo.findByUser(user);
						prof.setProfileLevel(UserLevelRole.GOLD3);
						prepo.save(prof);
					}else if(levelup == -1) {
						ProfileVO prof = prepo.findByUser(user);
						prof.setProfileLevel(UserLevelRole.GOLD1);
						prepo.save(prof);
					}
					break;
				case "3":
					if(levelup == 1) {
						ProfileVO prof = prepo.findByUser(user);
						prof.setProfileLevel(UserLevelRole.GOLD4);
						prepo.save(prof);
					}else if(levelup == -1) {
						ProfileVO prof = prepo.findByUser(user);
						prof.setProfileLevel(UserLevelRole.GOLD2);
						prepo.save(prof);
					}
					break;
				case "4":
					if(levelup == 1) {
						ProfileVO prof = prepo.findByUser(user);
						prof.setProfileLevel(UserLevelRole.PLATINUM1);
						prepo.save(prof);
					}else if(levelup == -1) {
						ProfileVO prof = prepo.findByUser(user);
						prof.setProfileLevel(UserLevelRole.GOLD3);
						prepo.save(prof);
					}
					break;

				default:
					break;
				}

				break;
			case "P":
				switch (lastLevel) {
				case "1":
						if(levelup == 1) {
							ProfileVO prof = prepo.findByUser(user);
							prof.setProfileLevel(UserLevelRole.PLATINUM2);
							prepo.save(prof);
						}else if(levelup == -1) {
							ProfileVO prof = prepo.findByUser(user);
							prof.setProfileLevel(UserLevelRole.GOLD4);
							prepo.save(prof);
						}
					break;
				case "2":
					if(levelup == 1) {
						ProfileVO prof = prepo.findByUser(user);
						prof.setProfileLevel(UserLevelRole.PLATINUM3);
						prepo.save(prof);
					}else if(levelup == -1) {
						ProfileVO prof = prepo.findByUser(user);
						prof.setProfileLevel(UserLevelRole.PLATINUM1);
						prepo.save(prof);
					}
					break;
				case "3":
					if(levelup == 1) {
						ProfileVO prof = prepo.findByUser(user);
						prof.setProfileLevel(UserLevelRole.PLATINUM4);
						prepo.save(prof);
					}else if(levelup == -1) {
						ProfileVO prof = prepo.findByUser(user);
						prof.setProfileLevel(UserLevelRole.PLATINUM2);
						prepo.save(prof);
					}
					break;
				case "4":
						if(levelup == -1) {
						ProfileVO prof = prepo.findByUser(user);
						prof.setProfileLevel(UserLevelRole.PLATINUM3);
						prepo.save(prof);
					}
					break;

				default:
					break;
				}
				break;

			default:
				break;
			}
		
	    	 
	     }else {
	    	 //이번 달에 운동한 날짜
	    	 SimpleDateFormat dateFormat = new SimpleDateFormat("yy/MM");

		      String date = dateFormat.format(timestamp);
		      System.out.println(date);
		     wrepo.findByWorkDateContaining(date, 12).forEach(w->{
		    	 wcount++;
		     });
		     System.out.println("6월동안 운동한 날짜: "+wcount);
	     }
	     
	     
	     //유저 등급 가져오기 
	     UserVO user =  urepo.findById(1).get();
	     ProfileVO pro = prepo.findByUser(user);
	     String l = pro.getProfileLevel()+"";
	     System.out.println(l);
	}
	
	
	
	//@Test//이메일 보내기
	void test12() {
		emailSend("shinhan3355@gmail.com", "재밌는 제목", "재밌는 내용");
		
		
	}
	
	
	void emailSend(String toEmail,String subject,String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("shinhan3355@gmail.com");
		message.setTo(toEmail);
		message.setSubject(subject);
		message.setText(body);
		
		javaMailSender.send(message);
		
		System.out.println("성공");
		
	}
	
	//@Test//로그인테스트
	void test11() {
		int userNo=0;
		String userEmail = "ww";
		String userPass = "ww";
		if (urepo.findByUserEmailAndUserPass(userEmail,userPass) == null) {
			userNo = 0;
		} else {
			userNo = urepo.findByUserEmailAndUserPass(userEmail,userPass).getUserNo();
		}
		System.out.println(userNo);
	}
	
	//@Test//이메일로 유저 조회
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
			
			UserVO user = urepo.findById(12).get();
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
		
		UserVO user = urepo.findById(1).get();
		p2.setProfileLevel(UserLevelRole.GOLD1);
		p2.setUser(user);
		prepo.save(p2);
	}
	
	//@Test//유저 입력
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
}