package com.forexservice.ForexService.Exception;

public class UserBankDetailsNotFoundException extends RuntimeException {
	
	public UserBankDetailsNotFoundException (String msg)
	{
		super(msg);
	}
}
