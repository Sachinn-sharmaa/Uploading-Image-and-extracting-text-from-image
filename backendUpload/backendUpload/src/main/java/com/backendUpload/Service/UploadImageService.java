package com.backendUpload.Service;
import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backendUpload.Entity.UploadImageFile;
import com.backendUpload.Repository.UploadImageRepository;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Service
public class UploadImageService {
	@Autowired 
    private UploadImageRepository uploadImageRepository;
    @Autowired 
    private BoldWordExtractor boldWordExtractor;
    @Autowired
    private Tesseract tesseract;
    

    public UploadImageFile recognizeText(InputStream inputStream, MultipartFile file, String filePath) throws IOException {
    	  BufferedImage image = ImageIO.read(inputStream);    	  
    	  try {
    		  String text = tesseract.doOCR(image);
    		  
    		  String[] words = text.split("\\s+");
    		  String normalText = "";
    		  String boldWords = "";
              for (String word : words) {
                  if (isBold(word)) {
                      System.out.println("Bold Text: " + word);
                      boldWords += word;
                  } else {
                      System.out.println("Normal Text: " + word);
                      normalText += word;
                  }
              }
    	
//    	    	String boldWords = boldWordExtractor.extractBoldWords(text);
//    	    	String imageBase64 = Base64.getEncoder().encodeToString(file.getBytes());
    	    	
    	    	UploadImageFile uploadedImage = new UploadImageFile();
    	    	
    	    	uploadedImage.setImage(filePath);
    	    	uploadedImage.setText(normalText);
    	    	uploadedImage.setBold_text_words(boldWords);
    	    	
    	   return uploadImageRepository.save(uploadedImage);
    	  } catch (TesseractException e) {
    	   e.printStackTrace();
    	  }
    	  return null;
    	 }
    
    
    private static boolean isBold(String text) {
        return text.contains("*") || text.startsWith("<b>") || text.endsWith("</b>");
    }
    
//    public UploadImageFile uploadImage(MultipartFile file) throws Exception{
//
//    	Tesseract tessract = new Tesseract();
//    	tesseract.setDatapath("tessdata");
//    	tesseract.setLanguage("eng");
//    	tesseract.setPageSegMode(1);
//    	tesseract.setOcrEngineMode(1);
//    	
//    	
//    	BufferedImage imgData = ImageIO.read(file.getInputStream());
//    	
//    	String text = tessract.doOCR(imgData);
//    	System.out.println(text);
//    	String boldWords = boldWordExtractor.extractBoldWords(text);
//    	
//    	String imageBase64 = Base64.getEncoder().encodeToString(file.getBytes());
//    	
//    	UploadImageFile uploadedImage = new UploadImageFile();
//    	uploadedImage.setImage(imageBase64);
//    	uploadedImage.setText(text);
//    	uploadedImage.setBoldTextWords(boldWords);
//    	
//    	return uploadImageRepository.save(uploadedImage);
//    }
    
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

