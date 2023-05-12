package com.bank.transfer.dao;

import com.bank.transfer.model.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Page<Person> findByNameContainingIgnoreCase(Pageable pageable, String name);

    Page<Person> findByBirthday(Pageable pageable, LocalDate birthday);
}
