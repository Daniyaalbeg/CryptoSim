package com.dan.group11.cryptosim.Data;

import com.dan.group11.cryptosim.Coin;
import com.dan.group11.cryptosim.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by daniyaalbeg on 26/03/2018.
 */

public class CoinsOwned {

    List<Transaction> transactions;
    Map<String, Double> coinsOwned;

    public CoinsOwned(List<Transaction> transactions) {
        this.transactions = transactions;
        coinsOwned = new HashMap();
    }

    public Map<String, Double> getCoinsOwned() {
        for (Transaction transaction: transactions) {
            if (transaction.isBought()) {
                if (coinsOwned.containsKey(transaction.getCoin().getName())) {
                    coinsOwned.put(transaction.getCoin().getName(), coinsOwned.get(transaction.getCoin().getName()) + transaction.getAmount());
                } else {
                    coinsOwned.put(transaction.getCoin().getName(), transaction.getAmount());
                }
            } else {
                if (coinsOwned.containsKey(transaction.getCoin().getName())) {
                    coinsOwned.put(transaction.getCoin().getName(), coinsOwned.get(transaction.getCoin().getName()) - transaction.getAmount());
                } else {
                    coinsOwned.put(transaction.getCoin().getName(), -transaction.getAmount());
                } 
            }
        }
        if (coinsOwned.isEmpty()) {
            return null;
        }
        return coinsOwned;
    }
}
