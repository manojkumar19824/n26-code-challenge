package com.mkm.builder;

import com.mkm.model.TransactionStatistics;



public class TransactionStatisticsBuilder {

    private Double max;
    private Double min;
    private Long count;
    private Double average;
    private Double sum;

    public TransactionStatisticsBuilder withMax(Double max){
        this.max = max;
        return this;
    }
    public TransactionStatisticsBuilder withMin(Double min){
        this.min = min;
        return this;
    }
    public TransactionStatisticsBuilder withCount(Long count){
        this.count = count;
        return this;
    }
    public TransactionStatisticsBuilder withAverage(Double average){
        this.average = average;
        return this;
    }
    public TransactionStatisticsBuilder withSum(Double sum){
        this.sum = sum;
        return this;
    }
    public TransactionStatistics createTransactionStatistics(){
        return new TransactionStatistics(max,min,count,average,sum);
    }
}
