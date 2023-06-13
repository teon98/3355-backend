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

import com.samsam.service.AccPoBalService;
import com.samsam.vo.CardVO;

@RestController
@RequestMapping("/home")
public class CardRestController2 {

	@Autowired
	AccPoBalService AccService;

	// 카드잔액, 포인트잔액 조회
	@GetMapping
	public CardVO searchBalance(@RequestParam String userNo) {
		return AccService.searchBalance(userNo);
	}

	// 카드 잔액 충전
	@PostMapping("/charge")
	public String chargeBalance(@RequestBody HashMap<String, String> map) {
	    return AccService.chargeBalance(map);
	}
	
	// 카드 입출금 내역
	@GetMapping("/history/{userNo}")
	public List<String> getWithdrawDepositHistory(@PathVariable String userNo) {
		return AccService.getWithdrawDepositHistory(userNo);
	}

}
