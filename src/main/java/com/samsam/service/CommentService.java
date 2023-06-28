package com.samsam.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsam.repository.CommRepository;
import com.samsam.repository.PostRepository;
import com.samsam.vo.CommentVO;
import com.samsam.vo.PostVO;

@Service
@Transactional
public class CommentService {
	
	@Autowired
	PostRepository postRepo;
	@Autowired
	CommRepository commRepo;
	
	public List<Object> commentList(int postNo) {
		List<Object> result = new ArrayList<>();
		
		PostVO post = postRepo.findById(postNo).get();
		List<CommentVO> comments= commRepo.findByPostOrderByCommNoDesc(post);
		
		for(CommentVO comment : comments) {
			HashMap<String, Object> comm = new HashMap<>();
			comm.put("commNo", comment.getCommNo());
			comm.put("commContent", comment.getCommContent());
			comm.put("commDate", comment.getCommDate());
			comm.put("commUser", comment.getCommuser().getUserNickname());
			result.add(comm);
		}
		
		return result;
	}

}
