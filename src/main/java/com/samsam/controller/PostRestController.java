package com.samsam.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.samsam.service.PostService;
import com.samsam.vo.CommentVO;

@RestController
@RequestMapping("/post")
public class PostRestController {

	@Autowired
	PostService postservice;

	@GetMapping(value="/followerPost")
	public int Post(@RequestParam  String userNickName){
		return postservice.Post(userNickName);
	}

	@PostMapping(value="/addcomments")
	public CommentVO addComment(int userNo, int postNo, String commContent) {
		return postservice.addComment(userNo, postNo, commContent);
	}
	
	@GetMapping(value="/my")
	public List<Object> myPost(@RequestParam int userNo){
		return postservice.myPost(userNo);
	}
	
	@GetMapping(value="/main")
	public List<Object> mainPost(@RequestParam int userNo){
		return postservice.mainPost(userNo);
	}
	
	
	@PostMapping(value="/newpost")
	public ResponseEntity<?> uploadPost(
			@RequestParam MultipartFile[] postImgs, 
			@RequestParam int userNo,
			@RequestParam String[] tagList) {
		
		int post_id = 0;
		try {
			post_id = postservice.uploadPost(postImgs, userNo, tagList);
		}catch (IOException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		return ResponseEntity.ok(post_id);
	}
}
