package com.mkm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.mkm.exception.ServiceException;
import com.mkm.model.TransactionData;
import com.mkm.model.TransactionStatistics;
import com.mkm.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.jayway.restassured.response.Response;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.port;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class TransactionControllerTest {

    @LocalServerPort
    int port;
    @Autowired
    TransactionService transactionService;

    @Before
    public void setUp(){
      RestAssured.port = port;
    }
    @Test
    public void testCallStat(){
        Response resp = callStatsEndpoint();
       	Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
    }
    @Test
    public void testSaveTransWithOlderTimeStamp() throws JsonProcessingException {
        String transactionDataOlder = new ObjectMapper().writeValueAsString(new TransactionData(11.77, Instant.now().minusSeconds(70).toEpochMilli()));
        Response resp = given().contentType("application/json")
                .body(transactionDataOlder).when().post("/transactions");
        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.SC_NO_CONTENT);
    }
    @Test
    public void testSaveTransWithSuccessTimeStamp() throws JsonProcessingException {
        String transactionData = new ObjectMapper().writeValueAsString(new TransactionData(10.55, Instant.now().minusSeconds(50).toEpochMilli()));
        Response resp = given().contentType("application/json")
                .body(transactionData).when().post("/transactions");
        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.SC_CREATED);
    }
    @Test
    public void testGetTransactionStats() throws IOException {
        transactionService.clearCache();
        Arrays.asList(new TransactionData(20D, getTime(20)), new TransactionData(40D, getTime(30)))
                .stream()
                .forEach(t -> {
                    try {
                        transactionService.saveTransactionData(t);
                    } catch (ServiceException e) {
                        Assertions.fail(e.getMessage());
                    }
                });
        Response resp = callStatsEndpoint();
        TransactionStatistics result = new ObjectMapper().readValue(resp.getBody().asString(), TransactionStatistics.class);
        Assertions.assertThat(result.getAverage()).isEqualTo(30D);
        Assertions.assertThat(result.getMax()).isEqualTo(40D);
        Assertions.assertThat(result.getMin()).isEqualTo(20D);
        Assertions.assertThat(result.getSum()).isEqualTo(60D);
        Assertions.assertThat(result.getCount()).isEqualTo(2L);
    }
    private long getTime(long time){
       		return Instant.now().minusSeconds(time).toEpochMilli();
    }
    private Response callStatsEndpoint() {
       		return given().contentType("application/json")
               			.when().get("/statistics");
    }
}
