package com.forexservice.ForexService.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.forexservice.ForexService.Dto.TransactionDto;
import com.forexservice.ForexService.Entity.Transaction;


public interface TransactionService {
   public TransactionDto saveTransaction(TransactionDto transactionDto);
	
	public Transaction getTransactionById(int transactionId);
	
	public TransactionDto findTransactionById(int transactionId);
	
	public List<Transaction> getAllTransactions();
	
	
	
}
