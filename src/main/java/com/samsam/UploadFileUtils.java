package com.samsam;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.util.FileCopyUtils;

//import net.coobird.thumbnailator.Thumbnails;

 
public class UploadFileUtils {
	// 썸네일 이미지 크기
	static final int THUMB_WIDTH = 300;
	static final int THUMB_HEIGHT = 300;
	
	//파일 업로드
	public static String fileUpload(
			String uploadPath, String fileName, byte[] fileData, String mdPath) throws Exception {
		
		UUID uid = UUID.randomUUID();
		String newFileName = uid + "_" + fileName; //image 이름
		String imgPath = uploadPath + mdPath; //image 경로
		
		File target = new File(imgPath, newFileName);
		FileCopyUtils.copy(fileData, target); //파일 복사 작업 : 오류가 발생하면 IOException이 발생
		
		//썸네일
//		String thumbFileName = "s_" + newFileName;
//		
//		File image = new File(imgPath + File.separator + newFileName);
//		File thumbnail = new File(imgPath + File.separator + "s" + File.separator + thumbFileName);
//		System.out.println(thumbnail.getAbsolutePath());
//		if (image.exists()) {
//			thumbnail.getParentFile().mkdirs();
//			Thumbnails.of(image).size(THUMB_WIDTH, THUMB_HEIGHT).toFile(thumbnail);
//		}
		
		//System.out.println("fileUpload newFileName: " + newFileName);
		return newFileName;
	}
	
	//폴더이름 및 폴더 생성
	public static String calcPath(String uploadPath) {
		Calendar cal = Calendar.getInstance();
		String monthPath =  File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
		String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));
		
		int pos = uploadPath.lastIndexOf("\\");
		//System.out.println("pos: " + pos);
		String folder = uploadPath.substring(0,pos);
		//System.out.println("folder: " + folder);
		makeDir(folder, uploadPath.substring(pos));
		//System.out.println(folder + ":" + uploadPath.substring(pos));
		 
		makeDir(uploadPath, monthPath, datePath );
		return datePath;
	}
	
	// 폴더 생성
	private static void makeDir(String uploadPath, String... paths) {
		if (new File(paths[paths.length - 1]).exists())
			return;
		for (String path : paths) {
			File dirPath = new File(uploadPath + path);
			
			System.out.println(dirPath);
			
			if (!dirPath.exists())
				dirPath.mkdir();
		}
	}
}
