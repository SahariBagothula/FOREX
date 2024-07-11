package com.forexservice.ForexService.Service;

import com.forexservice.ForexService.Dto.UserBankDetailsDto;
import com.forexservice.ForexService.Entity.UserBankDetails;

import java.util.List;

public interface UserBankDetailsService {

    UserBankDetailsDto saveBankDetails(UserBankDetailsDto bankDto);

    List<UserBankDetailsDto> getAllBankDetails();

    UserBankDetailsDto getBankDetailsById(int userBankId);

    UserBankDetailsDto updateBankDetails(int userBankId, UserBankDetailsDto updatedDetails);
}
