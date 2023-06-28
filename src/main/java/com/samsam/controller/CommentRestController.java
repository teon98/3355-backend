package com.samsam.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samsam.service.CommentService;
import com.samsam.vo.CommentVO;

@RestController
@RequestMapping("/comment")
public class CommentRestController {
	
	@Autowired
	CommentService commservice;

	@GetMapping(value="/allComments")
	public List<Object> commentList(int postNo) {
		System.out.println(postNo);
		return commservice.commentList(postNo);
	}
}
