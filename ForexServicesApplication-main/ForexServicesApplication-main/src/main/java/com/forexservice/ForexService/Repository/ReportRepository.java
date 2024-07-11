package com.forexservice.ForexService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forexservice.ForexService.Entity.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer>{

}
