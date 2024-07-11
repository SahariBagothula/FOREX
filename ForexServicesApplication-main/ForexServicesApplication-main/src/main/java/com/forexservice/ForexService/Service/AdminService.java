package com.forexservice.ForexService.Service;

import org.springframework.stereotype.Service;

import com.forexservice.ForexService.Dto.AdminDto;
import com.forexservice.ForexService.Dto.ExchangeRateDto;
import com.forexservice.ForexService.Entity.ExchangeRate;
import com.forexservice.ForexService.Exception.InvalidInputException;



public interface AdminService {
	AdminDto saveAdmin(AdminDto adminDto);
	public ExchangeRateDto saveExchangeRate(ExchangeRateDto exchangeRateDto);
	public ExchangeRate updateExchangeRate(ExchangeRate exchangeRate);

	
	String resetPassword(String adminEmail, String adminPassword, String newPassword) throws InvalidInputException;
	
	String resetForgotPassword(String adminEmail, String newPassword, String confirmPassword) throws InvalidInputException;

}

