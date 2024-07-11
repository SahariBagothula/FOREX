package com.forexservice.ForexService.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forexservice.ForexService.Entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>{

	Optional<Transaction> findById(int transcationId);
	
}
