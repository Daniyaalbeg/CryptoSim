package com.dan.group11.cryptosim.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.dan.group11.cryptosim.Activites.CoinDetailedInfoSimMode;
import com.dan.group11.cryptosim.Coin;
import com.dan.group11.cryptosim.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniyaalbeg on 21/03/2018.
 */

public class CoinAdapter extends ArrayAdapter<Coin>{

    private Context coinContext;
    List<Coin> coinList = new ArrayList<>();

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
        NumberFormat formatter = new DecimalFormat("#0.00");

        TextView coinName = (TextView) listItem.findViewById(R.id.textCoinName);
        coinName.setText(coin.getName());

        TextView coinRank = (TextView) listItem.findViewById(R.id.textCoinRank);
        coinRank.setText(String.valueOf(coin.getRank()));

        TextView coinPrice = (TextView) listItem.findViewById(R.id.textCoinPrice);
        coinPrice.setText("£" + String.valueOf(formatter.format(coin.getPrice())));
        coinPrice.setTextColor(CoinDetailedInfoSimMode.checkPositive(coin.getPercentChange()) ? Color.GREEN: Color.RED);

        TextView coinID = (TextView) listItem.findViewById(R.id.textCoinID);
        coinID.setText(String.valueOf(coin.getSymbol()));

        int imageName = getContext().getResources().getIdentifier(coin.getSymbol().toLowerCase(), "mipmap", getContext().getPackageName());
        ImageView coinImage = (ImageView) listItem.findViewById(R.id.imageCoin);
        coinImage.setImageResource(imageName);

        return listItem;
    }
}
