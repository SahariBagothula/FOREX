package com.forexservice.ForexService.Service;

import org.springframework.stereotype.Service;

import com.forexservice.ForexService.Dto.ReportDto;


public interface ReportService {
  
    byte[] generateReport(ReportDto reportDto);
    ReportDto generateReport(int transactionId);
}