package com.bank.transfer.service;

import com.bank.transfer.dao.AccountRepository;
import com.bank.transfer.model.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    private List<Account> initAccounts = new ArrayList<>();

    private final BigDecimal balanceLimit = new BigDecimal(2.07);
    private final BigDecimal coefficient = new BigDecimal(1.1);

    private List<BigDecimal> accountsForMoney = new ArrayList<>();

    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
        initAccount();
    }

    @Override
    public List<Account> getAccount() {
        return repository.findAll();
    }

    @Override
    @Scheduled(fixedRateString = "${scheduler.interval}", initialDelayString =  "${scheduler.interval}")
    @Async
    public void incrementAccountMoney() {
        List<Account> accounts = getAccount();

        accounts.forEach(account-> {
            BigDecimal newBalance = account.getBalance().multiply(coefficient);
            if (!(newBalance.compareTo(accountsForMoney.get(accounts.indexOf(account))) > 0)){
                account.setBalance(newBalance);
                log.info("person ID - {}, current money - {}, changed at - {}", account.getPerson().getId(), account.getBalance().setScale(2, RoundingMode.HALF_UP), formatEventDate());
            }
        });
    }

    private void initAccount(){
        initAccounts = getAccount();
        accountsForMoney = initAccounts.stream()
                .map(account -> account.getBalance().multiply(balanceLimit))
                .collect(Collectors.toList());
        initAccounts.forEach(account->
                log.info("person ID - {}, initial money - {}, started at - {}", account.getPerson().getId(), account.getBalance().setScale(2, RoundingMode.HALF_UP), formatEventDate()));
    }

    private  String formatEventDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
        return formatter.format(LocalDateTime.now());
    }
}
