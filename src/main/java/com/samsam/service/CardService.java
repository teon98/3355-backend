package com.samsam.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samsam.repository.AlarmRepository;
import com.samsam.repository.CardRepository;
import com.samsam.repository.DepositRepository;
import com.samsam.repository.PointRepository;
import com.samsam.repository.PostRepository;
import com.samsam.repository.ProfileRepository;
import com.samsam.repository.StoreRepository;
import com.samsam.repository.UserRepository;
import com.samsam.repository.WithdrawRepository;
import com.samsam.vo.AlarmVO;
import com.samsam.vo.CardVO;
import com.samsam.vo.DepositVO;
import com.samsam.vo.PointVO;
import com.samsam.vo.ProfileVO;
import com.samsam.vo.StoreVO;
import com.samsam.vo.TransactionVO;
import com.samsam.vo.UserVO;
import com.samsam.vo.WithdrawVO;

@Service
@Transactional
public class CardService {

	@Autowired
	UserRepository userRepo;
	@Autowired
	ProfileRepository profRepo;
	@Autowired
	CardRepository cardRepo;
	@Autowired
	PointRepository pointRepo;
	@Autowired
	StoreRepository storeRepo;
	@Autowired
	WithdrawRepository wdRepo;
	@Autowired
	DepositRepository dpRepo;
	@Autowired
	AlarmRepository alaRepo;

	// 카드번호와 사용자 별명 가져오기
	public HashMap<String, String> getCardCodeNick(String userNo){
		int userNum = Integer.parseInt(userNo);
		UserVO user = userRepo.findById(userNum).get();
		CardVO card = cardRepo.findByUser(user);
		
		HashMap<String, String> map = new HashMap<>();
		
		String userNick = user.getUserNickname();
		String cardCode = card.getCardCode();
		
		map.put("userNick", userNick);
		map.put("cardCode", cardCode);
		
		return map;
	}
	
	// 알림 단건 읽음 처리
	public String singleReadAlarm(String alarmNo) {
		int alarmNum = Integer.parseInt(alarmNo);
		AlarmVO alarm = alaRepo.findById(alarmNum).orElse(null);
		
		if(alarm != null) {
			alarm.setAlarmStatus(true);
		}
		alaRepo.save(alarm);
		
		return "OK";
	}
	
	// 알림 전체 읽음 처리
	public String allReadAlarm(String userNo){
		int userNum = Integer.parseInt(userNo);
		List<AlarmVO> unreadList = alaRepo.findUnreadAlarms(userNum, 0);
		
		for(AlarmVO al : unreadList) {
			al.setAlarmStatus(true);
		}
		alaRepo.saveAll(unreadList);
		
		return "OK";
	}

	// 결제 상세 내역에서 단 건 상세 보기 (영수증)
	public HashMap<String, String> selectWithdrawDetail(String withdrawNo) {
		HashMap<String, String> map = new HashMap<>();
		
		WithdrawVO wd = wdRepo.findById(Integer.parseInt(withdrawNo)).get();
		PointVO point = pointRepo.findByWithdrawNo(Integer.parseInt(withdrawNo));
		
		int spendMoney = wd.getWithdrawCash();
		int spendPoint = wd.getWithdrawPoint();

		map.put("storeName", wd.getStore().getStoreName());
		map.put("withdrawDate", wd.getWithdrawDate().toString());
		map.put("amount", (spendMoney + spendPoint) + "");
		map.put("withdrawCash", spendMoney + "");
		map.put("point", spendPoint + "");
		map.put("pointSave", point != null ? point.getPointSave() + "" : "0");
		map.put("levelRatio", point != null ? (Math.round((double) point.getPointSave() / (double) spendMoney * 100) / 100.0) + "" : "0");

		System.out.println(map);
		return map;
	}

	// 알림 배지 갯수
	public int getCountAlarm(String userNo) {
		return alaRepo.findUnreadCnt(Integer.parseInt(userNo), 0);
	}

	// 읽지 않은 알림 전체 + 읽은 알림 5건 조회
	public List<AlarmVO> getAlarm(String userNo) {
		int userNum = Integer.parseInt(userNo);
		List<AlarmVO> unreadList = alaRepo.findUnreadAlarms(userNum, 0);
		List<AlarmVO> readList = alaRepo.findReadAlarms(userNum, 1);

		List<AlarmVO> resultList = Stream.concat(unreadList.stream(), readList.stream()).collect(Collectors.toList());
		return resultList;
	}

	// 포인트 내역서
	public List<Object> getPointHistory(String userNo) {
		int num = Integer.parseInt(userNo);
		UserVO user = userRepo.findById(num).orElse(null);
		CardVO card = cardRepo.findByUser(user);

		List<PointVO> pointList = pointRepo.findByCardOrderByPointDateDesc(card);
		List<Object> pointHistory = new ArrayList<>();

		for (PointVO point : pointList) {
			TransactionVO transaction = TransactionVO.builder().type(point.getPointSave() < 0 ? "-" : "+")
					.storeName(point.getPointMemo())
					.amount(point.getPointSave() < 0 ? point.getPointSave() * -1 : point.getPointSave())
					.amountHistory(point.getPointHistory()).date(point.getPointDate().toString()
							.substring(2, point.getPointDate().toString().indexOf('.')).replaceAll("-", "/"))
					.build();
			pointHistory.add(transaction);
		}
		pointHistory.add(0, card.getCardCode()); // 카드번호

		return pointHistory;
	}

	// 카드 입출금 내역서
	public List<Object> getWithdrawDepositHistory(String userNo) {
		int num = Integer.parseInt(userNo);
		UserVO user = userRepo.findById(num).orElse(null);
		CardVO card = cardRepo.findByUser(user);

		List<WithdrawVO> withdrawList = wdRepo.findByCardOrderByWithdrawDateDesc(card);
		List<DepositVO> depositList = dpRepo.findByCardOrderByDepositDateDesc(card);

		List<Object> transactionList = new ArrayList<>();

		// 출금 내역 + 입금 내역을 합쳐서 날짜별로 내림차순하기위해 TransactionVO로 변환하여 통합
		for (WithdrawVO withdraw : withdrawList) {
			TransactionVO transaction = TransactionVO.builder()
					.no(withdraw.getWithdrawNo())
					.type("-").storeName(withdraw.getStore().getStoreName())
					.amount(withdraw.getWithdrawCash()).amountHistory(withdraw.getWithdrawHistory())
					.date(withdraw.getWithdrawDate().toString()
							.substring(2, withdraw.getWithdrawDate().toString().indexOf('.')).replaceAll("-", "/"))
					.build();
			transactionList.add(transaction);
		}

		for (DepositVO deposit : depositList) {
			TransactionVO transaction = TransactionVO.builder()
					.type("+").storeName("충전")
					.amount(deposit.getDepositCash()).amountHistory(deposit.getDepositHistory())
					.date(deposit.getDepositDate().toString()
							.substring(2, deposit.getDepositDate().toString().indexOf('.')).replaceAll("-", "/"))
					.build();
			transactionList.add(transaction);
		}

		// 통합된 리스트를 날짜를 기준으로 내림차순 정렬
		Collections.sort(transactionList, new Comparator<Object>() {
			public int compare(Object t1, Object t2) {
				return ((TransactionVO) t2).getDate().compareTo(((TransactionVO) t1).getDate());
			}
		});

		transactionList.add(0, card.getCardCode()); // 카드번호

		return transactionList;
	}

	// 카드 잔액 충전
	public String chargeBalance(HashMap<String, String> map) {
		int num = Integer.parseInt(map.get("userNo"));

		UserVO user = userRepo.findById(num).orElse(null);
		if (user == null)
			throw new IllegalArgumentException("유효하지 않은 사용자입니다.");

		CardVO card = cardRepo.findByUser(user);
		if (card == null)
			throw new IllegalArgumentException("사용자에게 할당된 카드가 없습니다.");

		// 비밀번호 확인
		if (!card.getCardPass().toString().equals(map.get("cardPass")))
			return "WRONG";

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

	// 결제 정보 불러오기 (결제 완료 후, 영수증)
	public HashMap<String, String> selectWithdraws(String userNo) {
		HashMap<String, String> map = new HashMap<>();

		UserVO user = userRepo.findById(Integer.parseInt(userNo)).get();
		CardVO card = cardRepo.findByUser(user);

		List<WithdrawVO> wdList = wdRepo.findByCardOrderByWithdrawDateDesc(card);
		WithdrawVO wd = wdList.get(0);
		int spendMoney = wd.getWithdrawCash();
		int spendPoint = wd.getWithdrawPoint();

		PointVO point = pointRepo.findByWithdrawNo(wd.getWithdrawNo());

		map.put("storeName", wd.getStore().getStoreName());
		map.put("withdrawDate", wd.getWithdrawDate().toString());
		map.put("amount", (spendMoney + spendPoint) + "");
		map.put("withdrawCash", spendMoney + "");
		map.put("point", spendPoint + "");
		map.put("pointSave", point != null ? point.getPointSave() + "" : "0");
		map.put("levelRatio", point != null ? (Math.round((double) point.getPointSave() / (double) spendMoney * 100) / 100.0) + "" : "0");

		return map;
	}

	// 카드 잔액, 포인트 잔액 조회
	public CardVO readBalance(String userNo) {
		int num = Integer.parseInt(userNo);

		UserVO user = userRepo.findById(num).get();
		CardVO card = cardRepo.findByUser(user);

		int accBal = card.getAccountBalance();
		int poBal = card.getPointBalance();

		CardVO result = CardVO.builder().accountBalance(accBal).pointBalance(poBal).build();

		return result;
	}

	// 결제
	public String pay(HashMap<String, String> obj) {
		// 결제 시, 포인트 사용할 수 있음.
		// 결제 시, 등급별로 차등 적립률 적용하여 포인트 적립
		String msg = "OK";
		int num = Integer.parseInt(obj.get("userNo"));

		UserVO user = userRepo.findById(num).get();
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
		
		// 포인트 사용
		if (pointspend != 0) {
			PointVO pointMinus = PointVO.builder()
					.pointSave(pointspend * -1)
					.pointMemo(store.getStoreName() + " 결제")
					.pointHistory(savedCard.getPointBalance())
					.card(savedCard)
					.build();
			pointRepo.save(pointMinus);
		}
		
		// 결제
		WithdrawVO withdraw = WithdrawVO.builder()
				.withdrawCash(spend)
				.withdrawPoint(pointspend)
				.withdrawHistory(savedCard.getAccountBalance())
				.card(savedCard)
				.store(store)
				.build();
		wdRepo.save(withdraw);
		
		List<WithdrawVO> wdList = wdRepo.findByCardOrderByWithdrawDateDesc(savedCard);
		
		// 결제 후 포인트 적립
		if (spend != 0) {
			card = cardRepo.findByUser(user);
			card.setPointBalance(card.getPointBalance() + (int) (spend * ratio));
			CardVO savedCard2 = cardRepo.save(card);

			PointVO pointPlus = PointVO.builder()
					.pointSave((int) (spend * ratio))
					.pointMemo(store.getStoreName() + " 적립")
					.pointHistory(savedCard2.getPointBalance())
					.card(savedCard2)
					.withdrawNo(wdList.get(0).getWithdrawNo())
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
