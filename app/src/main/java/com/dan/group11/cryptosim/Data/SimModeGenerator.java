package com.dan.group11.cryptosim.Data;

import android.app.Activity;
import android.widget.ArrayAdapter;

import com.dan.group11.cryptosim.Coin;

import java.util.List;
import java.util.Random;

/**
 * Created by daniyaalbeg on 24/03/2018.
 */

public class SimModeGenerator extends Activity implements Runnable {

    List<Coin> coins;
    ArrayAdapter adapter;
    double price, newPrice;

    public SimModeGenerator(List<Coin> coins, ArrayAdapter adapter) {
        this.coins = coins;
        this.adapter = adapter;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                for (int i = 0; i < coins.size(); i++) {
                    price = coins.get(i).getPrice();
                    newPrice = price + Math.round(Math.random()) * 2 - 1;
                    coins.get(i).setPrice(newPrice);
                    coins.get(i).setPercentChange((price - newPrice) / 100);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            } catch (InterruptedException e) {
                System.out.println("ERROR");
            }
        }
    }
}
