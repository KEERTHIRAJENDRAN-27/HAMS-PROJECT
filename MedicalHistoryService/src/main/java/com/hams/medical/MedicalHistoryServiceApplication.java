package com.hams.medical;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MedicalHistoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicalHistoryServiceApplication.class, args);
	}

}
