package com.samsam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.samsam.service.CardService;

@RestController
@RequestMapping("/home")
public class CardRestController {
	
	@Autowired
	CardService cardService;
	
	@GetMapping("/pay")
	public String barcodeScan(@RequestParam String storeNo) {
		return cardService.barcodeScan(storeNo);
	}
}
