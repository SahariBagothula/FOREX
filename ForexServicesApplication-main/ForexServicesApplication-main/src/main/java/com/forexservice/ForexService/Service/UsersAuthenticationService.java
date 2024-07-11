package com.forexservice.ForexService.Service;

import org.springframework.stereotype.Service;

import com.forexservice.ForexService.Entity.Users;


public interface UsersAuthenticationService {
	
public Users login(String email, String password);
	
	
	public String sendEmail(String toEmail);
	public String verifyOtp(String email,String otp);
	public String verifyEmail(String email);

}
