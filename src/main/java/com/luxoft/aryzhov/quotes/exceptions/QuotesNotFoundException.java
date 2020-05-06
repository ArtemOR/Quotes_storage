package com.luxoft.aryzhov.quotes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class QuotesNotFoundException extends RuntimeException{
    public QuotesNotFoundException() {
        super("No elvls was found", null, false, false);
    }
}
