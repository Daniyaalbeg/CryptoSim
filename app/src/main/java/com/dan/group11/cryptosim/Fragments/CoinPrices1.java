package com.dan.group11.cryptosim.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.dan.group11.cryptosim.Activites.CoinDetailedInfo;
import com.dan.group11.cryptosim.Adapter.CoinAdapter;
import com.dan.group11.cryptosim.Coin;
import com.dan.group11.cryptosim.CoinMarketAPI;
import com.dan.group11.cryptosim.Data.CoinPriceGetter;
import com.dan.group11.cryptosim.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by daniyaalbeg on 20/03/2018.
 */

public class CoinPrices1 extends Fragment{

    CoinAdapter adapter;
    ListView listView;
    View myView, headerView;
    List<Coin> coins;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.coin_prices, container, false);
        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Coin coin = new Coin("bitcoin", "Bitcoin", "BTC", 1, 573.2, 1.0, 72855700, 9080883500.0, 15844176.0, 15844176.0, 0.04);

        coins = new ArrayList<>();
//        for (int i = 0; i < 1; i++) {
//            coins.add(coin);
//        }

        adapter = new CoinAdapter(getActivity().getApplicationContext(), coins);
        listView = (ListView) getView().findViewById(R.id.listView);
        headerView = getLayoutInflater().inflate(R.layout.coin_price_header, listView, false);
        listView.addHeaderView(headerView);
        listView.setAdapter(adapter);

        Thread thread = new Thread(new CoinPriceGetter(coins, adapter, listView, getContext(), true));
        thread.start();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object coin = listView.getItemAtPosition((int) l+1);
                Coin newCoin = (Coin) coin;
                if (newCoin != null) {
                    Intent intent = new Intent(getContext(), CoinDetailedInfo.class);
                    intent.putExtra("coin", newCoin);
                    startActivity(intent);
                }
            }
        });

        SearchView search = (SearchView) getView().findViewById(R.id.searchView);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s.length() != 0) {
                    adapter.getFilter().filter(s.toLowerCase());
                    adapter.notifyDataSetChanged();
                } else {
                    adapter.removeFilter();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.length() != 0) {
                    adapter.getFilter().filter(s.toLowerCase());
                    adapter.notifyDataSetChanged();
                } else {
                    adapter.removeFilter();
                }
                return false;
            }
        });

    }
}
