package com.luxoft.aryzhov.quotes.integrationTests;

import com.luxoft.aryzhov.quotes.QuoteBuilder;
import com.luxoft.aryzhov.quotes.model.ElvlForResponse;
import com.luxoft.aryzhov.quotes.model.entities.Quote;
import com.luxoft.aryzhov.quotes.service.QuoteServiceImplementation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static com.luxoft.aryzhov.quotes.TestConstants.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PositiveQuoteRestControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private QuoteServiceImplementation service;

    private final String BID_VAl1 = "10";
    private final String ASK_VAl1 = "11";
    private final String SMALLER_BID_VAl1 = "1";
    private final String SMALLER_ASK_VAl1 = "2";
    private final String BIGGER_BID_VAL1 = "21";
    private final String BIGGER_ASK_VAL1 = "22";

    private final String BID_VAl2 = "1000";
    private final String ASK_VAl2 = "1001";

    @Test
    public void whenAddNewQuoteWithNewIsin_thenNewElvlWithBidValueCreates() {
        //initial verifying
        ResponseEntity<ElvlForResponse> responseResponseEntity = testRestTemplate.getForEntity(BASE_URL + CORRECT_ISIN_VAL1, ElvlForResponse.class);
        assertEquals(responseResponseEntity.getStatusCode(), HttpStatus.NOT_FOUND);

        //prepare data
        Quote quote = new QuoteBuilder().setIsin(CORRECT_ISIN_VAL1).setAsk(ASK_VAl1).setBid(BID_VAl1).build();

        //test
        ResponseEntity<ElvlForResponse> responseEntity = testRestTemplate.postForEntity(BASE_URL, quote, ElvlForResponse.class);

        //verifying
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(BID_VAl1, responseEntity.getBody().getElvl());
        assertEquals(CORRECT_ISIN_VAL1, responseEntity.getBody().getIsin());

        //cleanup
        cleanup(CORRECT_ISIN_VAL1);
    }


    @Test
    public void whenQuoteHasNoBidValue_thenElvlHasAskValue() {
        //prepare data
        Quote quote = new QuoteBuilder().setIsin(CORRECT_ISIN_VAL1).setAsk(ASK_VAl1).build();

        //test
        ResponseEntity<ElvlForResponse> responseEntity = testRestTemplate.postForEntity(BASE_URL, quote, ElvlForResponse.class);

        //verifying
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(ASK_VAl1, responseEntity.getBody().getElvl());
        assertEquals(CORRECT_ISIN_VAL1, responseEntity.getBody().getIsin());

        //cleanup
        cleanup(CORRECT_ISIN_VAL1);
    }

    @Test
    public void whenFindByIsin_thenElvlReturns() {
        //prepare data
        Quote quote1 = new QuoteBuilder().setIsin(CORRECT_ISIN_VAL1).setAsk(ASK_VAl1).setBid(BID_VAl1).build();
        createElvl(quote1);

        //test
        ResponseEntity<ElvlForResponse> responseEntity = testRestTemplate.getForEntity(BASE_URL + CORRECT_ISIN_VAL1, ElvlForResponse.class);

        //verifying
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(BID_VAl1, responseEntity.getBody().getElvl());
        assertEquals(CORRECT_ISIN_VAL1, responseEntity.getBody().getIsin());

        //cleanup
        cleanup(CORRECT_ISIN_VAL1);
    }

    @Test
    public void whenFindByAllIsin_thenListOfElvlReturns() {
        //prepare data
        Quote quote1 = new QuoteBuilder().setIsin(CORRECT_ISIN_VAL1).setAsk(ASK_VAl1).setBid(BID_VAl1).build();
        Quote quote2 = new QuoteBuilder().setIsin(CORRECT_ISIN_VAL2).setAsk(ASK_VAl2).setBid(BID_VAl2).build();
        createElvl(quote1);
        createElvl(quote2);

        //test
        ResponseEntity<ElvlForResponse[]> responseEntity = testRestTemplate.getForEntity(BASE_URL, ElvlForResponse[].class);

        //verifying
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody().length >= 2);

        //cleanup
        cleanup(CORRECT_ISIN_VAL1);
        cleanup(CORRECT_ISIN_VAL2);
    }

    @Test
    public void whenBidMoreThanElvl_thenElvlIsBid() {
        //prepare data
        Quote quote1 = new QuoteBuilder().setIsin(CORRECT_ISIN_VAL1).setAsk(ASK_VAl1).setBid(BID_VAl1).build();
        createElvl(quote1);

        //elvl is "small" bid
        ResponseEntity<ElvlForResponse> initialResponseEntity = testRestTemplate.getForEntity(BASE_URL + CORRECT_ISIN_VAL1, ElvlForResponse.class);
        assertNotNull(initialResponseEntity.getBody());
        assertEquals(BID_VAl1, initialResponseEntity.getBody().getElvl());

        //prepare data with same isin and "big" bid
        Quote quote2 = new QuoteBuilder().setIsin(CORRECT_ISIN_VAL1).setAsk(BIGGER_ASK_VAL1).setBid(BIGGER_BID_VAL1).build();

        //test
        ResponseEntity<ElvlForResponse> responseEntity = testRestTemplate.postForEntity(BASE_URL, quote2, ElvlForResponse.class);

        //verifying
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(BIGGER_BID_VAL1, responseEntity.getBody().getElvl());
        assertEquals(CORRECT_ISIN_VAL1, responseEntity.getBody().getIsin());

        //cleanup
        cleanup(CORRECT_ISIN_VAL1);
    }

    @Test
    public void whenAscLessThanElvl_thenElvlIsASK() {
        //prepare data
        Quote quote1 = new QuoteBuilder().setIsin(CORRECT_ISIN_VAL1).setAsk(ASK_VAl1).setBid(BID_VAl1).build();
        createElvl(quote1);

        //elvl is "small" bid
        ResponseEntity<ElvlForResponse> initialResponseEntity = testRestTemplate.getForEntity(BASE_URL + CORRECT_ISIN_VAL1, ElvlForResponse.class);
        assertNotNull(initialResponseEntity.getBody());
        assertEquals(BID_VAl1, initialResponseEntity.getBody().getElvl());

        //prepare data with same isin and "big" bid
        Quote quote2 = new QuoteBuilder().setIsin(CORRECT_ISIN_VAL1).setAsk(SMALLER_ASK_VAl1).setBid(SMALLER_BID_VAl1).build();

        //test
        ResponseEntity<ElvlForResponse> responseEntity = testRestTemplate.postForEntity(BASE_URL, quote2, ElvlForResponse.class);

        //verifying
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(SMALLER_ASK_VAl1, responseEntity.getBody().getElvl());
        assertEquals(CORRECT_ISIN_VAL1, responseEntity.getBody().getIsin());

        //cleanup
        cleanup(CORRECT_ISIN_VAL1);
    }

    //to verify that for existing Isin runs update operation, but not create new one
    @Test
    public void whenQuoteRunsForExistIsin_thenNewElvlNotCreates() {
        //prepare data
        Quote quote1 = new QuoteBuilder().setIsin(CORRECT_ISIN_VAL1).setAsk(ASK_VAl1).setBid(BID_VAl1).build();
        createElvl(quote1);

        //verify that exist only one record with ISIN_VAL1
        ResponseEntity<ElvlForResponse[]> initialResponseEntity = testRestTemplate.getForEntity(BASE_URL, ElvlForResponse[].class);
        assertEquals(HttpStatus.OK, initialResponseEntity.getStatusCode());
        assertNotNull(initialResponseEntity.getBody());
        long initialNumberOfElvlsWithIsin = Arrays.stream(initialResponseEntity.getBody()).filter(el->el.getIsin().equals(CORRECT_ISIN_VAL1)).count();
        assertEquals(1, initialNumberOfElvlsWithIsin);

        //post new quote with same isin and different ask and bid values
        Quote quote2 = new QuoteBuilder().setIsin(CORRECT_ISIN_VAL1).setAsk(ASK_VAl2).setBid(BID_VAl2).build();
        ResponseEntity<ElvlForResponse> responseEntity = testRestTemplate.postForEntity(BASE_URL, quote2, ElvlForResponse.class);
        assertEquals(HttpStatus.OK, initialResponseEntity.getStatusCode());

        ///verify that exist only one record with ISIN_VAL1
        ResponseEntity<ElvlForResponse[]> newResponseEntity = testRestTemplate.getForEntity(BASE_URL, ElvlForResponse[].class);
        assertEquals(HttpStatus.OK, newResponseEntity.getStatusCode());
        assertNotNull(newResponseEntity.getBody());
        long newNumberOfElvlsWithIsin = Arrays.stream(newResponseEntity.getBody()).filter(el->el.getIsin().equals(CORRECT_ISIN_VAL1)).count();
        assertEquals(initialNumberOfElvlsWithIsin, newNumberOfElvlsWithIsin);

        //cleanup
        cleanup(CORRECT_ISIN_VAL1);
    }

    private void createElvl(Quote quote) {
        ResponseEntity<ElvlForResponse> responseEntity = testRestTemplate.postForEntity(BASE_URL, quote, ElvlForResponse.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    private void cleanup(String isin) {
        service.deleteByIsin(isin);
    }
}
