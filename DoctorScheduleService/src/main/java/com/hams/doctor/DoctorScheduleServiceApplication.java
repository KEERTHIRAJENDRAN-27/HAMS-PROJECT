package com.hams.doctor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DoctorScheduleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoctorScheduleServiceApplication.class, args);
	}

}
