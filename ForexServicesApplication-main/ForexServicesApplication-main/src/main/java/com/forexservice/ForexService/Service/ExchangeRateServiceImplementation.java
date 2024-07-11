package com.forexservice.ForexService.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.forexservice.ForexService.Dto.ExchangeRateDto;
import com.forexservice.ForexService.Entity.Admin;
import com.forexservice.ForexService.Entity.ExchangeRate;
import com.forexservice.ForexService.Exception.AdminNotFoundException;
import com.forexservice.ForexService.Exception.ExchangeRateNotFoundException;
import com.forexservice.ForexService.Repository.AdminRepository;
import com.forexservice.ForexService.Repository.ExchangeRateRepository;

@Service
public class ExchangeRateServiceImplementation implements ExchangeRateService {
	@Autowired
	private ExchangeRateRepository exchangeRateRepository;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private AdminRepository adminRepository;

	@Override
	public ExchangeRateDto saveExchangeRate(ExchangeRateDto exchangeRateDto) {
		// TODO Auto-generated method stub
		ExchangeRate exRate =new ExchangeRate();
        exRate.setId(exchangeRateDto.getId());
         exRate.setFromCurrency(exchangeRateDto.getFromCurrency());
         exRate.setToCurrency(exchangeRateDto.getToCurrency());
         exRate.setRate(exchangeRateDto.getRate());
         exRate.setDate(exchangeRateDto.getDate());
        
		Optional<Admin> optionalAdmin = adminRepository.findById(exchangeRateDto.getAdminId());
		if (optionalAdmin.isEmpty()) {

			throw new AdminNotFoundException(" admin is not exists");

			}
		
             Admin admin=optionalAdmin.get();
              exRate.setAdmin(admin);
             ExchangeRate newExchangeRate = exchangeRateRepository.save(exRate);

		return exchangeRateDto;
	}

	@Override
	public Double convertAmount(Double amount, String fromCurrency, String toCurrency) {
		
		ExchangeRate exchangeRate = exchangeRateRepository.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency);
		if (exchangeRate == null) {

			throw new ExchangeRateNotFoundException("Excahnge Rate Not found " +" "+ fromCurrency + "/" + toCurrency);
		}
		Double rate = exchangeRate.getRate();

		return rate * amount;
	}


//	To get all the rates by entering date
	@Override
	public List<ExchangeRate> getRatesByDate(String dateString) throws ExchangeRateNotFoundException {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(dateString, formatter);
		List<ExchangeRate> exchangeRates = exchangeRateRepository.findByDate(date);
		if (exchangeRates.isEmpty()) {

			throw new ExchangeRateNotFoundException("exchangeRatenotfound" + date);
		}
		
		
		return exchangeRates;
	}

//	To get rates by providing from currency and to currency and date
	@Override
	public List<ExchangeRate> getRatesByFromToCurrencyAndDate(String fromCurrency, String toCurrency, LocalDate date)
			throws ExchangeRateNotFoundException {
		
		List<ExchangeRate> exchangeRates = exchangeRateRepository.findByFromCurrencyAndToCurrencyAndDate(fromCurrency,
				toCurrency, date);
		if (exchangeRates.isEmpty()) {

			throw new ExchangeRateNotFoundException("exchangeRate.not.found" +" "+ fromCurrency + "/" + toCurrency + " "+ date + "");
		}
		return exchangeRates;

	}
//To update the rates
	@Override
	public ExchangeRate updateExchangeRate(ExchangeRate exchangeRate)throws ExchangeRateNotFoundException {
		
		Optional<ExchangeRate> optionalExchangeRate = exchangeRateRepository.findById(exchangeRate.getId());
		if (optionalExchangeRate.isEmpty()) {
			throw new ExchangeRateNotFoundException("Rate not existing with id:" + exchangeRate.getId());
		}
		ExchangeRate updatedExchangeRate = exchangeRateRepository.save(exchangeRate);
		return updatedExchangeRate;
	}

	@Override
	public ExchangeRate getById(int Id)throws ExchangeRateNotFoundException {
		Optional<ExchangeRate> optionalExchangeRate = exchangeRateRepository.findById(Id);
		if (optionalExchangeRate.isEmpty()) {
			throw new ExchangeRateNotFoundException("exchangeRate.not.found"+Id);
		}
		ExchangeRate exchangeRate = optionalExchangeRate.get();

		return exchangeRate;
	}

	@Override
	public List<ExchangeRate> findAllRates() {
		List<ExchangeRate> exchangeRate = exchangeRateRepository.findAll();
		return exchangeRate;
		
	}

	
}
