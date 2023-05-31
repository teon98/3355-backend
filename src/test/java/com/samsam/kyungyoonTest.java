package com.samsam;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.samsam.repository.CardRepository;
import com.samsam.repository.DepositRepository;
import com.samsam.repository.PointRepository;
import com.samsam.repository.UserRepository;
import com.samsam.vo.CardVO;
import com.samsam.vo.DepositVO;
import com.samsam.vo.PointVO;
import com.samsam.vo.UserVO;

@SpringBootTest
public class kyungyoonTest {

	@Autowired
	CardRepository cardRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	DepositRepository depoRepo;
	@Autowired
	PointRepository pointRepo;

	@Test
	void test4() {
		// 포인트 지급: 포인트 잔액에 적립되고 포인트 테이블에 추가
		int income = 80;

		CardVO card = cardRepo.findById(1853).get();
		int current = card.getPointBalance();
		card.setPointBalance(current + income);
		CardVO savedCard = cardRepo.save(card);

		PointVO point = PointVO.builder()
				.pointSave(income)
				.pointMemo("5/31 출석체크")
				.card(savedCard)
				.build();
		pointRepo.save(point);
	}

//	@Test
	void test3() {
		// 충전: 입금 발생시, 계좌 잔액에 입금되고 입금 테이블에 추가
		int income = 10000;

		CardVO card = cardRepo.findById(1853).get();
		int current = card.getAccountBalance();
		card.setAccountBalance(current + income);
		CardVO savedCard = cardRepo.save(card);

		DepositVO depo = DepositVO.builder()
				.depositCash(income)
				.card(savedCard)
				.build();
		depoRepo.save(depo);

	}

//	@Test
	void test2() {
		// 카드 시퀀스(1000~9999)로 카드 번호 생성
		String rst = "3355";

		CardVO card = cardRepo.findById(1853).get();
		rst += "-" + card.getCardSeq();

		Random random = new Random();
		rst += "-" + (random.nextInt(9000) + 1000);
		rst += "-" + (random.nextInt(9000) + 1000);

		card.setCardCode(rst);
		cardRepo.save(card);
	}

//	@Test
	void test() {
		// 카드 생성, 카드 번호 없음... 시퀀스가 생성되고 나서 따로 카드 번호를 만들어줘야함.
		CardVO card1 = CardVO.builder()
				.cardPass(1234)
				.accountBalance(30000)
				.pointBalance(100)
				.build();
		CardVO savedCard1 = cardRepo.save(card1);

		UserVO user = userRepo.findById(1).get();

		savedCard1.setUser(user);
		cardRepo.save(savedCard1);
	}
}
