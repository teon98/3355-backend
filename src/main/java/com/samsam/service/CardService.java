package com.samsam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsam.repository.CardRepository;
import com.samsam.repository.StoreRepository;
import com.samsam.vo.StoreVO;

@Service
public class CardService {

	@Autowired
	CardRepository cardRepo;
	@Autowired
	StoreRepository storeRepo;

	public String barcodeScan(String storeNo) {
		String storeName = null;
		int num = Integer.parseInt(storeNo);

		StoreVO store = storeRepo.findById(num).orElse(null);
		if (store != null)
			storeName = store.getStoreName();
		
		return storeName;
	}
	
}
