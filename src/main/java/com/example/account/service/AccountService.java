package com.example.account.service;

import com.example.account.dto.AccountDto;
import com.example.account.dto.AccountDtoConverter;
import com.example.account.dto.CreateAccountRequest;
import com.example.account.model.Account;
import com.example.account.model.Customer;
import com.example.account.model.Transaction;
import com.example.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private  final CustomerService customerService;

    private final  TransactionService transactionService;
    private final AccountDtoConverter converter;
    public AccountService(AccountRepository accountRepository,
                          CustomerService customerService,
                          TransactionService transactionService,
                          AccountDtoConverter converter) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.transactionService = transactionService;
        this.converter = converter;
    }

    public AccountDto createAccount(CreateAccountRequest createAccountRequest){
        Customer customer = customerService.findCustomerById(createAccountRequest.getCustomerId());

        Account account = new Account( customer,
                createAccountRequest.getInitialCredit(),
                LocalDateTime.now()
                );
        if(createAccountRequest.getInitialCredit().compareTo(BigDecimal.ZERO)>0){
            Transaction transaction = transactionService.initiateMoney(account, createAccountRequest.getInitialCredit());
            account.getTransaction().add(transaction);
        }
        //Transaction u hiç kaydetmeden direct account u set etsek bile jpa onu kaydeder zaten
        return converter.convert(accountRepository.save(account));
    }
}
