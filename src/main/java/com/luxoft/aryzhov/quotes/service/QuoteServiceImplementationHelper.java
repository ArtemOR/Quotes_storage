package com.luxoft.aryzhov.quotes.service;

import com.luxoft.aryzhov.quotes.model.ElvlForResponse;
import com.luxoft.aryzhov.quotes.model.entities.Elvl;
import com.luxoft.aryzhov.quotes.model.entities.Quote;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuoteServiceImplementationHelper {

    Elvl prepareElvlForUpsert(Quote quote, Elvl elvlForUpsert) {
        if(elvlForUpsert ==null){
            elvlForUpsert = new Elvl();
            elvlForUpsert.setIsin(quote.getIsin());
        }

        BigDecimal ask = quote.getAsk();
        BigDecimal bid = quote.getBid();

        if (bid == null || (elvlForUpsert.getElvl() != null && ask.compareTo(elvlForUpsert.getElvl()) < 0)) {
            elvlForUpsert.setElvl(ask);
        } else {
            elvlForUpsert.setElvl(bid);
        }

        return elvlForUpsert;
    }

    ElvlForResponse convertElvl(final Elvl elvl) {
        ElvlForResponse response = new ElvlForResponse();
        response.setIsin(elvl.getIsin());
        response.setElvl(elvl.getElvl().stripTrailingZeros().toPlainString());

        return response;
    }

    List<ElvlForResponse> convertListOfElvl(List<Elvl> elvl) {
        return elvl.stream().map(this::convertElvl).collect(Collectors.toList());
    }

}
