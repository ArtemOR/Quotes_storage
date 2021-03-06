package com.luxoft.aryzhov.quotes.integrationTests;

import com.luxoft.aryzhov.quotes.QuoteBuilder;
import com.luxoft.aryzhov.quotes.model.ElvlForResponse;
import com.luxoft.aryzhov.quotes.model.entities.Quote;
import com.luxoft.aryzhov.quotes.service.QuoteServiceImplementation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static com.luxoft.aryzhov.quotes.TestConstants.BASE_URL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PerformanceQuoteRestControllerTest {

    private final Logger LOGGER = LoggerFactory.getLogger(PerformanceQuoteRestControllerTest.class);

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private QuoteServiceImplementation service;

    private final String ISIN_VAL_TEMPLATE = "ELVL123";
    List<Quote> quotes;

    @Test
    public void whenHandles100Operations_thenItTakesLessThanSecond() {
        //prepare data
        quotes = createListOf1000QuotesWithDifferentIsin();

        List<ResponseEntity<ElvlForResponse>> responseEntityList = new ArrayList<>();
        warmUp();

        //test
        long start = System.currentTimeMillis();
        quotes.forEach(quote -> responseEntityList.add(testRestTemplate.postForEntity(BASE_URL, quote, ElvlForResponse.class)));
        long end = System.currentTimeMillis();

        long timeFor100Operations = (end - start) / 10;
        LOGGER.info(" ");
        LOGGER.info("==================================================");
        LOGGER.info("Time for 100 operations = " + timeFor100Operations + "ms.");
        LOGGER.info("==================================================");
        LOGGER.info(" ");

        //cleanup
        cleanUp();

        //verifying
        responseEntityList.forEach(re -> assertEquals(HttpStatus.OK, re.getStatusCode()));

    }

    private List<Quote> createListOf1000QuotesWithDifferentIsin() {
        List<Quote> quotes = new ArrayList<>();
        int bid = 2;
        int ask = 3;
        int initNumberOfIsin = 10000;

        for (int i = 0; i < 1000; i++) {
            quotes.add(new QuoteBuilder().setIsin(ISIN_VAL_TEMPLATE + (initNumberOfIsin + i)).setAsk(String.valueOf(ask++)).setBid(String.valueOf(bid++)).build());
        }

        return quotes;
    }

    private void warmUp() {
        quotes.forEach(quote -> testRestTemplate.postForEntity(BASE_URL, quote, ElvlForResponse.class));
    }

    private void cleanUp() {
        int initNumberOfIsin = 10000;
        for (int i = 0; i < 1000; i++) {
            service.deleteByIsin(ISIN_VAL_TEMPLATE + (initNumberOfIsin + i));
        }
    }

}
