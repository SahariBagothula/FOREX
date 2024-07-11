package com.forexservice.ForexService.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;



import com.forexservice.ForexService.Dto.TransactionDto;
import com.forexservice.ForexService.Entity.ExchangeRate;
import com.forexservice.ForexService.Entity.Transaction;
import com.forexservice.ForexService.Entity.UserBankDetails;
import com.forexservice.ForexService.Exception.ExchangeRateNotFoundException;
import com.forexservice.ForexService.Exception.TransactionNotFoundException;
import com.forexservice.ForexService.Exception.UserBankDetailsNotFoundException;
import com.forexservice.ForexService.Repository.ExchangeRateRepository;
import com.forexservice.ForexService.Repository.TransactionRepository;
import com.forexservice.ForexService.Repository.UserBankDetailsRepository;

@Service
public class TransactionServiceImplementation implements TransactionService{
	//for exception messages
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private UserBankDetailsRepository userBankDetailsRepository;
	
	@Autowired
	private ExchangeRateRepository exchangeRateRepository;
	
//	@Override
	public TransactionDto saveTransaction(TransactionDto transactionDto) {
		Transaction ts = new Transaction();
		
		
		
		ts.setFromCountry(transactionDto.getFromCountry());
		ts.setToCountry(transactionDto.getToCountry());
		ts.setSenderName(transactionDto.getSenderName());
		ts.setReceiverName(transactionDto.getReceiverName());
	
		Optional<UserBankDetails> optionalBankDetails = userBankDetailsRepository.findByAccountNumber(transactionDto.getSenderAccNo());
		if(optionalBankDetails.isEmpty()) {
			throw new UserBankDetailsNotFoundException(messageSource.getMessage("userBankDetails.not.found",null,Locale.US));
		}
		UserBankDetails ub = optionalBankDetails.get();
		ts.setSenderAccNo(ub.getAccountNumber());

		
		Optional<UserBankDetails> optionalBankDetailsrec = userBankDetailsRepository.findByAccountNumber(transactionDto.getReceiverAccNo());
		if(optionalBankDetailsrec.isEmpty()) {
			throw new UserBankDetailsNotFoundException(messageSource.getMessage("usersBankDetails.not.found",null,Locale.US));
		}
		UserBankDetails ub1 = optionalBankDetails.get();
		ts.setReceiverAccNo(transactionDto.getReceiverAccNo());
		
		
		
		//try
		ExchangeRate exchangeRate = exchangeRateRepository.findByFromCurrencyAndToCurrency(transactionDto.getSendingCurrency(), transactionDto.getReceivingCurrency());
		
		if (exchangeRate == null) {
			throw new ExchangeRateNotFoundException(messageSource.getMessage("exchangeRate.not.found",null,Locale.US) +" "+ transactionDto.getSendingCurrency() + "/" + transactionDto.getReceivingCurrency());
		}
		double rate = exchangeRate.getRate();
		double totalAmount = transactionDto.getSendingAmount() * rate;
		ts.setTotalAmount(totalAmount);
		ts.setSendingCurrency(transactionDto.getSendingCurrency());
		ts.setReceivingCurrency(transactionDto.getReceivingCurrency());
		ts.setSendingAmount(transactionDto.getSendingAmount());
		ts.setTransactionDate(transactionDto.getTransactionDate());
		
		

		Transaction newTransaction = transactionRepository.save(ts);
		TransactionDto newTransactionDto = new TransactionDto();
		newTransactionDto.setTransactionId(newTransaction.getTransactionId());
		newTransactionDto.setFromCountry(newTransaction.getFromCountry());
		newTransactionDto.setToCountry(newTransaction.getToCountry());
		newTransactionDto.setSenderName(newTransaction.getSenderName());
		newTransactionDto.setReceiverName(newTransaction.getReceiverName());
		newTransactionDto.setSenderAccNo(newTransaction.getSenderAccNo());
		newTransactionDto.setReceiverAccNo(newTransaction.getReceiverAccNo());
		newTransactionDto.setSendingCurrency(newTransaction.getSendingCurrency());
		newTransactionDto.setReceivingCurrency(newTransaction.getReceivingCurrency());
		newTransactionDto.setSendingAmount(newTransaction.getSendingAmount());
		newTransactionDto.setTransactionDate(newTransaction.getTransactionDate());
		return newTransactionDto;
	}
	
//	@Override
	public Transaction getTransactionById(int transactionId) throws TransactionNotFoundException {
		Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
		if (optionalTransaction.isEmpty()) {
			throw new TransactionNotFoundException(messageSource.getMessage("transaction.not.found",null,Locale.US) + " "+transactionId);
		}
		Transaction transaction = optionalTransaction.get();
		return transaction;
	}
	
//	@Override
	public List<Transaction> getAllTransactions() {
		List<Transaction> transaction = transactionRepository.findAll();
		return transaction;
	}
    
//	@Override
	public TransactionDto findTransactionById(int transactionId) {
	Transaction transaction = transactionRepository.findById(transactionId)
	 .orElseThrow(() -> new TransactionNotFoundException(messageSource.getMessage("transaction.not.found",null,Locale.US) + " "+transactionId));
      return new TransactionDto(transaction);
   }
}
