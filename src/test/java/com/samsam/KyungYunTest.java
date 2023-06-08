package com.samsam;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.samsam.repository.CardRepository;
import com.samsam.repository.CardcustomRepository;
import com.samsam.repository.DailyStampRepository;
import com.samsam.repository.DepositRepository;
import com.samsam.repository.FollowRepository;
import com.samsam.repository.PointRepository;
import com.samsam.repository.ProfileRepository;
import com.samsam.repository.StoreRepository;
import com.samsam.repository.UserRepository;
import com.samsam.repository.WithdrawRepository;
import com.samsam.repository.WorkRepository;
import com.samsam.vo.CardVO;
import com.samsam.vo.CardcustomVO;
import com.samsam.vo.DepositVO;
import com.samsam.vo.FollowId;
import com.samsam.vo.FollowVO;
import com.samsam.vo.PointVO;
import com.samsam.vo.ProfileVO;
import com.samsam.vo.StoreVO;
import com.samsam.vo.UserLevelRole;
import com.samsam.vo.UserVO;
import com.samsam.vo.WithdrawVO;

@SpringBootTest
public class KyungYunTest {

	@Autowired
	CardRepository cardRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	DepositRepository depoRepo;
	@Autowired
	PointRepository pointRepo;
	@Autowired
	ProfileRepository profRepo;
	@Autowired
	WorkRepository workRepo;
	@Autowired
	DailyStampRepository dailyRepo;
	@Autowired
	FollowRepository followRepo;
	@Autowired
	StoreRepository storeRepo;
	@Autowired
	WithdrawRepository wdRepo;
	@Autowired
	CardcustomRepository cardcusRepo;
	
	// 팔로우 테이블 추가
//	@Test
	void follow() {
		UserVO user1 = userRepo.findById(2).get();
		UserVO user2 = userRepo.findById(3).get();
		
		FollowId fid = FollowId.builder()
				.followStart(user1)
				.followEnd(user2)
				.build();
		
		FollowVO follow = FollowVO.builder()
				.follow(fid)
				.build();
		
		followRepo.save(follow);
	}
	
	// 회원 탈퇴(Delete)
	@Test
	void deleteUser() {
		userRepo.deleteById(4);
	}
	
	// 카드 커스텀 insert
//	@Test
	void cardCustomInsert() {
		UserVO user = userRepo.findById(4).get();
		CardVO card = cardRepo.findByUser(user);
		
		CardcustomVO cardCus = CardcustomVO.builder()
				.card(card)
				.customColor("orange")
				.customLettering("우하하")
				.build();
		
		cardcusRepo.save(cardCus);
	}
	
	// 결제
//	@Test
	void pay() {
		// 결제 시, 포인트 사용할 수 있음.
		// 결제 시, 등급별로 차등 적립률 적용하여 포인트 적립
		UserVO user = userRepo.findById(4).get();
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
		StoreVO store = storeRepo.findById(88883001).get();
		
		int amount = 5000;
		int pointspend = 50;
		int spend = amount - pointspend;
		
		int current = card.getAccountBalance();
		int currentPoint = card.getPointBalance();
		card.setAccountBalance(current - spend);
		card.setPointBalance(currentPoint - pointspend);
		CardVO savedCard = cardRepo.save(card);
		
		if(pointspend != 0) {
			PointVO pointMinus = PointVO.builder()
					.pointSave(pointspend * -1)
					.pointMemo(store.getStoreName() + " 결제")
					.pointHistory(savedCard.getPointBalance())
					.card(savedCard)
					.build();
			pointRepo.save(pointMinus);
		}
		
		WithdrawVO withdraw = WithdrawVO.builder()
				.withdrawCash(spend)
				.withdrawPoint(pointspend)
				.withdrawHistory(savedCard.getAccountBalance())
				.card(savedCard)
				.store(store)
				.build();
		wdRepo.save(withdraw);
		
		if(spend != 0) {
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
	}
	
	// 가게 정보 입력해두기
//	@Test
	void insertStore() {
		StoreVO store = StoreVO.builder()
				.storeNo(88883001)
				.storeName("택주네 헬스장")
				.build();
		storeRepo.save(store);
	}
	
	// 포인트 지급: 포인트 잔액에 추가되고 포인트 테이블 내역에 추가
//	@Test
	void insertPoint() {
		int income = 80;

		UserVO user = userRepo.findById(4).get();
		CardVO card = cardRepo.findByUser(user);
		
		int current = card.getPointBalance();
		card.setPointBalance(current + income);
		CardVO savedCard = cardRepo.save(card);

		PointVO point = PointVO.builder()
				.pointSave(income)
				.pointHistory(savedCard.getPointBalance())
				.pointMemo("23/06/07 출석체크")
				.card(savedCard)
				.build();
		pointRepo.save(point);
	}

	// 충전: 입금 발생시, 계좌 잔액에 추가되고 입금 테이블 내역에 추가
//	@Test
	void insertMoney() {
		int income = 10000;

		UserVO user = userRepo.findById(4).get();
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

	// 프로필 생성
//	@Test
	void insertProfile() {
		ProfileVO profile = ProfileVO.builder()
				.profileAbout("나 갱윤쓰이올시다")
				.profileImg("무야호.jpg")
				.build();
		ProfileVO savedProfile = profRepo.save(profile);
		
		UserVO user = userRepo.findById(4).get();
		savedProfile.setProfileLevel(UserLevelRole.BRONZE4);
		savedProfile.setUser(user);
		profRepo.save(savedProfile);
	}
	
	// 카드 생성
//	@Test
	void insertCard() {
		// 카드 번호 없음.. 시퀀스가 생성되고 나서 따로 카드 번호를 만들어줘야함.
		UserVO user = userRepo.findById(4).get();
		int password = 1234;
		
		CardVO card = CardVO.builder()
				.cardPass(password)
				.build();
		CardVO savedCard = cardRepo.save(card);

		savedCard.setUser(user);
		
		// 카드 시퀀스(1000~9999)로 카드 번호 생성
		String rst = "3355";
		rst += "-" + savedCard.getCardSeq();

		Random random = new Random();
		rst += "-" + (random.nextInt(9000) + 1000);
		rst += "-" + (random.nextInt(9000) + 1000);

		savedCard.setCardCode(rst);
		cardRepo.save(savedCard);
	}

	// 유저 입력
//	@Test
	void insertUser() {
		UserVO u1 = UserVO.builder()
				.userNickname("경윤")
				.userEmail("kky@mail.com")
				.userPass("1234")
				.userBirth(970417)
				.userGender(1)
				.build();
		userRepo.save(u1);
	}
}
