package com.forexservice.ForexService.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.forexservice.ForexService.Dto.UsersDto;
import com.forexservice.ForexService.Entity.Users;
import com.forexservice.ForexService.Exception.InvalidInputException;


public interface UsersService {

	public void deleteUsers(int usersId);

	public List<Users> getAllUsers();

	public Users getUsersById(int usersId);

	UsersDto saveUsers(UsersDto usersDto);

	String resetPassword(String email, String password, String newPassword) throws InvalidInputException;

	String resetForgotPassword(String email, String newPassword, String confirmPassword) throws InvalidInputException;

}