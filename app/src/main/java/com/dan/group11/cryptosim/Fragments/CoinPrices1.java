package com.dan.group11.cryptosim.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dan.group11.cryptosim.Adapter.CoinAdapter;
import com.dan.group11.cryptosim.Coin;
import com.dan.group11.cryptosim.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniyaalbeg on 20/03/2018.
 */

public class CoinPrices1 extends Fragment {

    ArrayAdapter adapter;
    ListView listView;
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.coin_prices, container, false);
        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Coin coin = new Coin("bitcoin", "Bitcoin", "BTC", 1, 573.2, 1.0, 72855700, 9080883500.0, 15844176.0, 15844176.0, 0.04);

        List<Coin> coins = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            coins.add(coin);
        }

        String[] array = {"Bitcoin", "Ethereum", "Ripple"};

        adapter = new CoinAdapter(getActivity().getApplicationContext(), coins);

        listView = (ListView) getView().findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}
