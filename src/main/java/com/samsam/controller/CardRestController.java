package com.samsam.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.samsam.service.CardService;
import com.samsam.vo.AlarmVO;
import com.samsam.vo.CardVO;

@RestController
@RequestMapping("/home")
public class CardRestController {

	@Autowired
	CardService cardService;
	
	// 알림 배지 갯수
	@GetMapping("/alarm-count")
	public int getCountAlarm(String userNo) {
		return cardService.getCountAlarm(userNo);
	}

	// 결제 상세 내역에서 단 건 상세 보기 (영수증)
	@GetMapping("/history/detail")
	public HashMap<String, String> selectWithdrawDetail(String withdrawNo) {
		return cardService.selectWithdrawDetail(withdrawNo);
	}
	
	// 카드번호와 사용자 별명 가져오기
	@GetMapping("/cardCodeNick")
	public HashMap<String, String> getCardCodeNick(String userNo) {
		return cardService.getCardCodeNick(userNo);
	}

	// 알림 단건 읽음 처리
	@PutMapping("/readSingle")
	public String singleReadAlarm(String alarmNo) {
		return cardService.singleReadAlarm(alarmNo);
	}

	// 알림 전체 읽음 처리
	@PutMapping("/readAll")
	public String allReadAlarm(String userNo) {
		return cardService.allReadAlarm(userNo);
	}


	// 읽지 않은 알림 전체 + 읽은 알림 5건 조회
	@GetMapping("/alarm")
	public List<AlarmVO> getAlarm(String userNo) {
		return cardService.getAlarm(userNo);
	}

	// 카드 잔액 충전
	@PostMapping("/charge")
	public String chargeBalance(@RequestBody HashMap<String, String> map) {
		return cardService.chargeBalance(map);
	}

	// 카드 입출금 내역
	@GetMapping("/history/{userNo}")
	public List<Object> getWithdrawDepositHistory(@PathVariable String userNo) {
		return cardService.getWithdrawDepositHistory(userNo);
	}

	// 포인트 내역
	@GetMapping("/pthistory/{userNo}")
	public List<Object> getPointHistory(@PathVariable String userNo) {
		return cardService.getPointHistory(userNo);
	}

	// 결제 정보 불러오기 (결제 완료 후, 영수증처럼 보려고)
	@GetMapping("/pay/complete")
	public HashMap<String, String> selectWithdraws(@RequestParam String userNo) {
		return cardService.selectWithdraws(userNo);
	}

	// 카드 잔액, 포인트 잔액 조회
	@GetMapping(value = { "/pay", "" })
	public CardVO readBalance(@RequestParam String userNo) {
		return cardService.readBalance(userNo);
	}

	// 결제
	@PostMapping(value = "/pay", consumes = "application/json")
	public String pay(@RequestBody HashMap<String, String> obj) {
		return cardService.pay(obj);
	}

	// 바코드 읽은 정보로 store테이블에 store_no중에 존재하는지 체크
	@GetMapping("/barcode")
	public String storeExistCheck(@RequestParam String storeNo) {
		return cardService.storeExistCheck(storeNo);
	}
}
