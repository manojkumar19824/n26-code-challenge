package com.mkm.model;

import java.io.Serializable;


public class TransactionStatistics implements Serializable {

    private Double max;
    private Double min;
    private Long count;
    private Double average;
    private Double sum;

    public TransactionStatistics() {
    }

    public TransactionStatistics(Double max, Double min, Long count, Double average, Double sum) {
        this.max = max;
        this.min = min;
        this.count = count;
        this.average = average;
        this.sum = sum;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }


}
