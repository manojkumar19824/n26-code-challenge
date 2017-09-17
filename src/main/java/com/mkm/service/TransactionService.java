package com.mkm.service;

import com.mkm.exception.ServiceException;
import com.mkm.model.TransactionData;
import com.mkm.model.TransactionStatistics;

import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;

public interface TransactionService {

    public void saveTransactionData(TransactionData transactionData) throws ServiceException;
    public TransactionStatistics getStatistics(int inSeconds) throws ServiceException;
    public void clearCache();
    public ConcurrentNavigableMap<Long, List<TransactionData>> getTransaction();
}
