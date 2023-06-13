package com.samsam.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsam.repository.CardRepository;
import com.samsam.repository.DepositRepository;
import com.samsam.repository.UserRepository;
import com.samsam.vo.CardVO;
import com.samsam.vo.DepositVO;
import com.samsam.vo.UserVO;

@Service
public class AccPoBalService {

	@Autowired
	UserRepository userRepo;
	@Autowired
	CardRepository cardRepo;
	@Autowired
	DepositRepository depoRepo;

	// 카드잔액, 포인트잔액 조회
	public CardVO searchBalance(String userNo) {
		int num = Integer.parseInt(userNo);

		UserVO user = userRepo.findById(num).get();
		CardVO card = cardRepo.findByUser(user);

		int accBal = card.getAccountBalance();
		int poBal = card.getPointBalance();

		CardVO result = new CardVO();
		result.setAccountBalance(accBal);
		result.setPointBalance(poBal);

		return result;
	}

	// 카드 잔액 충전
	public String chargeBalance(HashMap<String, String> map) {
		int num = Integer.parseInt(map.get("userNo"));

		UserVO user = userRepo.findById(num).orElse(null);
		if (user == null) {
			throw new IllegalArgumentException("유효하지 않은 사용자입니다.");
		}

		CardVO card = cardRepo.findByUser(user);
		if (card == null) {
			throw new IllegalArgumentException("사용자에게 할당된 카드가 없습니다.");
		}

		// 비밀번호 확인
		if (!card.getCardPass().toString().equals(map.get("cardPass"))) {
		    return "WRONG";
		}

		// 충전
		int chargeAmount = Integer.parseInt(map.get("chargeAmount"));
		int currentBalance = card.getAccountBalance();
		int newBalance = currentBalance + chargeAmount;
		card.setAccountBalance(newBalance);
		CardVO savedCard = cardRepo.save(card);

		// 입금 히스토리 생성
		DepositVO deposit = DepositVO.builder()
				.depositCash(chargeAmount)
				.depositHistory(savedCard.getAccountBalance())
				.card(savedCard)
				.build();
		depoRepo.save(deposit);

		return "OK";
	}

}
