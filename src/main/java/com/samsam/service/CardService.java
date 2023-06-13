package com.samsam.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samsam.repository.CardRepository;
import com.samsam.repository.PointRepository;
import com.samsam.repository.ProfileRepository;
import com.samsam.repository.StoreRepository;
import com.samsam.repository.UserRepository;
import com.samsam.repository.WithdrawRepository;
import com.samsam.vo.CardVO;
import com.samsam.vo.PointVO;
import com.samsam.vo.ProfileVO;
import com.samsam.vo.StoreVO;
import com.samsam.vo.UserVO;
import com.samsam.vo.WithdrawVO;

@Service
@Transactional
public class CardService {

	@Autowired
	CardRepository cardRepo;
	@Autowired
	StoreRepository storeRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	ProfileRepository profRepo;
	@Autowired
	PointRepository pointRepo;
	@Autowired
	WithdrawRepository wdRepo;
	
	// 결제 정보 불러오기 (결제 완료 후, 영수증처럼 보려고)
	public HashMap<String, String> selectWithdraws(String userNo) {
		HashMap<String, String> map = new HashMap<>();
		
		int num = Integer.parseInt(userNo);
		UserVO user = userRepo.findById(num).get();
		CardVO card = cardRepo.findByUser(user);
		List<WithdrawVO> list = wdRepo.findByCardOrderByWithdrawDateDesc(card);
		WithdrawVO wd = list.get(0);
		int spendMoney = wd.getWithdrawCash();
		int spendPoint = wd.getWithdrawPoint();

		String level = user.getProfile().getProfileLevel().toString();
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
		
//		System.out.println("결제 가맹점 명: " + wd.getStore().getStoreName());
//		System.out.println("결제 일시: " + wd.getWithdrawDate());
//		System.out.println("실 결제 금액: " + wd.getWithdrawCash());
//		System.out.println("포인트 사용: " + wd.getWithdrawPoint());
//		System.out.println("주문 금액: " + (wd.getWithdrawCash() + wd.getWithdrawPoint()));
//		System.out.println("포인트 적립: " + (int) (wd.getWithdrawCash() * ratio));
		
		map.put("storeName", wd.getStore().getStoreName());
		map.put("withdrawDate", wd.getWithdrawDate().toString());
		map.put("withdrawCash", spendMoney + "");
		map.put("point", spendPoint + "");
		map.put("amount", (spendMoney + spendPoint) + "");
		map.put("pointSave", (int) (wd.getWithdrawCash() * ratio) + "");
		map.put("levelRatio", ratio + "");

		return map;
	}

	// 카드 잔액, 포인트 잔액 조회
	public CardVO readBalance(String userNo) {
		int num = Integer.parseInt(userNo);

		UserVO user = userRepo.findById(num).get();
		CardVO card = cardRepo.findByUser(user);

		int accBal = card.getAccountBalance();
		int poBal = card.getPointBalance();

		CardVO result = CardVO.builder()
				.accountBalance(accBal)
				.pointBalance(poBal)
				.build();

		return result;
	}

	// 결제
	public String pay(HashMap<String, String> obj) {
		// 결제 시, 포인트 사용할 수 있음.
		// 결제 시, 등급별로 차등 적립률 적용하여 포인트 적립
		String msg = "OK";

		int userNo = 110;

		UserVO user = userRepo.findById(userNo).get();
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

		int storeNo = Integer.parseInt(obj.get("storeNo"));

		CardVO card = cardRepo.findByUser(user);
		StoreVO store = storeRepo.findById(storeNo).get();

		int amount = Integer.parseInt(obj.get("amount"));
		int pointspend = Integer.parseInt(obj.get("point"));
		int spend = amount - pointspend;

		int current = card.getAccountBalance();
		int currentPoint = card.getPointBalance();
		card.setAccountBalance(current - spend);
		card.setPointBalance(currentPoint - pointspend);
		CardVO savedCard = cardRepo.save(card);

		if (pointspend != 0) {
			PointVO pointMinus = PointVO.builder().pointSave(pointspend * -1).pointMemo(store.getStoreName() + " 결제")
					.pointHistory(savedCard.getPointBalance()).card(savedCard).build();
			pointRepo.save(pointMinus);
		}

		WithdrawVO withdraw = WithdrawVO.builder().withdrawCash(spend).withdrawPoint(pointspend)
				.withdrawHistory(savedCard.getAccountBalance()).card(savedCard).store(store).build();
		wdRepo.save(withdraw);

		if (spend != 0) {
			card = cardRepo.findByUser(user);
			card.setPointBalance(card.getPointBalance() + (int) (spend * ratio));
			CardVO savedCard2 = cardRepo.save(card);

			PointVO pointPlus = PointVO.builder().pointSave((int) (spend * ratio))
					.pointMemo(store.getStoreName() + " 적립").pointHistory(savedCard2.getPointBalance()).card(savedCard2)
					.build();
			pointRepo.save(pointPlus);
		}

		return msg;
	}

	// 바코드 읽은 정보로 store테이블에 store_no중에 존재하는지 체크
	public String storeExistCheck(String storeNo) {
		String storeName = null;
		StoreVO store = null;

		try {
			store = storeRepo.findById(Integer.parseInt(storeNo)).orElse(null);
		} catch (Exception e) {
			return null;
		}
		if (store != null)
			storeName = store.getStoreName();

		return storeName;

	}

}
