package com.dan.group11.cryptosim;

/**
 * Created by Ugo on 24/03/2018.
 * This class represents the object that stores all information pertaining to an...
 * ... individual transaction
 */

public class Transaction {
    public Coin getCoin() {
        return coin;
    }

    public int getAmount() {
        return amount;
    }

    public float getCost() {
        return cost;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    private Coin coin ;
    private int amount ;
    private float cost ;
    private String transactionDate ; //DD-MM-YYYY Format
    private String extraInfo ;

    Transaction(Coin ccoin, int aamount, float ccost, String ttransactionDate, String eextraInfo) {  //if no extra info make argument ""
        coin =ccoin ;
        amount =aamount ;
        cost =ccost ;
        transactionDate = ttransactionDate ;
        extraInfo =eextraInfo ;
    }
}
