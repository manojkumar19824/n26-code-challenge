package com.mkm.model;

import java.io.Serializable;

public class TransactionData implements Serializable {

    private static final long serialVersionUID = 1L;

    public TransactionData() {
    }

    public TransactionData(double amount, long timeStamp) {
        this.amount = amount;
        this.timeStamp = timeStamp;
    }

    private double amount;
    private long timeStamp;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "TransactionData{" +
                "amount=" + amount +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
