package com.backendUpload.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backendUpload.Entity.UploadImageFile;
import com.backendUpload.Service.UploadImageService;

@RestController
@RequestMapping("/api/")
public class UploadImageController {
	@Autowired
	private UploadImageService uploadImageService;
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping("upload")
	public ResponseEntity<UploadImageFile> uploadImage(@RequestParam("image") MultipartFile file){
		try {
			System.out.println("Inside controller");
			System.out.println(file);
			UploadImageFile uploadImageFile = uploadImageService.uploadImage(file);
			return ResponseEntity.ok(uploadImageFile);
					
		}catch(Exception e) {
			return ResponseEntity.status(500).body(null);
		}
	
	}
}

