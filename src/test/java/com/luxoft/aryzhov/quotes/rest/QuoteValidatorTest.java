package com.luxoft.aryzhov.quotes.rest;

import com.luxoft.aryzhov.quotes.TestConstants;
import com.luxoft.aryzhov.quotes.exceptions.QuotesValidationException;
import com.luxoft.aryzhov.quotes.QuoteBuilder;
import com.luxoft.aryzhov.quotes.model.entities.Quote;
import org.junit.Test;

import static com.luxoft.aryzhov.quotes.TestConstants.CHECK_THE_PARAMS;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.fail;

public class QuoteValidatorTest {

    private final QuoteValidator undertest = new QuoteValidator();

    @Test
    public void testValidateIsin_whenIsinHasWhongFormat_ThenExceptionIsThrown() {
        assertThatThrownBy(() -> undertest.validateIsin(TestConstants.ISIN_WRONG_VALUE))
                .isInstanceOf(QuotesValidationException.class).hasMessageContaining(CHECK_THE_PARAMS);
    }

    @Test
    public void testValidateIsin_whenIsinHasCorrectFormat_ThenNoAnyExceptionIsThrown() {
        try {
            undertest.validateIsin(TestConstants.CORRECT_ISIN_VAL1);
        } catch (Exception e) {
            fail("No exception is expected");
        }
    }

    @Test
    public void testValidateQuote_whenQuoteIsNull_ThenExceptionIsThrown() {
        assertThatThrownBy(() -> undertest.validateQuote(null))
                .isInstanceOf(QuotesValidationException.class).hasMessageContaining(CHECK_THE_PARAMS);
    }

    @Test
    public void testValidateQuote_whenAskIsNull_ThenExceptionIsThrown() {
        Quote quote = new QuoteBuilder().setBid("123").setIsin(TestConstants.CORRECT_ISIN_VAL1).build();
        assertThatThrownBy(() -> undertest.validateQuote(null))
                .isInstanceOf(QuotesValidationException.class).hasMessageContaining(CHECK_THE_PARAMS);
    }

    @Test
    public void testValidateQuote_whenAskIsLessThanBid_ThenExceptionIsThrown() {
        Quote quote = new QuoteBuilder().setBid("1234").setAsk("123").setIsin(TestConstants.CORRECT_ISIN_VAL1).build();
        assertThatThrownBy(() -> undertest.validateQuote(quote))
                .isInstanceOf(QuotesValidationException.class).hasMessageContaining(CHECK_THE_PARAMS);
    }

    @Test
    public void testValidateQuote_whenQuoteIsFine_ThenNoAnyExceptionIsThrown() {
        Quote quote = new QuoteBuilder().setBid("123").setAsk("124").setIsin(TestConstants.CORRECT_ISIN_VAL1).build();
        try {
            undertest.validateQuote(quote);
        } catch (Exception e) {
            fail("No exception is expected");
        }
    }

}
