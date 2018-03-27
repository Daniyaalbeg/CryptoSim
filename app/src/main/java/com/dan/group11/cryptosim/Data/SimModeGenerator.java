package com.dan.group11.cryptosim.Data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dan.group11.cryptosim.Adapter.CoinAdapter;
import com.dan.group11.cryptosim.Coin;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by daniyaalbeg on 24/03/2018.
 */

public class SimModeGenerator extends Activity implements Runnable {

    ListView listView;
    List<Coin> coins;
    CoinAdapter adapter;
    double price, newPrice;
    Context context;
    Random random;
    boolean marketCrash;
    boolean crashing;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    public static final String PREFS_NAME = "MyApp_Settings";


    public SimModeGenerator(List<Coin> coins, CoinAdapter adapter, ListView listView, Context context) {
        this.coins = coins;
        this.adapter = adapter;
        this.listView = listView;
        this.context = context;

        sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        marketCrash = sharedPref.getBoolean("market_crash", true);
        crashing = false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(1000);
                if (marketCrash) {
                    if (Math.random() < 0.1) {
                        crashing = true;
                    }
                }
                for (int i = 0; i < coins.size(); i++) {
                    price = coins.get(i).getPrice();
                    if (crashing) {
                        System.out.println("Crashing");
                        newPrice = price - (Math.random()) * (coins.get(i).getPrice() * 0.05);
                    } else {
                        newPrice = price + (Math.random() - Math.random()) * (coins.get(i).getPrice() * 0.05);

                    }
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
                    }
                });
                if (crashing && (Math.random() < 0.1)) {
                    editor.putBoolean("market_crash", false);
                    editor.commit();
                    marketCrash = false;
                    crashing = false;
                    System.out.println("Stopped Crashing");
                }
            } catch (InterruptedException e) {
                System.out.println("Thread Killed");
                Thread.currentThread().interrupt();
            }
        } while ((!Thread.interrupted()));
    }
}
