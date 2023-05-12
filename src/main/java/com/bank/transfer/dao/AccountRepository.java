package com.bank.transfer.dao;

import com.bank.transfer.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE a.person.id = :personId")
    List<Account> getAccountByPersonId(Long personId);
}
