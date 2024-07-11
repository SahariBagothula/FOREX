package com.forexservice.ForexService.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UsersNotFoundException.class)
	public ResponseEntity<String> handleUsersNotFoundException(Exception e){

         ResponseEntity<String> responseEntity = new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
         return responseEntity;
       
    }
	

	@ExceptionHandler(UserBankDetailsNotFoundException.class)
	public ResponseEntity<String> UserBankDetailsNotFoundException(Exception e) {
		ResponseEntity<String> responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		return responseEntity;
	}
	
	@ExceptionHandler(InvalidInputException.class)
	 public ResponseEntity<String> UsersInvalidInputException(Exception e) {
		ResponseEntity<String> responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		return responseEntity;
}
	
}