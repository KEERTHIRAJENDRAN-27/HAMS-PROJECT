package com.hams.appointment.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(name = "MAILNOTIFICATIONS", path = "/email")
public interface MailClient {
	@PostMapping("/send")
	public String sendEmail(@RequestParam String recipient, @RequestParam String subject,
			@RequestParam String message) ;
}
