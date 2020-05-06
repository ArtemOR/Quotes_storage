package com.luxoft.aryzhov.quotes;

import com.luxoft.aryzhov.quotes.model.entities.Quote;

import java.math.BigDecimal;

public class QuoteBuilder {

   private final Quote quote = new Quote();

   public QuoteBuilder setIsin(String isin) {
      quote.setIsin(isin);
      return this;
   }

   public QuoteBuilder setBid(String bid) {
      quote.setBid(new BigDecimal(bid));
      return this;
   }

   public QuoteBuilder setAsk(String ask) {
      quote.setAsk(new BigDecimal(ask));
      return this;
   }

   public Quote build() {
      return quote;
   }
}
