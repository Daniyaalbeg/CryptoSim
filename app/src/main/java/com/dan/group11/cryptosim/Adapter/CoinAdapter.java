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

public class CoinAdapter extends ArrayAdapter<Coin> implements Filterable{

    private Context coinContext;
    List<Coin> coinList = new ArrayList<>();
    List<Coin> backUpArray;
    CoinFilter filter;

    public CoinAdapter(@NonNull Context context, List<Coin> coinList) {
        super(context, 0, coinList);
        this.coinContext = context;
        this.coinList = coinList;
        this.backUpArray = coinList;
        filter = new CoinFilter(coinList, "getName", this);
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

    public void removeFilter() {
        coinList = backUpArray;
        notifyDataSetChanged();
    }

    public void updateCoins(List<Coin> coins) {
        this.coinList = coins;
        this.backUpArray = coinList;
    }

    @Override
    public int getCount() {
        return coinList.size();
    }

    @Nullable
    @Override
    public Coin getItem(int position) {
        return coinList.get(position);
    }

    //
//    @NonNull
//    @Override
//    public Filter getFilter() {
//        return filter;
//    }

        @NonNull
    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                List<Coin> coinsFiltered = new ArrayList<>();

                charSequence = charSequence.toString().toLowerCase();
                if (charSequence.length() > 0) {
                    for (int i = 0; i < coinList.size(); i++) {
                        if (coinList.get(i).getName().toLowerCase().contains(charSequence)) {
                            coinsFiltered.add(coinList.get(i));
                        }
                    }
                } else {
                    coinsFiltered.addAll(coinList);
                }

                results.count = coinsFiltered.size();
                results.values = coinsFiltered;

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                coinList = (List<Coin>) filterResults.values;
                if (coinList.size() == 0) {
                    notifyDataSetInvalidated();
                } else {
                    notifyDataSetChanged();
                }
            }
        };
        return filter;
    }
}
