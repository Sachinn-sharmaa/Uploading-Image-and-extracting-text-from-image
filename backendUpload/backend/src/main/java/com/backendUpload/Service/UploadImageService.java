package com.backendUpload.Service;
import net.sourceforge.tess4j.Tesseract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backendUpload.Repository.UploadImageRepository;

import com.backendUpload.Entity.UploadImageFile;

import java.awt.image.BufferedImage;

import java.util.Base64;

import javax.imageio.ImageIO;

@Service
public class UploadImageService {
	@Autowired 
    private UploadImageRepository uploadImageRepository;
    @Autowired 
    private BoldWordExtractor boldWordExtractor;
    
    public UploadImageFile uploadImage(MultipartFile file) throws Exception{
    	Tesseract tessract = new Tesseract();
    	tessract.setDatapath("tessdata");
    	
    	BufferedImage imgData = ImageIO.read(file.getInputStream());
    	String text = tessract.doOCR(imgData);
    	
    	String boldWords = boldWordExtractor.extractBoldWords(text);
    	
    	String imageBase64 = Base64.getEncoder().encodeToString(file.getBytes());
    	
    	UploadImageFile uploadedImage = new UploadImageFile();
    	uploadedImage.setImage(imageBase64);
    	uploadedImage.setText(text);
    	uploadedImage.setBoldTextWords(boldWords);
    	
    	return uploadImageRepository.save(uploadedImage);
    }
    
}
