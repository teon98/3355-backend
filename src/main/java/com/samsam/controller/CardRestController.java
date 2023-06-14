package com.samsam.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.samsam.service.CardService;
import com.samsam.vo.CardVO;

@RestController
@RequestMapping("/home")
public class CardRestController {

	@Autowired
	CardService cardService;

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
