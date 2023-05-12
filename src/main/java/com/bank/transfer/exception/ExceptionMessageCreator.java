package com.bank.transfer.exception;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@AllArgsConstructor
public class ExceptionMessageCreator {
    private final MessageSource messageSource;

    private final Locale locale;
    public String createMessage(String code) {
        return messageSource.getMessage(code, null, locale);
    }

}
