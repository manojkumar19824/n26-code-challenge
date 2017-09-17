package com.mkm.service;

import com.mkm.builder.TransactionStatisticsBuilder;
import com.mkm.exception.ServiceException;
import com.mkm.model.TransactionData;
import com.mkm.model.TransactionStatistics;
import com.mkm.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private ConcurrentNavigableMap<Long, List<TransactionData>> transactionCache;

    @Autowired
    public TransactionServiceImpl() {
        this.transactionCache = new ConcurrentSkipListMap<>();;
    }
    @Override
    public synchronized void clearCache(){
        transactionCache.clear();
    }

    @Override
    public ConcurrentNavigableMap<Long, List<TransactionData>> getTransaction() {
        return transactionCache;
    }

    @Override
    public void saveTransactionData(TransactionData transactionData) throws ServiceException {
        log.info("Transaction coming for adding: {}",transactionData);
        List<TransactionData> existingTrans = transactionCache.get(transactionData.getTimeStamp());
        synchronized (transactionData){
            if(existingTrans == null) existingTrans = new ArrayList<>();
            existingTrans.add(transactionData);
            transactionCache.put(transactionData.getTimeStamp(),existingTrans);
        }
    }

    private List<TransactionData> fetchHistoricalDataForThePastSeconds(long seconds){
       return transactionCache.tailMap(DateTimeUtil.secondsAgoTime(seconds))
                .values().stream()
                .flatMap(t -> t.stream())
                .collect(Collectors.toList());
    }
    @Override
    public TransactionStatistics getStatistics(int olderInSecond) throws ServiceException {
        TransactionStatistics transactionStatistics = null;
        log.info("Request coming for getting the statistics");
        if(transactionCache.size() >0){
            List<TransactionData> transactionData = fetchHistoricalDataForThePastSeconds(olderInSecond);
            log.info("Transaction data for the last 60 second is {}",transactionData);
            if(transactionData != null && transactionData.size() > 0) {
                synchronized (transactionData) {
                    DoubleSummaryStatistics stats =
                            transactionData.parallelStream().collect(Collectors.summarizingDouble(TransactionData::getAmount));
                    transactionStatistics = new TransactionStatisticsBuilder().withMin(stats.getMin()).withCount(stats.getCount())
                            .withMax(stats.getMax()).withAverage(stats.getAverage()).withSum(stats.getSum())
                            .createTransactionStatistics();
                }
            }else {
                throw new ServiceException("There is no transactions available for the last 60 seconds");
            }

        }else {
            throw new ServiceException("There is no transactions available.");
        }
        return transactionStatistics;
    }
}
