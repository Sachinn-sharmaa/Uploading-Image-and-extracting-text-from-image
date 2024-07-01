package com.backendUpload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class BackendUploadApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendUploadApplication.class, args);
	}



	@Configuration
	public class AppConfig {

	    @Bean
	    public Tesseract tesseract() {
	        return new Tesseract();
	    }
	}

}
