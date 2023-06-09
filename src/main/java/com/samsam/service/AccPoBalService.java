package com.samsam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsam.repository.CardRepository;
import com.samsam.repository.UserRepository;
import com.samsam.vo.CardVO;
import com.samsam.vo.UserVO;

@Service
public class AccPoBalService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	CardRepository cardRepo;
	
	public CardVO SearchBalance(String userNo) {
		int num = Integer.parseInt(userNo);
		
		UserVO user = userRepo.findById(num).get();
		CardVO card = cardRepo.findByUser(user);
		
		int AccBal =card.getAccountBalance();
		int PoBal = card.getPointBalance();
		
		CardVO result = new CardVO();
		result.setAccountBalance(AccBal);
		result.setPointBalance(PoBal);
		
		return result;
	}
}
