package com.hams.appointment.feignclient;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hams.appointment.dto.*;

@FeignClient(name = "NOTIFICATIONSERVICE", path = "/notification")
public interface NotificationClient {
	@PostMapping("/send")
	public String sendNotification(@RequestBody NotificationDTO dto);
}
