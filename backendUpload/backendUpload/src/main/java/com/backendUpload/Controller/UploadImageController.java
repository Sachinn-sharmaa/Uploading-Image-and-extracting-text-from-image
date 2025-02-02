package com.backendUpload.Controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
	
	 private String BASE_DIR="src/main/resources/imageData";
	 
	 
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping("upload")
	public ResponseEntity<UploadImageFile> uploadImage(@RequestParam("image") MultipartFile file){
		try {
			String saveFileDirectory = handleFileUpload(file);
			
			UploadImageFile uploadImageFile = uploadImageService.recognizeText(file.getInputStream(), file, saveFileDirectory);
			return ResponseEntity.ok(uploadImageFile);
					
		}catch(Exception e) {
			return ResponseEntity.status(500).body(null);
		}
	
	}
	
 
    public String handleFileUpload(MultipartFile file) {
        if (file.isEmpty()) {
            return "Please select a file to upload.";
        }

        try {
        	System.out.println("inside handleFileUpload");
            // Get the bytes from the uploaded file
            byte[] bytes = file.getBytes();
            // Create directory if not exists
            Path path = Paths.get(BASE_DIR);
            Files.createDirectories(path);
            // Create the file path where the uploaded file will be stored
            Path uploadedFilePath = path.resolve(file.getOriginalFilename());
            // Save the file
            Files.write(uploadedFilePath, bytes);
            // Return the uploaded file path
            return "File uploaded successfully: " + uploadedFilePath.toString();
        } catch (Exception e) {
            return "Failed to upload file: " + e.getMessage();
        }
    }
}

