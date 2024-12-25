package com.sam.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Random;

import javax.xml.bind.DatatypeConverter;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sam.to.FileTO;

@RestController
public class FileRestController {

	@PostMapping("/addFile")
	public void addImageFile(@RequestBody FileTO fileTO) {
		
		String imageFile = fileTO.getImageData();
		String imageArr[] = imageFile.split(",");
		
		String fileExt = "";
		
		switch(imageArr[0]) {
		case "data:image/jpeg;base64":
			fileExt = ".jpeg";
			break;
		case "data:image/jpg;base64":
			fileExt = ".jpg";
			break;
		case "data:image/png;base64":
			fileExt = ".png";
			break;
		}
		
		String imageFileName = new Random().nextInt(10000000) + fileExt;
		
		byte[] data = DatatypeConverter.parseBase64Binary(imageArr[1]);
		
		String uploadPath = "upload/";
		
		Path path = Paths.get(uploadPath);
		if(!Files.exists(path)) {
			try {
				Files.createDirectory(path);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		File file = new File(uploadPath + imageFileName);
		
		try(OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))){
			outputStream.write(data);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostMapping("/addFileUsingMultipart")
	public void addImageFileUsingMultipart(@RequestPart MultipartFile file) {
		try {
		byte[] imageFile = file.getBytes();
		
		String baseImg = Base64.getEncoder().encodeToString(imageFile);
		System.out.println(baseImg);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
