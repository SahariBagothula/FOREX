package com.forexservice.ForexService.Exception;

public class InvalidAccountException extends RuntimeException{
    public  InvalidAccountException(String msg) {
   	 super(msg);
    }
}

