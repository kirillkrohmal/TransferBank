package com.bank.transfer.service;

import com.bank.transfer.dao.AccountRepository;
import com.bank.transfer.dao.EmailRepository;
import com.bank.transfer.dao.PersonRepository;
import com.bank.transfer.dao.PhoneRepository;
import com.bank.transfer.exception.ExceptionMessageCreator;
import com.bank.transfer.exception.PersonException;
import com.bank.transfer.model.dto.PersonDto;
import com.bank.transfer.model.entity.Account;
import com.bank.transfer.model.entity.Mail;
import com.bank.transfer.model.entity.Person;
import com.bank.transfer.model.entity.Phone;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.bank.transfer.model.extrasupport.ServiceConstants.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PhoneRepository phoneRepository;
    private final EmailRepository emailRepository;
    private final AccountRepository accountRepository;
    private final ExceptionMessageCreator messageCreator;
    private final ModelMapper modelMapper;

    @Override
    public Person getPersonByPhone(String phone) {
        Phone p = phoneRepository.getByPhone(phone).orElseThrow(() -> PersonException.of(messageCreator.createMessage(PHONE_NOT_FOUND)));

        return p.getPerson();
    }

    @Override
    public Person getPersonByMail(String email) {
        Mail m = emailRepository.getFirstByEmail(email).orElseThrow(() -> PersonException.of(messageCreator.createMessage(MAIL_NOT_FOUND)));

        return m.getPerson();
    }

    @Override
    @Transactional
    public Person registerPerson(PersonDto personDto) {
        Person person = modelMapper.map(personDto, Person.class);
        return personRepository.saveAndFlush(person);
    }

    @Override
    public Page<Person> getPersonsByName(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size);
        return personRepository.findByNameContainingIgnoreCase(pageable, name);
    }

    @Override
    public boolean moneyTransfer(long sourceId, long recipientId, float amount) {
        Account sender ;
        Account recipient;

        if(Float.isInfinite(amount))
            throw PersonException.of(messageCreator.createMessage(AMOUNT_IS_INFINITE));

        if(Float.isNaN(amount))
            throw PersonException.of(messageCreator.createMessage(AMOUNT_IS_NAN));

        BigDecimal amountAsBigDecimal = new BigDecimal(amount);

        List<Account> senderAccounts = accountRepository.getAccountByPersonId(sourceId);
        List<Account> recipientAccounts = accountRepository.getAccountByPersonId(recipientId);

        if (senderAccounts.isEmpty()) {
            throw PersonException.of(messageCreator.createMessage(SENDER_ID_ACCOUNT_NOT_FOUND));
        } else {
            sender = senderAccounts.get(0);
        }

        if (recipientAccounts.isEmpty()) {
            throw PersonException.of(messageCreator.createMessage(RECIPIENT_ID_ACCOUNT_NOT_FOUND));
        } else {
            recipient = recipientAccounts.get(0);
        }

        if (sender.getBalance().compareTo(amountAsBigDecimal) >= 0 ) {
            sender.getAccountLock().writeLock().lock();
            recipient.getAccountLock().writeLock().lock();
            sender.setBalance(sender.getBalance().subtract(amountAsBigDecimal));
            sender.getAccountLock().writeLock().unlock();
            recipient.setBalance(recipient.getBalance().add(amountAsBigDecimal));
            recipient.getAccountLock().writeLock().unlock();
            return true;
        } else {
            throw PersonException.of(messageCreator.createMessage(NOT_ENOUGH_FUNDS));
        }
    }

    @Override
    public Page<Person> getPersonsByBirthdate(int page, int size, LocalDate birthday) {
        Pageable pageable = PageRequest.of(page, size);

        return personRepository.findByBirthday(pageable, birthday);
    }
}
