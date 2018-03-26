package com.dan.group11.cryptosim.Data;

import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dan.group11.cryptosim.Adapter.CoinAdapter;
import com.dan.group11.cryptosim.Coin;

import java.util.List;
import java.util.Random;

/**
 * Created by daniyaalbeg on 24/03/2018.
 */

public class SimModeGenerator extends Activity implements Runnable {

    ListView listView;
    List<Coin> coins;
    CoinAdapter adapter;
    double price, newPrice;
    Context context;

    public SimModeGenerator(List<Coin> coins, CoinAdapter adapter, ListView listView, Context context) {
        this.coins = coins;
        this.adapter = adapter;
        this.listView = listView;
        this.context = context;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                for (int i = 0; i < coins.size(); i++) {
                    price = coins.get(i).getPrice();
                    newPrice = price + (Math.random() * (coins.get(i).getPrice() * 0.05));
                    if (newPrice <= 0) {
                        newPrice = Math.abs(newPrice);
                    }
                    coins.get(i).setPrice(newPrice);
                    coins.get(i).setPercentChange((price - newPrice) / 100);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.updateCoins(coins);
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);
                    }
                });
            } catch (InterruptedException e) {
                System.out.println("ERROR");
            }
        }
    }
}
