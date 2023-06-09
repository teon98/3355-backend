package com.samsam.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@GetMapping("/pay")
	public CardVO readBalance(@RequestParam String userNo) {
		return cardService.readBalance(userNo);
	}
	
	@PostMapping(value = "/pay", consumes = "application/json")
	public String pay(@RequestBody HashMap<String, String> obj) {
		for (String key : obj.keySet()) {
			System.out.println(key + " : " + obj.get(key));
		}
		return cardService.pay(obj);
	}
	
	@GetMapping("/barcode")
	public String storeExistCheck(@RequestParam String storeNo) {
		return cardService.storeExistCheck(storeNo);
	}
}
