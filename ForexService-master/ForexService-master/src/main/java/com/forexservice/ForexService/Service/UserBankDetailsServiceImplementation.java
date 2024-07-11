package com.forexservice.ForexService.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.forexservice.ForexService.Dto.UserBankDetailsDto;
import com.forexservice.ForexService.Entity.UserBankDetails;
import com.forexservice.ForexService.Entity.Users;
import com.forexservice.ForexService.Exception.UserBankDetailsNotFoundException;
import com.forexservice.ForexService.Exception.UsersNotFoundException;
import com.forexservice.ForexService.Repository.UserBankDetailsRepository;
import com.forexservice.ForexService.Repository.UsersRepository;

@Service
public class UserBankDetailsServiceImplementation implements UserBankDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserBankDetailsRepository userBankDetailsRepository;

    @Autowired
    private MessageSource messageSource;

    @Override
    public List<UserBankDetailsDto> getAllBankDetails() {
        List<UserBankDetails> userBankDetailsList = userBankDetailsRepository.findAll();
        if (userBankDetailsList.isEmpty()) {
            throw new UserBankDetailsNotFoundException(
                    messageSource.getMessage("bankDetails.not.found", null, Locale.US));
        }
        return convertToDtoList(userBankDetailsList);
    }

    @Override
    public UserBankDetailsDto getBankDetailsById(int userBankId) {
        Optional<UserBankDetails> optionalBankDetails = userBankDetailsRepository.findById(userBankId);
        UserBankDetails bankDetails = optionalBankDetails.orElseThrow(() -> new UserBankDetailsNotFoundException(
                messageSource.getMessage("bankDetails.not.found.id", new Object[] { userBankId }, Locale.US)));
        return convertToDto(bankDetails);
    }

    @Override
    public UserBankDetailsDto updateBankDetails(int userBankId, UserBankDetailsDto updatedDetails) {
        Optional<UserBankDetails> optionalBankDetails = userBankDetailsRepository.findById(userBankId);
        UserBankDetails bankDetails = optionalBankDetails.orElseThrow(() -> new UserBankDetailsNotFoundException(
                messageSource.getMessage("bankDetails.not.found.id", new Object[] { userBankId }, Locale.US)));

        // Update the fields
        bankDetails.setAccountHolderName(updatedDetails.getAccountHolderName());
        bankDetails.setBankName(updatedDetails.getBankName());
        bankDetails.setContactNumber(updatedDetails.getContactnumber());
        bankDetails.setIfscCode(updatedDetails.getIfscCode());
        bankDetails.setAccountNumber(updatedDetails.getAccountNumber());

        UserBankDetails updatedBankDetails = userBankDetailsRepository.save(bankDetails);
        return convertToDto(updatedBankDetails);
    }

    @Override
    public UserBankDetailsDto saveBankDetails(UserBankDetailsDto bankDto) {
        Optional<Users> optionalUsers = usersRepository.findById(bankDto.getUserId());

        if (optionalUsers.isEmpty()) {
            throw new UsersNotFoundException(
                    messageSource.getMessage("users.not.found", null, Locale.US) + " " + bankDto.getUserId());
        }
        Users user = optionalUsers.get();

        UserBankDetails obj = new UserBankDetails();

        obj.setAccountHolderName(bankDto.getAccountHolderName());
        obj.setBankName(bankDto.getBankName());

        obj.setContactNumber(bankDto.getContactnumber());

        obj.setIfscCode(bankDto.getIfscCode());
        obj.setAccountNumber(bankDto.getAccountNumber());
        obj.setUser(user);
        UserBankDetails newBankDetails = userBankDetailsRepository.save(obj);

        return convertToDto(newBankDetails);
    }

    // Convert Entity to DTO methods
    private UserBankDetailsDto convertToDto(UserBankDetails bankDetails) {
        UserBankDetailsDto dto = new UserBankDetailsDto();
        dto.setUserBankId(bankDetails.getId());
        dto.setAccountHolderName(bankDetails.getAccountHolderName());
        dto.setAccountNumber(bankDetails.getAccountNumber());
        dto.setBankName(bankDetails.getBankName());
        dto.setIfscCode(bankDetails.getIfscCode());
        dto.setContactnumber(bankDetails.getContactNumber());
        dto.setUserId(bankDetails.getUser().getUsersId());
        return dto;
    }

    private List<UserBankDetailsDto> convertToDtoList(List<UserBankDetails> bankDetailsList) {
        return bankDetailsList.stream().map(this::convertToDto).collect(Collectors.toList());
    }

}



