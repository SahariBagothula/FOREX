package com.forexservice.ForexService.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forexservice.ForexService.Entity.UserBankDetails;

public interface UserBankDetailsRepository extends JpaRepository<UserBankDetails, Integer>{
	
	 Optional<UserBankDetails> findByAccountNumber(long accountNumber);

}
