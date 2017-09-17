package com.mkm.controller;

import lombok.extern.slf4j.Slf4j;
import com.mkm.exception.ServiceException;
import com.mkm.model.TransactionData;
import com.mkm.model.TransactionStatistics;
import com.mkm.service.TransactionService;
import com.mkm.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(path="/transactions", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> postTransaction(@RequestBody TransactionData transactionData,@Value("${transaction.older.in.seconds}") int olderInSecond)  {
        try {
            log.info("Transaction request coming for adding transaction{}{}",transactionData,olderInSecond);
            transactionService.saveTransactionData(transactionData);
            return DateTimeUtil.isTransactionOlder(transactionData.getTimeStamp(),olderInSecond)?ResponseEntity.
                    status(HttpStatus.NO_CONTENT).build():ResponseEntity.status(HttpStatus.CREATED).build();
        }catch(ServiceException se){
            log.error("Exception occured while processing request{}",se.getErrorMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }

    @GetMapping(path="/statistics", produces = "application/json")
    public ResponseEntity<?> getStatistics(@Value("${transaction.older.in.seconds}") int olderInSecond) {
        try {
            log.info("Request coming for fetching the transaction stat. for last 60 seconds{}",olderInSecond);
            return ResponseEntity.ok(transactionService.getStatistics(olderInSecond));
        }catch (ServiceException se){
            log.error("Exception occured while processing request{}",se.getErrorMessage());
            return ResponseEntity.ok(se.getErrorMessage());
        }
    }
}
