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

import com.dan.group11.cryptosim.Adapter.TransactionAdapter;
import com.dan.group11.cryptosim.Coin;
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

public class CoinDetailedInfoSimMode extends AppCompatActivity {

    private Wallet wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_detailed_info_sim_mode);

        checkFile();

        Bundle data = getIntent().getExtras();
        final Coin coin = (Coin) data.getSerializable("coin");

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
        coinPrice.setText(String.valueOf(coin.getPrice()));

        TextView coinVolume = (TextView) findViewById(R.id.coin_detailed_info_volume24h);
        coinVolume.setText(String.valueOf(coin.getVolume24H()));

        TextView coinMarketCap = (TextView) findViewById(R.id.coin_detailed_info_market_cap);
        coinMarketCap.setText(String.valueOf(coin.getMarketCap()));

        TextView coinAvailableSupply = (TextView) findViewById(R.id.coin_detailed_info_available_supply);
        coinAvailableSupply.setText(String.valueOf(coin.getAvailableSupply()));

        TextView coinTotalSupply = (TextView) findViewById(R.id.coin_detailed_info_total_supply);
        coinTotalSupply.setText(String.valueOf(coin.getTotalSupply()));

        TextView coinPercentChange = (TextView) findViewById(R.id.coin_detailed_info_percent_change);
        coinPercentChange.setText(String.valueOf(coin.getPercentChange()));
        coinPercentChange.setTextColor(checkPositive(coin.getPercentChange()) ? Color.GREEN: Color.RED);

        final EditText coinAmount = (EditText) findViewById(R.id.coin_input);

        Button buyButton = (Button) findViewById(R.id.buyCoinButton);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wallet.addTransaction(new Transaction(coin, Double.valueOf(coinAmount.getText().toString()), coin.getPrice()*Double.valueOf(coinAmount.getText().toString()), "HI There"));
                saveData();
            }
        });

        Button sellButton = (Button) findViewById(R.id.sellCoinButton);
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Hi");
            }
        });

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
                System.out.println(wallet.getTransactions().size());
                for (int i = 0; i < wallet.getTransactions().size(); i++) {
                    wallet.getTransactions().get(i).getCoin().getName();
                    wallet.getTransactions().get(i).getAmount();
                    System.out.println("----------------------------");
                }
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
                wallet = new Wallet();
                File file = new File(getFilesDir(), "WalletData");
                FileOutputStream fileOut = openFileOutput("WalletData", Context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fileOut);
                oos.writeObject(new Wallet());
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
