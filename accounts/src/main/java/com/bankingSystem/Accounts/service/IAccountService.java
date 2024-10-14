package com.bankingSystem.Accounts.service;

import com.bankingSystem.Accounts.dto.CustomerDto;

public interface IAccountService {

    void createAccount(CustomerDto customerDto);
    CustomerDto fetchAccount(String phoneNumber);

    boolean updateAccount(CustomerDto customerDto);

    boolean deleteAccount(String phoneNumber);
}
