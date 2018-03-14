package com.dan.group11.cryptosim;

/**
 * Created by Ugo on 12/03/2018.
 *
 * Coin class holds all info on individual cryptocurrencies
 * Each coin has one object
 * object can be replaced or updated, no duplicates
 */

public class Coin {

    private String ID ; //should be unique
    private String name ;
    private String symbol ;
    private int rank ;
    private float price ;
    private int volume24H ;
    private int marketCap ;
    private int availableSupply ;
    private int totalSupply ;
    private int maxSupply ;
    private float percentChange ;

    //Constructor
    Coin( String iID, String nName, String sSymbol, int rRank, float pPrice, int vVolume24H, int mMarketCap, int aAvailableSupply, int tTotalSupply, int mMaxSupply, float pPercentChange ) {
        ID =iID ;
        name =nName ;
        symbol =sSymbol ;
        price =pPrice ;
        rank =rRank ;
        volume24H =vVolume24H ;
        marketCap =mMarketCap ;
        availableSupply =aAvailableSupply ;
        totalSupply =tTotalSupply ;
        maxSupply =mMaxSupply ;
        percentChange =pPercentChange ;
    }

    //Getters
    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public float getPrice() {
        return price;
    }

    public int getRank() {
        return rank;
    }

    public int getVolume24H() {
        return volume24H;
    }

    public int getMarketCap() {
        return marketCap;
    }

    public int getAvailableSupply() {
        return availableSupply;
    }

    public int getTotalSupply() {
        return totalSupply;
    }

    public int getMaxSupply() {
        return maxSupply;
    }

    public float getPercentChange() {
        return percentChange;
    }

}
