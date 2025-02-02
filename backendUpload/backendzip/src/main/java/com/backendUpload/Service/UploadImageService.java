package com.backendUpload.Service;
import net.sourceforge.tess4j.Tesseract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backendUpload.Repository.UploadImageRepository;

import com.backendUpload.Entity.UploadImageFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Base64;

import javax.imageio.ImageIO;

@Service
public class UploadImageService {
	@Autowired 
    private UploadImageRepository uploadImageRepository;
    @Autowired 
    private BoldWordExtractor boldWordExtractor;
    @Autowired
    private Tesseract tesseract;
    
    public UploadImageFile uploadImage(MultipartFile file) throws Exception{
    	
    	

    	Tesseract tessract = new Tesseract();
    	tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");
    	tesseract.setLanguage("eng");
    	tesseract.setPageSegMode(1);
    	tesseract.setOcrEngineMode(1);
    	
    	
    	BufferedImage imgData = ImageIO.read(file.getInputStream());
    	String text = tessract.doOCR(imgData);
    	System.out.println(text);
    	String boldWords = boldWordExtractor.extractBoldWords(text);
    	
    	String imageBase64 = Base64.getEncoder().encodeToString(file.getBytes());
    	
    	UploadImageFile uploadedImage = new UploadImageFile();
    	uploadedImage.setImage(imageBase64);
    	uploadedImage.setText(text);
    	uploadedImage.setBoldTextWords(boldWords);
    	
    	return uploadImageRepository.save(uploadedImage);
    }
    
}

//Save the uploaded file to a temporary location
//File tempFile = File.createTempFile("temp", null);
//file.transferTo(tempFile);
//
//// Read the saved file into a BufferedImage
//BufferedImage bufferedImage = ImageIO.read(tempFile);
//
//// Perform OCR using Tesseract
//String result = null;
//if (bufferedImage != null) {
//  result = tesseract.doOCR(bufferedImage);
//}
//
//// Delete the temporary file
//tempFile.delete();
//
//return result;
//}
//}

