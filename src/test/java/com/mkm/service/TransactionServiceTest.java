package com.mkm.service;

import com.mkm.exception.ServiceException;
import com.mkm.model.TransactionData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    TransactionService transactionService;
    TransactionData transactionData;

    @Before
    public void setUp() throws ServiceException {
        transactionData = new TransactionData(50.55D, 2236781679L);
        transactionService.saveTransactionData(transactionData);
    }

    @Test
    public void testTrasactionsNotEmpty(){
        assertThat(transactionService.getTransaction()).isNotEmpty();
    }

    @Test
    public void testTransactionListSize(){
        assertThat(transactionService.getTransaction().size()).isEqualTo(1);
    }

    @Test
    public void testAddSameTimeStamp() throws ServiceException {
        TransactionData newTransaction = new TransactionData(20D, 2236781679L);
        transactionService.saveTransactionData(newTransaction);

        assertThat(transactionService.getTransaction().size()).isEqualTo(1);
    }

    @Test
    public void testAddTransactionSameTimestampListSize() throws ServiceException {
        TransactionData newTransaction = new TransactionData(20D, 2236781679L);
        transactionService.saveTransactionData(newTransaction);
        assertThat(transactionService.getTransaction().get(2236781679L).size()).isEqualTo(2);
    }

    @After
    public void coolDown() {
        transactionService.clearCache();
    }


}
