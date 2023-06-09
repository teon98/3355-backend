package com.samsam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.samsam.service.AccPoBalService;
import com.samsam.vo.CardVO;

@RestController
//@RequestMapping("/home")
public class CardRestController2 {
	
	@Autowired
	AccPoBalService AccService;
	
	@GetMapping("/home")
	public CardVO SearchBalance(@RequestParam String userNo) {
		return AccService.SearchBalance(userNo);
	}
	
	
}
