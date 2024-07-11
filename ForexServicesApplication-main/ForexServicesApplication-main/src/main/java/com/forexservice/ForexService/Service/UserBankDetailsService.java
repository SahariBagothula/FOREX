package com.forexservice.ForexService.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.forexservice.ForexService.Dto.UserBankDetailsDto;
import com.forexservice.ForexService.Entity.UserBankDetails;


public interface UserBankDetailsService {
	
public UserBankDetailsDto saveBankDetails(UserBankDetailsDto bankDto) ;
	
	
	public List<UserBankDetails> getAllBankDetails();

}
