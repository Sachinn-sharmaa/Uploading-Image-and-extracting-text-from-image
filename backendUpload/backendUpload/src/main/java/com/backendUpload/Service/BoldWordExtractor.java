package com.backendUpload.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
@Service
public class BoldWordExtractor {
	
	private final Set<String> boldWordsSet = new HashSet<>(Arrays.asList("add","your","desired", "bold", "words"));
	
	public String extractBoldWords(String text) {
		StringBuilder boldWords = new StringBuilder();
		String words[] = text.split(" ");
		
		for(String word: words) {
			if(boldWordsSet.contains(word.toLowerCase())) {
				boldWords.append(word).append(" ");
			}
		}
		return boldWords.toString().trim();
	}
	

}
