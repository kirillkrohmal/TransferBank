package com.bank.transfer.service;

import com.bank.transfer.model.entity.Account;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface AccountService {

    List<Account> getAccount();

    void incrementAccountMoney();
}
