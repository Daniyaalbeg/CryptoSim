package com.dan.group11.cryptosim.Activites;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dan.group11.cryptosim.Coin;
import com.dan.group11.cryptosim.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class CoinDetailedInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_detailed_info);

        Bundle data = getIntent().getExtras();
        Coin coin = (Coin) data.getSerializable("coin");

        int imageName = getResources().getIdentifier(coin.getSymbol().toLowerCase(), "mipmap", getPackageName());
        ImageView coinImage = (ImageView) findViewById(R.id.coinImageSimMode);
        coinImage.setImageResource(imageName);

        TextView coinName = (TextView) findViewById(R.id.coin_detailed_info_name);
        coinName.setText(String.valueOf(coin.getName()));

        TextView coinSymbol = (TextView) findViewById(R.id.coin_detailed_info_symbol);
        coinSymbol.setText(String.valueOf(coin.getSymbol()));

        TextView coinRank = (TextView) findViewById(R.id.coin_detailed_info_rank);
        coinRank.setText(String.valueOf(coin.getRank()));

        TextView coinPrice = (TextView) findViewById(R.id.coin_detailed_info_price);
        coinPrice.setText("£" + String.valueOf(new DecimalFormat("#0.0000").format(coin.getPrice())));

        TextView coinVolume = (TextView) findViewById(R.id.coin_detailed_info_volume24h);
        coinVolume.setText(String.valueOf(coin.getVolume24H()));

        TextView coinMarketCap = (TextView) findViewById(R.id.coin_detailed_info_market_cap);
        coinMarketCap.setText(String.valueOf(coin.getMarketCap()));

        TextView coinAvailableSupply = (TextView) findViewById(R.id.coin_detailed_info_available_supply);
        coinAvailableSupply.setText(String.valueOf(coin.getAvailableSupply()));

        TextView coinTotalSupply = (TextView) findViewById(R.id.coin_detailed_info_total_supply);
        coinTotalSupply.setText(String.valueOf(coin.getTotalSupply()));

//        TextView coinMaxSupply = (TextView) findViewById(R.id.coin_detailed_info_max_supply);
//        coinMaxSupply.setText(String.valueOf(coin.getMaxSupply()));

        TextView coinPercentChange = (TextView) findViewById(R.id.coin_detailed_info_percent_change);
        coinPercentChange.setText(String.valueOf(new DecimalFormat("#0.00").format(coin.getPercentChange())));
        coinPercentChange.setTextColor(checkPositive(coin.getPercentChange()) ? Color.GREEN: Color.RED);

        setTitle("Coin Info");

    }

    private boolean checkPositive(double i) {
        if (i != i) throw new IllegalArgumentException("NaN");
        i *= Double.POSITIVE_INFINITY;
        if (i == Double.NEGATIVE_INFINITY) {
            return true;
        } else {
            return false;
        }
    }
}
