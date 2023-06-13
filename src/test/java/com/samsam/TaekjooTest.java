package com.samsam;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.samsam.repository.CardRepository;
import com.samsam.repository.CardcustomRepository;
import com.samsam.repository.DepositRepository;
import com.samsam.repository.PointRepository;
import com.samsam.repository.ProfileRepository;
import com.samsam.repository.StoreRepository;
import com.samsam.repository.UserRepository;
import com.samsam.repository.WithdrawRepository;
import com.samsam.vo.CardVO;
import com.samsam.vo.CardcustomVO;
import com.samsam.vo.DepositVO;
import com.samsam.vo.PointVO;
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
	
	// @Test
	void test1() {
		// 가게 정보 입력해두기
		StoreVO store = StoreVO.builder()
				.storeNo(10041000)
				.storeName("택주네 헬스장")
				.build();
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
		
		PointVO point = PointVO.builder()
				.pointSave(pointspend * -1)
				.pointMemo(store.getStoreName() + " 결제")
				.pointHistory(savedCard.getPointBalance())
				.card(savedCard)
				.build();
		pointRepo.save(point);
		
		WithdrawVO with = WithdrawVO.builder()
				.withdrawCash(spend)
				.withdrawPoint(pointspend)
				.withdrawHistory(savedCard.getAccountBalance())
				.card(savedCard)
				.store(store)
				.build();
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
		
		PointVO point = PointVO.builder()
				.pointSave(incomePoint)
				.pointHistory(savedCard.getPointBalance())
				.pointMemo("23/06/01 출석체크")
				.card(savedCard)
				.build();
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

		DepositVO depo = DepositVO.builder()
				.depositCash(income)
				.depositHistory(savedCard.getAccountBalance())
				.card(savedCard)
				.build();
		depoRepo.save(depo);
	}
	
//	@Test
	void test5() {
		// 카드 커스텀 insert
		UserVO user = userRepo.findById(1).get();
		CardVO card = cardRepo.findByUser(user);
		
		CardcustomVO cardCus = CardcustomVO.builder()
				.card(card)
				.build();
		
		cardcusRepo.save(cardCus);
	}
	
	//@Test
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
		
		PointVO pointMinus = PointVO.builder()
				.pointSave(pointspend * -1)
				.pointMemo(store.getStoreName() + " 결제")
				.pointHistory(savedCard.getPointBalance())
				.card(savedCard)
				.build();
		pointRepo.save(pointMinus);
		
		WithdrawVO with = WithdrawVO.builder()
				.withdrawCash(spend)
				.withdrawPoint(pointspend)
				.withdrawHistory(savedCard.getAccountBalance())
				.card(savedCard)
				.store(store)
				.build();
		wdRepo.save(with);
		
		card = cardRepo.findByUser(user);
		card.setPointBalance(card.getPointBalance() + (int)(spend * ratio));
		CardVO savedCard2 = cardRepo.save(card);
		
		PointVO pointPlus = PointVO.builder()
				.pointSave((int)(spend * ratio))
				.pointMemo(store.getStoreName() + " 적립")
				.pointHistory(savedCard2.getPointBalance())
				.card(savedCard2)
				.build();
		pointRepo.save(pointPlus);
		
	}
	
	//@Test
	void test7() {
		int userNo = 110;

		UserVO user = userRepo.findById(userNo).get();
		CardVO card = cardRepo.findByUser(user);
		
		int AccBal =card.getAccountBalance();
		int PoBal = card.getPointBalance();
		System.out.println(AccBal);
		System.out.println(PoBal);
	}
	
	@Test
	void test8(){
		UserVO user = userRepo.findById(110).get();
		CardVO card = cardRepo.findByUser(user);
//		WithdrawVO with = wdRepo.fin
	}
	
	

}
