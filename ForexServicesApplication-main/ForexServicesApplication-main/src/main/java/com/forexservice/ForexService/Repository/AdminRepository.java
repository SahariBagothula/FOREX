package com.forexservice.ForexService.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.forexservice.ForexService.Entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>{
	
	@Query("select a from Admin a where a.adminUsername = :aname and a.adminPassword = :pwd")
	Optional<Admin> login(@Param("aname") String adminUsername, @Param("pwd") String adminPassword);
	
	Optional<Admin> findByAdminEmail(String email);

}
