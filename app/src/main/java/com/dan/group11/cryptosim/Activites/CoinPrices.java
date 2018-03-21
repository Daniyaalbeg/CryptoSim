package com.dan.group11.cryptosim.Activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dan.group11.cryptosim.Adapter.CoinAdapter;
import com.dan.group11.cryptosim.Coin;
import com.dan.group11.cryptosim.R;

public class CoinPrices extends AppCompatActivity {

    ArrayAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_prices);

        String[] array = {"Bitcoin", "Ethereum", "Ripple"};

//        adapter = new CoinAdapter(this.getApplicationContext(), );
//
//        listView = (ListView) findViewById(R.id.listView);
//        listView.setAdapter(adapter);
    }
}
