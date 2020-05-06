package com.luxoft.aryzhov.quotes.integrationTests;

import com.luxoft.aryzhov.quotes.QuoteBuilder;
import com.luxoft.aryzhov.quotes.TestConstants;
import com.luxoft.aryzhov.quotes.model.entities.Quote;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashMap;

import static com.luxoft.aryzhov.quotes.TestConstants.BASE_URL;
import static com.luxoft.aryzhov.quotes.TestConstants.CORRECT_ISIN_VAL1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NegativeQuoteRestControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private final String ISIN_NOT_EXIST = "ISINNOTEXIST";
    private final String BID_VAl1 = "10.01";
    private final String ASK_LESS_THAN_BID_VAl1 = "0.02";

    private final String MESSAGE = "message";

    private final String ELVL_NOT_FOUND = "No elvls was found";

    @Test
    public void whenIsinHasWrongFormat_thenExceptionIsThrows() {
        //test
        ResponseEntity<LinkedHashMap> responseEntity = testRestTemplate.getForEntity(BASE_URL + TestConstants.ISIN_WRONG_VALUE, LinkedHashMap.class);

        //verifying
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getBody().get(MESSAGE), TestConstants.CHECK_THE_PARAMS);

    }

    @Test
    public void whenAskLessThenBid_thenExceptionIsThrows() {
        //prepare data
        Quote quote = new QuoteBuilder().setIsin(CORRECT_ISIN_VAL1).setAsk(ASK_LESS_THAN_BID_VAl1).setBid(BID_VAl1).build();

        //test
        ResponseEntity<LinkedHashMap> responseEntity = testRestTemplate.postForEntity(BASE_URL, quote, LinkedHashMap.class);

        //verifying
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getBody().get(MESSAGE), TestConstants.CHECK_THE_PARAMS);
    }

    @Test
    public void whenAskIsNotSpecified_thenExceptionIsThrows() {
        //prepare data
        Quote quote = new QuoteBuilder().setIsin(CORRECT_ISIN_VAL1).setBid(BID_VAl1).build();

        //test
        ResponseEntity<LinkedHashMap> responseEntity = testRestTemplate.postForEntity(BASE_URL, quote, LinkedHashMap.class);

        //verifying
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getBody().get(MESSAGE), TestConstants.CHECK_THE_PARAMS);
    }

    @Test
    public void whenIsinIsNotFound_thenExceptionIsThrows() {
        //test
        ResponseEntity<LinkedHashMap> responseEntity = testRestTemplate.getForEntity(BASE_URL + ISIN_NOT_EXIST, LinkedHashMap.class);

        //verifying
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getBody().get(MESSAGE), ELVL_NOT_FOUND);

    }

}
