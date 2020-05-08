package com.luxoft.aryzhov.quotes.service;

import com.luxoft.aryzhov.quotes.model.ElvlForResponse;
import com.luxoft.aryzhov.quotes.model.entities.Quote;

import java.util.List;

public interface QuoteService {

    ElvlForResponse getElvlByIsin(String isin);

    ElvlForResponse handleNewQuote(Quote quote);

    List<ElvlForResponse> getAllElvls();

}
