package com.luxoft.aryzhov.quotes.service;

import com.luxoft.aryzhov.quotes.exceptions.QuotesNotFoundException;
import com.luxoft.aryzhov.quotes.model.ElvlForResponse;
import com.luxoft.aryzhov.quotes.model.entities.Elvl;
import com.luxoft.aryzhov.quotes.model.entities.Quote;
import com.luxoft.aryzhov.quotes.repository.ElvlRepository;
import com.luxoft.aryzhov.quotes.repository.QouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuoteServiceImplementation implements QuoteService {

    @Autowired
    private ElvlRepository elvlRepository;

    @Autowired
    private QouteRepository qouteRepository;

    @Autowired
    private QuoteServiceImplementationHelper helper;

    @Override
    public ElvlForResponse getElvlByIsin(String isin) {
        Elvl foundElvl = elvlRepository.searchElvlByIsin(isin);
        if (foundElvl == null) {
            throw new QuotesNotFoundException();
        }

        return helper.convertElvl(foundElvl);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ElvlForResponse handleNewQuote(Quote quote) {
        qouteRepository.save(quote);
        Elvl foundElvl = elvlRepository.searchElvlByIsin(quote.getIsin());
        Elvl updatedElvl = helper.prepareElvlForUpsert(quote, foundElvl);
        elvlRepository.save(updatedElvl);

        return helper.convertElvl(updatedElvl);
    }

    @Override
    public List<ElvlForResponse> getAllElvls() {
        List<Elvl> elvls = elvlRepository.findAll();
        if (elvls.isEmpty()) {
            throw new QuotesNotFoundException();
        }

        return helper.convertListOfElvl(elvls);
    }

    //has been made for tests cleanUp
    public void deleteByIsin(String isin) {
        elvlRepository.deleteByIsin(isin);
        qouteRepository.deleteByIsin(isin);
    }
}
