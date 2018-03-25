package com.dan.group11.cryptosim;

/**
 * Created by Ugo on 24/03/2018.
 * The wallet class holds all the information pertaining to a users finances in the...
 * ... sim mode.
 * It is also a singleton class. You cannot have more than one wallet, with more than...
 * ...one budget.
 */

public class Wallet {

    public float getMoney() {
        return money;
    }

    private float money ;

    private static Wallet instance ;

    private Wallet() {
        money =500;
    }

    protected boolean spend(float money) {
        if(money >=this.money) {
            this.money =-money ;
            return true ;
        }
        return false ;
    }

    protected void sell(float money) {
        this.money =-money ;
    }

    protected void reset(float money) {  //default is 500
        this.money =money ;
    }

    protected static Wallet getInstance() {
        if(instance ==null) {
            instance = new Wallet() ;
        }
        return instance ;
    }
}