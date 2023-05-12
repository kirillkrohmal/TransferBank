package com.bank.transfer.dao;

import com.bank.transfer.model.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
    Optional<Phone> getByPhone (String phone);
}
