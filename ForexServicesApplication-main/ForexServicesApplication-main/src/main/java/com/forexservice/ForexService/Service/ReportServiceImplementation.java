package com.forexservice.ForexService.Service;
 
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.Locale;
 
import com.itextpdf.text.Document;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
 
import com.forexservice.ForexService.Dto.ReportDto;
import com.forexservice.ForexService.Entity.Report;
import com.forexservice.ForexService.Entity.Transaction;
import com.forexservice.ForexService.Exception.TransactionNotFoundException;
import com.forexservice.ForexService.Repository.ReportRepository;
import com.forexservice.ForexService.Repository.TransactionRepository;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
 
 
@Service
public class ReportServiceImplementation implements ReportService {
 
	@Autowired
	private ReportRepository reportRepository;
 
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private MessageSource messageSource;
 
	@Override
	public ReportDto generateReport(int transactionId) {
		Transaction transaction = transactionRepository.findById(transactionId)
				.orElseThrow(() -> new TransactionNotFoundException(messageSource.getMessage("transaction.not.found",null, Locale.US)+ transactionId));
 
		ReportDto reportDto = new ReportDto();
		reportDto.setTransactionId(transaction);
 
		// Generate report based on reportDto and return as byte[]
		byte[] reportContent = generateReport(reportDto);
 
		// Save the report content to the database
		Report report = new Report();
		reportDto.setTransactionId(transaction);
		reportRepository.save(report);
 
		// Return the report DTO
		ReportDto newReportDto = new ReportDto();
		newReportDto.setReportId(report.getReportId());
		newReportDto.setTransactionId(reportDto.getTransactionId());
		return newReportDto;
	}
 
	public byte[] generateReport(ReportDto reportDto) {
		// Get the transaction details from the transaction DTO
		Transaction transaction = reportDto.getTransactionId();
		String transactionId = String.valueOf(transaction.getTransactionId());
		LocalDate transactionDate = transaction.getTransactionDate();
		// Get other transaction details as needed
 
		// Create a PDF document and add some content
		Document document = new Document();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
 
		try {
			PdfWriter.getInstance(document, outputStream);
			document.open();
			document.add(new Paragraph("Transaction ID: " + transactionId));
			document.add(new Paragraph("Transaction Date: " + transactionDate.toString()));
			// Add other report content as needed
			document.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
 
		// Return the report as a byte[]
		return outputStream.toByteArray();
	}
 
}