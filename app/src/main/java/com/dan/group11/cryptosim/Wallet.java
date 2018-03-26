package com.dan.group11.cryptosim;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ugo on 24/03/2018.
 * The wallet class holds all the information pertaining to a users finances in the...
 * ... sim mode.
 */

public class Wallet implements Serializable{

    private double money ;
    private List<Transaction> transactions;

//    private static Wallet instance ;

    public Wallet(double money) {
        transactions = new ArrayList<>();
        this.money = money;
//        transactions.add(new Transaction(
//                new Coin("bitcoin", "Bitcoin", "BTC", 1, 573.2, 1.0, 72855700, 9080883500.0, 15844176.0, 15844176.0, 0.04),
//                0.4,
//                12.543,
//                "hello"
//        ));
//        transactions.add(new Transaction(
//                new Coin("bitcoin", "Bitcoin", "BTC", 1, 573.2, 1.0, 72855700, 9080883500.0, 15844176.0, 15844176.0, 0.04),
//                0.4,
//                12.543,
//                "hello"
//        ));
    }

    public boolean spend(double money) {
        if(money <= this.money) {
            this.money = this.money - money;
            return true;
        }
        return false;
    }

    public void sell(double money) {
        this.money =-money ;
    }

    public void reset(double money) {  //default is 500
        this.money =money ;
    }

    public double getMoney() {
        return money;
    }

    public List<Transaction> getTransactions() {
//        if (transactions.size() == 0 || transactions == null) {
//            transactions.add(new Transaction(
//                new Coin("bitcoin", "Bitcoin", "BTC", 1, 573.2, 1.0, 72855700, 9080883500.0, 15844176.0, 15844176.0, 0.04),
//                0.4,
//                12.543,
//                "hello"
//            ));
//            return null;
//        }
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        System.out.println("ADDED TRANSACTION");
        System.out.println(transaction.getCoin().getName());
    }


    public void setMoney(double money) {
        this.money = money;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}