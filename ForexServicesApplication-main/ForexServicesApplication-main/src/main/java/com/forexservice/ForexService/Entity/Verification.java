package com.forexservice.ForexService.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Verification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String userName;
	String Otp;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOtp() {
		return Otp;
	}
	public void setOtp(String otp) {
		Otp = otp;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	

}
