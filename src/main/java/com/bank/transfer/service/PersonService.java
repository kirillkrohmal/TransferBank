package com.bank.transfer.service;

import com.bank.transfer.model.dto.PersonDto;
import com.bank.transfer.model.entity.Person;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
public interface PersonService {


    @Cacheable(value = "clientByPhoneCache", key = "#phone"/*,condition = "#phone.equals(...)"*/)
    Person getPersonByPhone(String phone);

    Person getPersonByMail(String mail);


    Person registerPerson(PersonDto personDto);

    Page<Person> getPersonsByName(int page, int size, String name);


    boolean moneyTransfer(long sourceId, long recipientId, float amount);

    Page<Person> getPersonsByBirthdate(int page, int size, LocalDate birthData);
}
