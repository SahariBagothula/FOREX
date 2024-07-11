package com.forexservice.ForexService.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.forexservice.ForexService.Dto.UserBankDetailsDto;
import com.forexservice.ForexService.Service.UserBankDetailsService;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController

public class UserBankDetailsController {
	
	@Autowired
	private UserBankDetailsService iUserBankDetails;
	
	
	@PostMapping("/UserBankDetails/save")
	public ResponseEntity<UserBankDetailsDto> addBankDetails( @Validated @RequestBody UserBankDetailsDto userBankDetails )
	{
		 UserBankDetailsDto newUserBankDetails = iUserBankDetails.saveBankDetails(userBankDetails);
		ResponseEntity<UserBankDetailsDto> responseEntity = new ResponseEntity<>(newUserBankDetails, HttpStatus.CREATED);
		return responseEntity;
	}
}	