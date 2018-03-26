package com.dan.group11.cryptosim;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ugo on 24/03/2018.
 * This class represents the object that stores all information pertaining to an...
 * ... individual transaction.
 */

public class Transaction implements Serializable{
    private Coin coin ;
    private double amount ;
    private double cost ;
    private String transactionDate ; //DD-MM-YYYY Format
    private String extraInfo ;
    private boolean bought;

    public Transaction(Coin ccoin, double aamount, double ccost, String eextraInfo, boolean bought) {  //if no extra info make argument ""
        coin =ccoin ;
        amount =aamount ;
        cost =ccost ;
        extraInfo =eextraInfo ;
        this.transactionDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        this.bought = bought;
    }

    public Coin getCoin() {
        return coin;
    }

    public double getAmount() {
        return amount;
    }

    public double getCost() {
        return cost;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public boolean isBought() {
        return bought;
    }

    public void setCoin(Coin coin) {
        this.coin = coin;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setTransactionDate() {
        this.transactionDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
}
