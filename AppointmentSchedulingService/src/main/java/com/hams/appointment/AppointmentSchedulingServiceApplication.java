package com.hams.appointment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;

@SpringBootApplication
@EnableFeignClients

public class AppointmentSchedulingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppointmentSchedulingServiceApplication.class, args);
	}

}
