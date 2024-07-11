package com.forexservice.ForexService.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forexservice.ForexService.Entity.Verification;

public interface VerificationRepository extends JpaRepository<Verification, Integer>{

	public Optional<Verification> findByUserName(String userName);
	public void deleteByUserName(String userName);
	
}
