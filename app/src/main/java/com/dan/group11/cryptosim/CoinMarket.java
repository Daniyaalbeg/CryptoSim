package com.dan.group11.cryptosim;

import java.util.ArrayList ;
import java.util.Collections ;

/**
 * Created by Ugo on 17/03/2018.
 */

enum SortBy {
    byMarketCap, byVolume, byPrice, byRank, byName //Different attributes the coins can be sorted by
}

public class CoinMarket {

    protected ArrayList<Coin> getCoins() {
        return coins;
    }

    protected ArrayList<ArrayList<Coin>> getCoinHistory() {
        return coinHistory;
    }

    private ArrayList<Coin> coins;
    private CoinMarketAPI coinMarketAPI;
    private ArrayList<ArrayList<Coin>> coinHistory;
    private static CoinMarket instance;


    private CoinMarket() { //Only ever called once - hence private
        coinHistory = new ArrayList<ArrayList<Coin>>();
        coinMarketAPI = new CoinMarketAPI(100);
        coins = coinMarketAPI.getAllCoinData();
    }

    protected Coin viewCoinInfo(String coinName) { //Will find and return specific coin
        coinName = coinName.toLowerCase();
        return coinMarketAPI.getCoinData(coinName);
    }

    protected void updateCoinInfo() {
        if (coins != null) {
            coinHistory.add(coins); //adds current coins arraylist to history
        }
        coinMarketAPI = new CoinMarketAPI(100); //refreshes data
        coins = coinMarketAPI.getAllCoinData();
    }

    protected ArrayList<Coin> searchCoin(String coinID) { //Gets arraylist with coins whose names match search term
        ArrayList<Coin> returnVal = new ArrayList<>();
        for (int i = 0; i < coins.size(); i++) {
            if (coins.get(i).getID().contains((coinID).toLowerCase())) {
                returnVal.add(coins.get(i));
            }
        }
        return returnVal;
    }

    protected void sortCoins(SortBy sortBy) {
        switch (sortBy) {
            case byPrice:
                Collections.sort(coins, Coin.sortByPrice);

            case byVolume:
                Collections.sort(coins, Coin.sortByVolume);

            case byMarketCap:
                Collections.sort(coins, Coin.sortByMarketCap);

            case byRank:
                updateCoinInfo();

            case byName:
                Collections.sort(coins, Coin.sortByName);
        }
    }

    protected ArrayList<Coin> filterCoins(int minPrice, int maxPrice, int minVol, int maxVol, int minMarketCap, int maxMarketCap) { //If user does not enter a minimum or maximum, then set them to 0 and Integer.MAX_VALUE respectively
        ArrayList<Coin> returnVal = new ArrayList<>();
        for (int i = 0; i < coins.size(); i++) {
            if (coins.get(i).getPrice() >= minPrice && coins.get(i).getPrice() <= maxPrice) {
                if (coins.get(i).getVolume24H() > minVol && coins.get(i).getVolume24H() < maxVol) {
                    if (coins.get(i).getMarketCap() > minMarketCap && coins.get(i).getMarketCap() < maxMarketCap) {
                        returnVal.add(coins.get(i));
                    }
                }
            }
        }
        return returnVal;
    }

    protected static CoinMarket getInstance() {
        if (instance == null) {
            instance = new CoinMarket();
        }
        return instance;
    }
}