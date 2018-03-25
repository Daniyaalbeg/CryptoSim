package com.dan.group11.cryptosim;

import java.util.ArrayList;
import java.util.Map ;
import java.util.HashMap ;

/**
 * Created by Ugo on 24/03/2018.
 * Account holds information about the account and the transactions made by...
 * ...the account holder. Essentially a save file.
 */

public class Account {
    private String userName ;
    private String password ;
    private ArrayList<Transaction> currentTransactionHistory ;
    private float profit ;
    private Map<String, Integer> coinQuantity ;

    Account(String uuserName, String ppassword) {
        userName =uuserName ;
        password =ppassword ;
        coinQuantity =new HashMap<>() ;
        profit =0 ;
        currentTransactionHistory =new ArrayList<>() ;
    }

    public void addToCoinQuantity(String coinName, int amount) {
        if( coinQuantity.containsKey(coinName)) {
            coinQuantity.put(coinName, coinQuantity.get(coinName)+amount) ;
        }
        else {
            coinQuantity.put(coinName, amount) ;
        }
    }

    public int getSpecificCoinQuantity(String coinName) {
        return coinQuantity.get(coinName) ;
    }

    public Map getCoinQuantity() {
        return coinQuantity ;
    }
    public void addToHistory(Transaction transaction) {
        currentTransactionHistory.add(transaction) ;
        profit += transaction.getCost() ;
    }

    public void reset() {
        currentTransactionHistory =new ArrayList<>() ;
        profit =0 ;
    }
}