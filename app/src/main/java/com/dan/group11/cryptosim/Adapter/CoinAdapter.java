package com.dan.group11.cryptosim.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dan.group11.cryptosim.Coin;
import com.dan.group11.cryptosim.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniyaalbeg on 21/03/2018.
 */

public class CoinAdapter extends ArrayAdapter<Coin> {

    private Context coinContext;
    private List<Coin> coinList = new ArrayList<>();

    public CoinAdapter(@NonNull Context context, List<Coin> coinList) {
        super(context, 0, coinList);
        this.coinContext = context;
        this.coinList = coinList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(coinContext).inflate(R.layout.coin_listview, parent, false);
        }

        Coin coin = coinList.get(position);

        TextView coinName = (TextView) listItem.findViewById(R.id.textCoinName);
        coinName.setText(coin.getName());

        TextView coinRank = (TextView) listItem.findViewById(R.id.textCoinRank);
        coinRank.setText(String.valueOf(coin.getRank()));

        TextView coinPrice = (TextView) listItem.findViewById(R.id.textCoinPrice);
        coinPrice.setText(Double.toString(coin.getPrice()));

        return listItem;
    }
}
