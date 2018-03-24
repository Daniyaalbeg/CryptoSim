package com.dan.group11.cryptosim.Activites;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.dan.group11.cryptosim.Coin;
import com.dan.group11.cryptosim.R;

public class CoinDetailedInfoSimMode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_detailed_info_sim_mode);

        Bundle data = getIntent().getExtras();
        Coin coin = (Coin) data.getParcelable("coin");

        int imageName = getResources().getIdentifier(coin.getSymbol().toLowerCase(), "mipmap", getPackageName());
        ImageView coinImage = (ImageView) findViewById(R.id.coinImageSimMode);
        coinImage.setImageResource(imageName);

//        TextView coinId = (TextView) findViewById(R.id.coin_detailed_info_id);
//        coinId.setText(String.valueOf(coin.getID()));

        TextView coinName = (TextView) findViewById(R.id.coin_detailed_info_name);
        coinName.setText(String.valueOf(coin.getName()));

        TextView coinSymbol = (TextView) findViewById(R.id.coin_detailed_info_symbol);
        coinSymbol.setText(String.valueOf(coin.getSymbol()));

        TextView coinRank = (TextView) findViewById(R.id.coin_detailed_info_rank);
        coinRank.setText(String.valueOf(coin.getRank()));

        TextView coinPrice = (TextView) findViewById(R.id.coin_detailed_info_price);
        coinPrice.setText(String.valueOf(coin.getPrice()));

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
        coinPercentChange.setText(String.valueOf(coin.getPercentChange()));
        coinPercentChange.setTextColor(checkPositive(coin.getPercentChange()) ? Color.GREEN: Color.RED);

    }

    private boolean checkPositive(double i) {
        if (i != i) throw new IllegalArgumentException("NaN");
        i *= Double.POSITIVE_INFINITY;
        if (i == Double.NEGATIVE_INFINITY) {
            return false;
        } else {
            return true;
        }
    }
}
