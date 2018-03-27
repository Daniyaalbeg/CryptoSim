package com.dan.group11.cryptosim.Activites;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
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
    private Map<String, Double> coinsOwned;
    private CoinsOwned coinsOwnedGetter;
    private double coinWorthOwned;
    private Coin coin;
    final NumberFormat moneyFormat = new DecimalFormat("#0.00");
    final NumberFormat formatter = new DecimalFormat("#0.0000");
    EditText coinAmount;
    TextView coinsOwnedText;
    Button sellButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_detailed_info_sim_mode);
        setTitle("Sim Coin Info");

        checkFile();
        coinsOwnedGetter = new CoinsOwned(wallet.getTransactions());
        coinsOwned = coinsOwnedGetter.getCoinsOwned();

        Bundle data = getIntent().getExtras();
        coin = (Coin) data.getSerializable("coin");

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

        coinsOwnedText = (TextView) findViewById(R.id.coins_owned);
        if (coinsOwned != null && coinsOwned.containsKey(coin.getName())) {
            coinWorthOwned = coinsOwned.get(coin.getName()) * coin.getPrice();
            coinWorthOwned = Double.valueOf(moneyFormat.format(Double.valueOf(coinWorthOwned)));
            String sentence = "You own " + coinsOwned.get(coin.getName()) + " " + coin.getName() + " worth " + " £" + coinWorthOwned + ".";
            coinsOwnedText.setText(sentence);
        } else {
            coinsOwnedText.setText("You own none of this coin.");
        }


        coinAmount = (EditText) findViewById(R.id.coin_input);

        Button buyButton = (Button) findViewById(R.id.buyCoinButton);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coinAmount.getText() == null) {
                    Toast.makeText(getApplicationContext(), "Please input an amount to buy", Toast.LENGTH_LONG).show();
                }try {
                    if (wallet.spend(coin.getPrice()*Double.valueOf(coinAmount.getText().toString()))) {
                        Toast.makeText(getApplicationContext(), "You have bought " + coinAmount.getText().toString() + " " + coin.getName(), Toast.LENGTH_LONG).show();
                        wallet.addTransaction(new Transaction(coin, Double.valueOf(coinAmount.getText().toString()), coin.getPrice() * Double.valueOf(coinAmount.getText().toString()), "HI There", true));
                        coinsOwned = coinsOwnedGetter.getCoinsOwned();
                        sellButton.setEnabled(true);
                        saveData();
                        setTextCoin();
                    } else {
                        Toast.makeText(getApplicationContext(), "You do not have enough money.", Toast.LENGTH_LONG).show();
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Please input a number", Toast.LENGTH_LONG).show();
                }
            }
        });

        sellButton = (Button) findViewById(R.id.sellCoinButton);
        if (coinsOwned == null || !coinsOwned.containsKey(coin.getName()) || !(coinsOwned.get(coin.getName()) > 0)) {
            sellButton.setEnabled(false);
        }
        sellButton.setOnClickListener(sellButtonClickHandler);
    }

    View.OnClickListener sellButtonClickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (coinAmount.getText() == null) {
                Toast.makeText(getApplicationContext(), "Please input an amount to sell", Toast.LENGTH_LONG).show();
            }
            try {
                System.out.println("Selling");
                Double amountSelling = Double.valueOf(coinAmount.getText().toString());
                if (coinsOwned.get(coin.getName()) >= amountSelling) {
                    wallet.addTransaction(new Transaction(coin, amountSelling, coin.getPrice(), "", false));
                    wallet.sell(Math.abs(amountSelling*coin.getPrice()));
                    coinsOwned = coinsOwnedGetter.getCoinsOwned();
                    Toast.makeText(getApplicationContext(), "You have sold " + amountSelling + " worth of " + coin.getName(), Toast.LENGTH_LONG).show();
                    saveData();
//                    setTextCoin();
                    finish();
                    getIntent().putExtra("coin", coin);
                    startActivity(getIntent());
                } else {
                    Toast.makeText(getApplicationContext(), "You do not have enough to sell", Toast.LENGTH_LONG).show();
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Please input a number", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Please input a number", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    };

    private void setTextCoin() {
        if ((coinsOwned != null && coinsOwned.containsKey(coin.getName())) || !(coinsOwned.get(coin.getName()) <= 0)) {
            coinWorthOwned = coinsOwned.get(coin.getName()) * coin.getPrice();
            coinWorthOwned = Double.valueOf(moneyFormat.format(Double.valueOf(coinWorthOwned)));
            String sentence = "You own " + coinsOwned.get(coin.getName()) + " " + coin.getName() + " worth " + " £" + coinWorthOwned + ".";
            coinsOwnedText.setText(sentence);
        } else {
            coinsOwnedText.setText("You own none of this coin.");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    //Goes back to simmode instead of coin price
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
