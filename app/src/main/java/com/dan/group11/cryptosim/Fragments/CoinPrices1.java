package com.dan.group11.cryptosim.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dan.group11.cryptosim.R;

/**
 * Created by daniyaalbeg on 20/03/2018.
 */

public class CoinPrices extends Fragment {

    ArrayAdapter adapter;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.coin_prices, container, false);

        String[] array = {"Bitcoin", "Ethereum", "Ripple"};

        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, array);

        listView = (ListView) myView.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        return myView;
    }

}
