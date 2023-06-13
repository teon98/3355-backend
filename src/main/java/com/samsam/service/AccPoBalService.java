package com.samsam.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsam.repository.CardRepository;
import com.samsam.repository.DepositRepository;
import com.samsam.repository.StoreRepository;
import com.samsam.repository.UserRepository;
import com.samsam.repository.WithdrawRepository;
import com.samsam.vo.CardVO;
import com.samsam.vo.DepositVO;
import com.samsam.vo.TransactionVO;
import com.samsam.vo.UserVO;
import com.samsam.vo.WithdrawVO;

@Service
public class AccPoBalService {

	@Autowired
	UserRepository userRepo;
	@Autowired
	CardRepository cardRepo;
	@Autowired
	DepositRepository dpRepo;
	@Autowired
	WithdrawRepository wdRepo;
	@Autowired
	StoreRepository storeRepo;

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
		DepositVO deposit = DepositVO.builder().depositCash(chargeAmount).depositHistory(savedCard.getAccountBalance())
				.card(savedCard).build();
		dpRepo.save(deposit);

		return "OK";
	}

	// 입출금 내역서
	public List<String> getWithdrawDepositHistory(String userNo) {
		int num = Integer.parseInt(userNo);
		UserVO user = userRepo.findById(num).orElse(null);
		CardVO card = cardRepo.findByUser(user);

		String cardCode = card.getCardCode(); // 카드번호

		List<WithdrawVO> withdrawList = wdRepo.findByCardOrderByWithdrawDateDesc(card);
		List<DepositVO> depositList = dpRepo.findByCardOrderByDepositDateDesc(card);

		List<TransactionVO> transactionList = new ArrayList<>();

		// 출금 내역 + 입금 내역을 합쳐서 날짜별로 내림차순하기위해 TransactionVO로 변환하여 통합
		for (WithdrawVO withdraw : withdrawList) {
			TransactionVO transaction = new TransactionVO();
			transaction.setAmount(withdraw.getWithdrawCash());
			transaction.setAmountHistory(withdraw.getWithdrawHistory());
			transaction.setDate(withdraw.getWithdrawDate());
			transaction.setType("출금");
			transaction.setStoreName(withdraw.getStore() != null ? withdraw.getStore().getStoreName() : null);
			transactionList.add(transaction);
		}

		for (DepositVO deposit : depositList) {
			TransactionVO transaction = new TransactionVO();
			transaction.setAmount(deposit.getDepositCash());
			transaction.setAmountHistory(deposit.getDepositHistory());
			transaction.setDate(deposit.getDepositDate());
			transaction.setType("입금");
			transactionList.add(transaction);
		}

		// 통합된 리스트를 날짜를 기준으로 내림차순 정렬
		Collections.sort(transactionList, new Comparator<TransactionVO>() {
			public int compare(TransactionVO t1, TransactionVO t2) {
				return t2.getDate().compareTo(t1.getDate());
			}
		});

		List<String> historyList = new ArrayList<>();

		// 카드번호 추가
		historyList.add("카드 번호: " + cardCode);

		// 정렬된 내역 출력
		for (TransactionVO transaction : transactionList) {
			Integer amount = transaction.getAmount();
			Integer amountHistory = transaction.getAmountHistory();
			Timestamp date = transaction.getDate();
			String type = transaction.getType();
			String storeName = transaction.getStoreName();
			
			if (amountHistory != null) {
				if (type.equals("출금")) {
					historyList.add("출금 누적: " + amountHistory);
				} else if (type.equals("입금")) {
					historyList.add("입금 누적: " + amountHistory);
				}
			}
			if (amount != null) {
				if (type.equals("출금")) {
					historyList.add("출금 금액: " + amount);
				} else if (type.equals("입금")) {
					historyList.add("입금 금액: " + amount);
				}
			}
			if (date != null) {
				historyList.add("날짜: " + date);
			}
			if (storeName != null) {
				historyList.add("매장명: " + storeName);
			}
		}

		return historyList;
	}

} // end class
