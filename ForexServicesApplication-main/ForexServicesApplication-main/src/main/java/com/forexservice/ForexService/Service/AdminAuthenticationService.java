package com.forexservice.ForexService.Service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.forexservice.ForexService.Entity.Admin;

public interface AdminAuthenticationService {
	
Admin doLogin(String adminUsername, String adminPassword);
	
	public String sendEmail(String toEmail);
	public String verifyOtp(String adminEmail,String otp);
	public String verifyEmail(String adminEmail);

}






