package com.dan.group11.cryptosim.Data;

import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dan.group11.cryptosim.Adapter.CoinAdapter;
import com.dan.group11.cryptosim.Coin;
import com.dan.group11.cryptosim.CoinMarket;
import com.dan.group11.cryptosim.CoinMarketAPI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniyaalbeg on 25/03/2018.
 */

public class CoinPriceGetter extends Activity implements Runnable {

    List<Coin> coins;
    CoinAdapter adapter;
    ListView listView;
    Context context;
    boolean run;


    public CoinPriceGetter(List<Coin> coins, CoinAdapter adapter, ListView listView, Context context, boolean run) {
        this.coins = coins;
        this.adapter = adapter;
        this.listView = listView;
        this.context = context;
        this.run = run;
    }

    @Override
    public void run() {
        do {
            CoinMarketAPI api = new CoinMarketAPI(50);
            coins.clear();
            List<Coin> ApiCoins = api.getAllCoinData();
             for (Coin coin : ApiCoins) {
                 coins.add(coin);
             }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
            System.out.println("Data Fetched");
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                System.out.println("Thread crashed because of interrupt");
            }
        } while (run);
    }
}
