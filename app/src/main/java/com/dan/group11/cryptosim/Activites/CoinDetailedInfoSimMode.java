package com.dan.group11.cryptosim.Activites;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dan.group11.cryptosim.Adapter.TransactionAdapter;
import com.dan.group11.cryptosim.Coin;
import com.dan.group11.cryptosim.Data.CoinsOwned;
import com.dan.group11.cryptosim.R;
import com.dan.group11.cryptosim.Transaction;
import com.dan.group11.cryptosim.Wallet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;

public class CoinDetailedInfoSimMode extends AppCompatActivity {

    private Wallet wallet;
    Map<String, Double> coinsOwned;
    CoinsOwned coinsOwnedGetter;
    double coinWorthOwned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_detailed_info_sim_mode);

        checkFile();
        coinsOwnedGetter = new CoinsOwned(wallet.getTransactions());
        coinsOwned = coinsOwnedGetter.getCoinsOwned();

        Bundle data = getIntent().getExtras();
        final Coin coin = (Coin) data.getSerializable("coin");
        NumberFormat formatter = new DecimalFormat("#0.0000");
        NumberFormat moneyFormat = new DecimalFormat("#0.00");

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
        coinPrice.setText("£" + String.valueOf(formatter.format(coin.getPrice())));

        TextView coinVolume = (TextView) findViewById(R.id.coin_detailed_info_volume24h);
        coinVolume.setText(String.valueOf(coin.getVolume24H()));

        TextView coinMarketCap = (TextView) findViewById(R.id.coin_detailed_info_market_cap);
        coinMarketCap.setText(String.valueOf(coin.getMarketCap()));

        TextView coinAvailableSupply = (TextView) findViewById(R.id.coin_detailed_info_available_supply);
        coinAvailableSupply.setText(String.valueOf(coin.getAvailableSupply()));

        TextView coinTotalSupply = (TextView) findViewById(R.id.coin_detailed_info_total_supply);
        coinTotalSupply.setText(String.valueOf(coin.getTotalSupply()));

        TextView coinPercentChange = (TextView) findViewById(R.id.coin_detailed_info_percent_change);
        coinPercentChange.setText(moneyFormat.format(coin.getPercentChange()));
        coinPercentChange.setTextColor(checkPositive(coin.getPercentChange()) ? Color.GREEN: Color.RED);

        TextView availableFundsText = (TextView) findViewById(R.id.wallet_worth);
        availableFundsText.setText("Fund available: £" + moneyFormat.format(wallet.getMoney()));

        TextView coinsOwnedText = (TextView) findViewById(R.id.coins_owned);
        if (coinsOwned.containsKey(coin.getName())) {
            coinWorthOwned = coinsOwned.get(coin.getName()) * coin.getPrice();
            coinWorthOwned = Double.valueOf(moneyFormat.format(Double.valueOf(coinWorthOwned)));
            String sentence = "You own " + coinsOwned.get(coin.getName()) + " " + coin.getName() + " worth " + " £" + coinWorthOwned + ".";
            coinsOwnedText.setText(sentence);
        } else {
            coinsOwnedText.setText("You own none of this coin.");
        }


        final EditText coinAmount = (EditText) findViewById(R.id.coin_input);

        Button buyButton = (Button) findViewById(R.id.buyCoinButton);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coinAmount.getText() == null) {
                    Toast.makeText(getApplicationContext(), "Please input an amount to buy", Toast.LENGTH_LONG).show();
                }
                System.out.println(coin.getPrice()*Double.valueOf(coinAmount.getText().toString()));
                try {
                    if (wallet.spend(coin.getPrice()*Double.valueOf(coinAmount.getText().toString()))) {
                        Toast.makeText(getApplicationContext(), "You have bought " + coinAmount.getText().toString() + " " + coin.getName(), Toast.LENGTH_LONG).show();
                        wallet.addTransaction(new Transaction(coin, Double.valueOf(coinAmount.getText().toString()), coin.getPrice() * Double.valueOf(coinAmount.getText().toString()), "HI There", true));
                        saveData();
                    } else {
                        Toast.makeText(getApplicationContext(), "You do not have enough money.", Toast.LENGTH_LONG).show();
                    }
                    saveData();
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Please input a number", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button sellButton = (Button) findViewById(R.id.sellCoinButton);
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Hi");
            }
        });

        setTitle("Sim Coin Info");

    }

    public static boolean checkPositive(double i) {
        if (i != i) throw new IllegalArgumentException("NaN");
        i *= Double.POSITIVE_INFINITY;
        if (i == Double.NEGATIVE_INFINITY) {
            return true;
        } else {
            return false;
        }
    }

    private void checkFile() {
        System.out.println("CHECKING FILE");
        boolean fileFound = false;
        try {
            FileInputStream file = openFileInput("WalletData");
            fileFound = true;
        } catch (FileNotFoundException e) {
            fileFound = false;
        }
        if (fileFound) {
            //Load Wallet Data
            System.out.println("LOADING WALLET DATA");
            try {
                FileInputStream fileIn = getApplicationContext().openFileInput("WalletData");
                ObjectInputStream ois = new ObjectInputStream(fileIn);
                wallet = (Wallet) ois.readObject();
                ois.close();
                fileIn.close();
                System.out.println("LOADING CLOSED");
            } catch (FileNotFoundException e ){
                System.out.println("File missing");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("IO Exception");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("Class not found exception");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("Empty...");
                e.printStackTrace();
            }
        } else {
            System.out.println("CREATING FILE");
            //Make file
            try {
                File file = new File(getFilesDir(), "WalletData");
                FileOutputStream fileOut = openFileOutput("WalletData", Context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fileOut);
                oos.writeObject(new Wallet(500));
                System.out.println("WRITING WALLET");
                oos.close();
                fileOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveData() {
        try {
            FileOutputStream fileOut = openFileOutput("WalletData", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fileOut);
            oos.writeObject(wallet);
            System.out.println("SAVING WALLET");
            oos.close();
            fileOut.close();
        } catch (FileNotFoundException e) {

        } catch (IOException e ) {
        }
    }
}
