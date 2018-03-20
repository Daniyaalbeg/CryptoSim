package com.dan.group11.cryptosim ;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import jsonParsing.JSONParser;

/**
 * Created by Ugo on 11/03/2018
 *
 * This class gets info on coins from API
 * Can get info for many coins (how many is our choice) or for an individual one
 * Every time the user wants to refresh info or app is closed and opened again a new CoinMarketAPI
 * object should be made since you can only call the constructor once.
 *
 */

public class CoinMarketAPI {

    private JSONArray coins ; //JSONArray holds JSON data in arrayList-like format

    CoinMarketAPI(int amount) {
        String url =("https://api.coinmarketcap.com/v1/ticker/?start=0&limit="+amount);
        //url is API URL, has limit variable to dictate how many coins are needed for display
        JSONParser jParser =new JSONParser() ;
        try {
            coins = jParser.getJSONFromUrl(url); //gets and parses JSON file from url
        }
        catch(JSONException | IOException e1) {
            Log.e("url conversion", "Error converting url to JSONArray" + e1.toString()) ;
        }
    }

    public ArrayList<Coin> getAllCoinData() { //gets arraylist of Coin objects
        ArrayList<Coin> returnVal = new ArrayList<>() ; //arraylist to return
        try {
            for (int i = 0; i < coins.length(); i++) { //for every coin in the JSONArray coins...
                JSONObject temp = coins.getJSONObject(i); //...make a JSONObject of it and...
                if(temp.getString("max_supply")!=null) { //Max supply can be null
                    returnVal.add(new Coin( // ...'translate' that into a coin object before adding it to returnVal
                            temp.getString("id"),
                            temp.getString("name"),
                            temp.getString("symbol"),
                            Integer.parseInt(temp.getString("rank")),
                            Float.parseFloat(temp.getString("price_usd")),
                            Float.parseFloat(temp.getString("24h_volume_usd")),
                            (long)(Double.parseDouble(temp.getString("market_cap_usd"))),
                            Float.parseFloat(temp.getString("available_supply")),
                            Float.parseFloat(temp.getString("total_supply")),
                            -1,
                            Float.parseFloat(temp.getString("percent_change_24h"))
                    )) ;
                }
                else {
                    returnVal.add(new Coin( // ...'translate' that into a coin object before adding it to returnVal
                            temp.getString("id"),
                            temp.getString("name"),
                            temp.getString("symbol"),
                            Integer.parseInt(temp.getString("rank")),
                            Float.parseFloat(temp.getString("price_usd")),
                            Float.parseFloat(temp.getString("24h_volume_usd")),
                            (long) (Double.parseDouble(temp.getString("market_cap_usd"))),
                            Float.parseFloat(temp.getString("available_supply")),
                            Float.parseFloat(temp.getString("total_supply")),
                            Float.parseFloat(temp.getString("max_supply")),
                            Float.parseFloat(temp.getString("percent_change_24h"))
                    ));
                }
            }
        }
        catch(JSONException e) { //Something went wrong accessing values in JSONArray coins
            Log.e("JSON Array Values", "Error getting values from JSONArray/JSONObject" + e.toString()) ;
        }
        return returnVal ; //returns arraylist
    }

    public Coin getCoinData(String ID) { //Gets coin object for given ID
        String url =("https://api.coinmarketcap.com/v1/ticker/"+ID+"/") ; //API URL for specific currency

        JSONParser jParser =new JSONParser() ;

        try {
            JSONObject temp =jParser.getJSONObjFromUrl(url) ;
            return new Coin(
                    temp.getString("id"),
                    temp.getString("name"),
                    temp.getString("symbol"),
                    Integer.parseInt(temp.getString("rank")),
                    Float.parseFloat(temp.getString("price_usd")),
                    Float.parseFloat(temp.getString("24h_volume_usd")),
                    Long.parseLong(temp.getString("market_cap_usd")),
                    Float.parseFloat(temp.getString("available_supply")),
                    Float.parseFloat(temp.getString("total_supply")),
                    Float.parseFloat(temp.getString("max_supply")),
                    Float.parseFloat(temp.getString("percent_change_24h"))
            ) ;
        }
        catch(JSONException |IOException e){
            Log.e("JSON Array Values", "Error getting values from JSONObject" + e.toString()) ;
            return null ;
        }
    }
}
