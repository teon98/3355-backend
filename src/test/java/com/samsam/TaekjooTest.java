package com.samsam;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.samsam.repository.AlarmRepository;
import com.samsam.repository.CardRepository;
import com.samsam.repository.CardcustomRepository;
import com.samsam.repository.CommRepository;
import com.samsam.repository.DepositRepository;
import com.samsam.repository.PointRepository;
import com.samsam.repository.PostRepository;
import com.samsam.repository.ProfileRepository;
import com.samsam.repository.StoreRepository;
import com.samsam.repository.UserRepository;
import com.samsam.repository.WithdrawRepository;
import com.samsam.vo.AlarmVO;
import com.samsam.vo.CardVO;
import com.samsam.vo.CardcustomVO;
import com.samsam.vo.CommentVO;
import com.samsam.vo.DepositVO;
import com.samsam.vo.PointVO;
import com.samsam.vo.PostVO;
import com.samsam.vo.ProfileVO;
import com.samsam.vo.StoreVO;
import com.samsam.vo.UserVO;
import com.samsam.vo.WithdrawVO;

@SpringBootTest
public class TaekjooTest {

	@Autowired
	WithdrawRepository wdRepo;
	@Autowired
	StoreRepository storeRepo;
	@Autowired
	CardRepository cardRepo;
	@Autowired
	PointRepository pointRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	DepositRepository depoRepo;
	@Autowired
	CardcustomRepository cardcusRepo;
	@Autowired
	ProfileRepository profRepo;
	@Autowired
	AlarmRepository alaRepo;
	@Autowired
	PostRepository postRepo;
	@Autowired
	CommRepository commRepo;
	

	@Test
//	void test15() {
//	    // 모든 Post 가져오기
//		List<PostVO> allPosts = postRepo.findAllByOrderByPostDateDesc();
//		
//		for(PostVO post : allPosts) {
//			
//		}	  
//	}

	//@Test
	void test13() {
	    // 댓글 다는 사용자 
	    int userNo = 3;
	    UserVO user = userRepo.findById(userNo).get();
	    
	    // 포스트 찾기
	    int postNo = 5;
	    PostVO post = postRepo.findById(postNo).get();
	    
	    // 사용자들이 댓글 달기
	    CommentVO comment = CommentVO.builder()
	            .commContent("댓글 내용")
	            .post(post)
	            .commuser(user)
	            .build();
	    commRepo.save(comment);
	}


	// @Test
	void test1() {
		// 가게 정보 입력해두기
		StoreVO store = StoreVO.builder().storeNo(10041000).storeName("택주네 헬스장").build();
		storeRepo.save(store);
	}

	// @Test
	void test2() {
		// 결제... test6에서 등급별로 포인트 지급하는거 처리함..
		UserVO user = userRepo.findById(1).get();
		CardVO card = cardRepo.findByUser(user);

		StoreVO store = storeRepo.findById(10041000).get();

		int amount = 5000;
		int pointspend = 10;
		int spend = amount - pointspend;

		int current = card.getAccountBalance();
		int currentPoint = card.getPointBalance();
		card.setAccountBalance(current - spend);
		card.setPointBalance(currentPoint - pointspend);
		CardVO savedCard = cardRepo.save(card);

		PointVO point = PointVO.builder().pointSave(pointspend * -1).pointMemo(store.getStoreName() + " 결제")
				.pointHistory(savedCard.getPointBalance()).card(savedCard).build();
		pointRepo.save(point);

		WithdrawVO with = WithdrawVO.builder().withdrawCash(spend).withdrawPoint(pointspend)
				.withdrawHistory(savedCard.getAccountBalance()).card(savedCard).store(store).build();
		wdRepo.save(with);
	}

	// @Test
	void test3() {
		// 포인트 지급
		int incomePoint = 30;

		UserVO user = userRepo.findById(1).get();
		CardVO card = cardRepo.findByUser(user);

		int current = card.getPointBalance();
		card.setPointBalance(current + incomePoint);
		CardVO savedCard = cardRepo.save(card);

		PointVO point = PointVO.builder().pointSave(incomePoint).pointHistory(savedCard.getPointBalance())
				.pointMemo("23/06/01 출석체크").card(savedCard).build();
		pointRepo.save(point);
	}

	// @Test
	void test4() {
		// 충전: 입금 발생시, 계좌 잔액에 입금되고 입금 테이블에 추가
		int income = 10000;

		UserVO user = userRepo.findById(1).get();
		CardVO card = cardRepo.findByUser(user);

		int current = card.getAccountBalance();
		card.setAccountBalance(current + income);
		CardVO savedCard = cardRepo.save(card);

		DepositVO depo = DepositVO.builder().depositCash(income).depositHistory(savedCard.getAccountBalance())
				.card(savedCard).build();
		depoRepo.save(depo);
	}

	// @Test
	void test5() {
		// 카드 커스텀 insert
		UserVO user = userRepo.findById(1).get();
		CardVO card = cardRepo.findByUser(user);

		CardcustomVO cardCus = CardcustomVO.builder()
//				.card(card)
				.build();

		cardcusRepo.save(cardCus);
	}

	// @Test
	void test6() {
		// 결제 시, 포인트 사용할 수 있음.
		// 결제 시, 등급별로 차등 적립률 적용하여 포인트 지급
		UserVO user = userRepo.findById(1).get();
		ProfileVO profile = profRepo.findByUser(user);
		String level = profile.getProfileLevel().toString();
		double ratio = 0.0;

		switch (level.charAt(0)) {
		case 'B':
			ratio = 0.05;
			break;
		case 'S':
			ratio = 0.1;
			break;
		case 'G':
			ratio = 0.15;
			break;
		case 'P':
			ratio = 0.2;
			break;
		}

		CardVO card = cardRepo.findByUser(user);
		StoreVO store = storeRepo.findById(10041000).get();

		int amount = 5000;
		int pointspend = 500;
		int spend = amount - pointspend;

		int current = card.getAccountBalance();
		int currentPoint = card.getPointBalance();
		card.setAccountBalance(current - spend);
		card.setPointBalance(currentPoint - pointspend);
		CardVO savedCard = cardRepo.save(card);

		PointVO pointMinus = PointVO.builder().pointSave(pointspend * -1).pointMemo(store.getStoreName() + " 결제")
				.pointHistory(savedCard.getPointBalance()).card(savedCard).build();
		pointRepo.save(pointMinus);

		WithdrawVO with = WithdrawVO.builder().withdrawCash(spend).withdrawPoint(pointspend)
				.withdrawHistory(savedCard.getAccountBalance()).card(savedCard).store(store).build();
		wdRepo.save(with);

		card = cardRepo.findByUser(user);
		card.setPointBalance(card.getPointBalance() + (int) (spend * ratio));
		CardVO savedCard2 = cardRepo.save(card);

		PointVO pointPlus = PointVO.builder().pointSave((int) (spend * ratio)).pointMemo(store.getStoreName() + " 적립")
				.pointHistory(savedCard2.getPointBalance()).card(savedCard2).build();
		pointRepo.save(pointPlus);

	}

	// @Test
	void test7() {
		int userNo = 110;

		UserVO user = userRepo.findById(userNo).get();
		CardVO card = cardRepo.findByUser(user);

		int AccBal = card.getAccountBalance();
		int PoBal = card.getPointBalance();
		System.out.println(AccBal);
		System.out.println(PoBal);
	}

	// 입출금 내역 뽑아오기~
	// @Test
	void test8() {
		UserVO user = userRepo.findById(110).get();
		CardVO card = cardRepo.findByUser(user);
		List<WithdrawVO> wdList = wdRepo.findByCardOrderByWithdrawDateDesc(card);
		List<DepositVO> dpList = depoRepo.findByCardOrderByDepositDateDesc(card);

		// 출금 내역, 출금 누적, 출금 날짜 가져오기
		for (WithdrawVO withdraw : wdList) {
			Integer wdCash = withdraw.getWithdrawCash();
			Integer wdHistory = withdraw.getWithdrawHistory();
			Timestamp wdDate = withdraw.getWithdrawDate();
			System.out.println("출금 내역: " + wdCash);
			System.out.println("출금 누적: " + wdHistory);
			System.out.println("출금 날짜: " + wdDate);
		}

		// 입금 내역, 입금 누적, 입금 날짜 가져오기
		for (DepositVO deposit : dpList) {
			Integer dpCash = deposit.getDepositCash();
			Integer dpHistory = deposit.getDepositHistory();
			Timestamp dpDate = deposit.getDepositDate();
			System.out.println("입금 내역: " + dpCash);
			System.out.println("입금 누적: " + dpHistory);
			System.out.println("입금 날짜: " + dpDate);
		}
	}

	// @Test
	void test9() {
		UserVO user = userRepo.findById(110).get();
		CardVO card = cardRepo.findByUser(user);

		List<PointVO> pointList = pointRepo.findByCardOrderByPointDateDesc(card);

		List<Object> pointHistory = new ArrayList<>();

		for (PointVO point : pointList) {
			Integer ptSave = point.getPointSave();
			Integer ptHistory = point.getPointHistory();
			String ptMemo = point.getPointMemo();
			Timestamp ptDate = point.getPointDate();
			System.out.println(ptSave);
			System.out.println(ptHistory);
			System.out.println(ptMemo);
			System.out.println(ptDate);
		}
	}

	// @Test
	void test10() {
		int userNum = 3;
		List<AlarmVO> unreadList = alaRepo.findUnreadAlarms(userNum, 0);

		for (AlarmVO al : unreadList) {
			al.setAlarmStatus(true);
		}
		alaRepo.saveAll(unreadList);
	}

	// @Test
	void test11() {
		int id = 1031;
		AlarmVO alarm = alaRepo.findById(id).orElse(null);

		if (alarm != null) {
			alarm.setAlarmStatus(true);
		}

		alaRepo.save(alarm);
	}

	// @Test
	void test12() {
		int userNum = 3;
		UserVO user = userRepo.findById(userNum).get();
		CardVO card = cardRepo.findByUser(user);

		HashMap<String, String> map = new HashMap<>();

		String userNick = user.getUserNickname();
		String cardCode = card.getCardCode();

		map.put("userNick", userNick);
		map.put("cardCode", cardCode);

		System.out.println(map);
	}

}
