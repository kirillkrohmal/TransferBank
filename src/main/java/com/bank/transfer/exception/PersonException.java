package com.bank.transfer.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PersonException extends RuntimeException{
    private final String message;

    public static PersonException of(String message) {
        return new PersonException(message);
    }

}
