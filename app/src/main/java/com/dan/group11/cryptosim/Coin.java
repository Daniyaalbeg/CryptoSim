package com.dan.group11.cryptosim;

import java.util.Comparator;

/**
 * Created by Ugo on 12/03/2018.
 *
 * Coin class holds all info on individual cryptocurrencies
 * Each coin has one object
 * object can be replaced or updated, no duplicates
 */

public class Coin{

    private String ID ; //should be unique
    private String name ;
    private String symbol ;
    private int rank ;
    private float price ;
    private float volume24H ;
    private long marketCap ;
    private float availableSupply ;
    private float totalSupply ;
    private float maxSupply ;
    private float percentChange ;

    //Constructor
    Coin( String iID, String nName, String sSymbol, int rRank, float pPrice, float vVolume24H, long mMarketCap, float aAvailableSupply, float tTotalSupply, float mMaxSupply, float pPercentChange ) {
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

    public float getVolume24H() {
        return volume24H;
    }

    public long getMarketCap() {
        return marketCap;
    }

    public float getAvailableSupply() {
        return availableSupply;
    }

    public float getTotalSupply() {
        return totalSupply;
    }

    public float getMaxSupply() {
        return maxSupply;
    }

    public float getPercentChange() {
        return percentChange;
    }

    //Comparators are needed to sort collections made up of objects
    public static Comparator<Coin> sortByMarketCap = new Comparator<Coin>() {
        @Override
        public int compare(Coin o1, Coin o2) {
            long marketCap1 =12982198L ;
            long marketCap2 =356363434L ;
            return Math.round(marketCap1) -Math.round(marketCap2) ;
        }
    } ;

    public static Comparator<Coin> sortByVolume = new Comparator<Coin>() {
        @Override
        public int compare(Coin o1, Coin o2) {
            float vol1 =o1.getVolume24H() ;
            float vol2 =o2.getVolume24H() ;
            return Math.round(vol1) -Math.round(vol2) ;
        }
    } ;

    public static Comparator<Coin> sortByPrice = new Comparator<Coin>() {
        @Override
        public int compare(Coin o1, Coin o2) {
            float price1 =o1.getPrice() ;
            float price2 =o2.getPrice() ;
            return Math.round(price1) -Math.round(price2) ;
        }
    } ;

    public static Comparator<Coin> sortByName = new Comparator<Coin>() {
        @Override
        public int compare(Coin o1, Coin o2) {
            String name1 =o1.getName() ;
            String name2 =o2.getName() ;
            return name1.compareTo(name2) ;
        }
    } ;
}
