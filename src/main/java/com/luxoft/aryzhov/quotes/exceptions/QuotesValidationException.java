package com.luxoft.aryzhov.quotes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class QuotesValidationException extends RuntimeException{
    public QuotesValidationException() {
        super("Please check the params", null, false, false);
    }
}
