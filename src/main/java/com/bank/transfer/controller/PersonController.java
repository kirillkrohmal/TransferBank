package com.bank.transfer.controller;

import com.bank.transfer.model.dto.PersonDto;
import com.bank.transfer.model.entity.Person;
import com.bank.transfer.service.PersonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import javax.validation.Valid;
import java.time.LocalDate;

@Validated
@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping("/getbyname")
    public ResponseEntity<Page<Person>> getPersonByName(@RequestParam int page, @RequestParam int size, @RequestParam String name) {
        return ResponseEntity.ok().body(personService.getPersonsByName(page, size, name));
    }

    @GetMapping("/getbyphone/{phone}")
    public ResponseEntity<Person> getPersonByPhone(@PathVariable String phone) {
        return ResponseEntity.ok().body(personService.getPersonByPhone(phone));
    }

    @GetMapping("/getbymail/{mail}")
    public ResponseEntity<Person> getPersonByMail(@PathVariable String mail) {
        return ResponseEntity.ok().body(personService.getPersonByMail(mail));
    }

    @GetMapping("/getbybirthdate")
    public ResponseEntity<Page<Person>> getPersonByBirthday(@RequestParam int page, @RequestParam int size, @RequestParam  @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate birthdate) {
        return ResponseEntity.ok().body(personService.getPersonsByBirthdate(page, size, birthdate));
    }

    @PostMapping("/registerclient")
    public ResponseEntity<Person> createClient(@RequestBody @Valid PersonDto personDto) {
        return new ResponseEntity<>(personService.registerPerson(personDto), HttpStatus.CREATED);
    }

    @PutMapping("/moneytransfer")
    public ResponseEntity<Boolean> moneyTransfer(@RequestParam long sourceId, @RequestParam long recipientId, @RequestParam  float amount) {
        return ResponseEntity.ok(personService.moneyTransfer(sourceId, recipientId, amount));
    }
}
