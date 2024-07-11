package com.forexservice.ForexService.Controller;
 
 
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.time.LocalDate;
import java.util.Locale;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
 
import com.forexservice.ForexService.Entity.Transaction;
import com.forexservice.ForexService.Exception.TransactionNotFoundException;
import com.forexservice.ForexService.Repository.TransactionRepository;
import com.forexservice.ForexService.Service.ReportService;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Element;
import com.itextpdf.text.DocumentException;
 
 
import com.itextpdf.text.pdf.PdfWriter;
 
@CrossOrigin(origins = "http://localhost:3000/")
@RestController
public class ReportController {
	@Autowired
	private ReportService reportService;
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private MessageSource messageSource;
 
	@GetMapping("/report/{transactionId}")
	public ResponseEntity<byte[]> getReport(@PathVariable int transactionId) throws IOException, BindException, DocumentException {
	    Transaction transaction = transactionRepository.findById(transactionId)
	            .orElseThrow(() -> new TransactionNotFoundException(messageSource.getMessage("transaction.not.found",null, Locale.US)+ transactionId));
 
	    // Generate report based on transaction and return as byte[]
	    byte[] reportContent = generateReport(transaction);
 
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_PDF);
	    headers.setContentDisposition(ContentDisposition.builder("attachment")
	        .filename("report.pdf")
	        .build());
 
        return new ResponseEntity<>(reportContent, headers, HttpStatus.OK);
	}
 
	public byte[] generateReport(Transaction transaction) throws BindException, DocumentException {
	    // Get the transaction details from the transaction object
	    int transactionId = transaction.getTransactionId();
	    LocalDate transactionDate = transaction.getTransactionDate();
	    String senderName = transaction.getSenderName();
	    String receiverName = transaction.getReceiverName();
	    long senderAccNo = transaction.getSenderAccNo();
	    long receiverAccNo = transaction.getReceiverAccNo();
	    long sendingAmount = transaction.getSendingAmount();
 
	    // Create a PDF document and set margins
	    Document document = new Document(PageSize.A4, 36, 36, 54, 54);
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    PdfWriter writer = PdfWriter.getInstance(document, outputStream);
 
	    // Set font
	    Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
	    Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
	    Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
	    // Open the document and add content
	    document.open();
	    // Add title
	    Paragraph title = new Paragraph("Transaction Receipt", titleFont);
	    title.setAlignment(Element.ALIGN_CENTER);
	    document.add(title);
 
	    // Add transaction details
	    Paragraph transactionIdLabel = new Paragraph("Transaction ID: ", labelFont);
	    transactionIdLabel.add(new Chunk(String.valueOf(transactionId), valueFont));
	    document.add(transactionIdLabel);
 
	    Paragraph transactionDateLabel = new Paragraph("Transaction Date: ", labelFont);
	    transactionDateLabel.add(new Chunk(transactionDate.toString(), valueFont));
	    document.add(transactionDateLabel);
 
	    Paragraph senderNameLabel = new Paragraph("Sender Name: ", labelFont);
	    senderNameLabel.add(new Chunk(senderName, valueFont));
	    document.add(senderNameLabel);
 
	    Paragraph receiverNameLabel = new Paragraph("Receiver Name: ", labelFont);
	    receiverNameLabel.add(new Chunk(receiverName, valueFont));
	    document.add(receiverNameLabel);
 
	    Paragraph senderAccNoLabel = new Paragraph("Sender Account Number: ", labelFont);
	    senderAccNoLabel.add(new Chunk(String.valueOf(senderAccNo), valueFont));
	    document.add(senderAccNoLabel);
 
	    Paragraph receiverAccNoLabel = new Paragraph("Receiver Account Number: ", labelFont);
	    receiverAccNoLabel.add(new Chunk(String.valueOf(receiverAccNo), valueFont));
	    document.add(receiverAccNoLabel);
 
	    Paragraph sendingAmountLabel = new Paragraph("Sending Amount: ", labelFont);
	    sendingAmountLabel.add(new Chunk(String.valueOf(sendingAmount), valueFont));
	    document.add(sendingAmountLabel);
 
	    // Add other report content as needed
 
	    // Add footer
	    Paragraph footer = new Paragraph("Thank you for your business.", valueFont);
	    footer.setAlignment(Element.ALIGN_CENTER);
	    document.add(new Paragraph("\n"));
	    document.add(footer);
 
	    // Close the document
	    document.close();
 
	    // Return the report as a byte[]
	    return outputStream.toByteArray();
	}
 
}