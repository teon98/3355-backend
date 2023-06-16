package com.samsam.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.samsam.UploadFileUtils;

@RestController
public class UploadController {
	
	@PostMapping("/uploadImg")
	public String uploadImg(@RequestParam MultipartFile[] files, 
			@RequestParam String defaultpath, @RequestParam String path) throws IOException, Exception{
		String UPLOAD_PATH = defaultpath + File.separator + path;
		System.out.println(defaultpath);
		
		//upload_path 폴더 존재여부 판단
		File upload_path = new File(UPLOAD_PATH);
		if(!upload_path.exists()) {
			upload_path.mkdir();
		}
		
		//upload_path 폴더 생성
		String mdPath = UploadFileUtils.calcPath(UPLOAD_PATH);
		String fileName = null;
		
		try {
			for(MultipartFile file:files) {
				String originFileName = file.getOriginalFilename();
				System.out.println("원본파일이름: " + originFileName);
				if(originFileName != null && !originFileName.equals("")) {
					fileName = UploadFileUtils.fileUpload(UPLOAD_PATH, originFileName, file.getBytes(), mdPath);
				}else {
					fileName = File.separator + "images" + File.separator + "hide.png";
				}
				System.out.println(fileName);
			}
		} catch(IOException e) {
			return "이미지 업로드 에러";
		}
		
		return "이미지 업로드 성공";
	}
	
}
