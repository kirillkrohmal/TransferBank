package com.bank.transfer.dao;

import com.bank.transfer.model.entity.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<Mail, Long> {

    Optional<Mail> getFirstByEmail (String email);
}
