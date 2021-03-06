package com.dan.group11.cryptosim.Fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.dan.group11.cryptosim.Activites.CoinDetailedInfoSimMode;
import com.dan.group11.cryptosim.Adapter.CoinAdapter;
import com.dan.group11.cryptosim.Coin;
import com.dan.group11.cryptosim.CoinMarketAPI;
import com.dan.group11.cryptosim.Data.CoinPriceGetter;
import com.dan.group11.cryptosim.Data.SimModeGenerator;
import com.dan.group11.cryptosim.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by daniyaalbeg on 20/03/2018.
 */

public class SimMode extends Fragment {

    private CoinAdapter adapter;
    private ListView listView;
    private View myView, headerView;
    private List<Coin> coins;
    Thread thread;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.sim_mode, container, false);
        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        coins = new ArrayList<>();
        adapter = new CoinAdapter(getContext(), coins);
//        createData();

        listView = (ListView) getView().findViewById(R.id.listViewSimMode);
        headerView = getLayoutInflater().inflate(R.layout.coin_price_header, listView, false);

        checkFile();

        listView.addHeaderView(headerView);
        listView.setAdapter(adapter);
        if (coins == null || coins.size() == 0) {
            listView.setEmptyView(getView().findViewById(R.id.coin_empty));
        }

        //Sim Mode Generator runs
        SimModeGenerator generator = new SimModeGenerator(coins, adapter, listView, getContext());
        thread = new Thread(generator);
        thread.start();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object coin = listView.getItemAtPosition((int)l+1);
                Coin newCoin = (Coin) coin;
                if (newCoin != null) {
                    Intent intent = new Intent(getContext(), CoinDetailedInfoSimMode.class);
                    intent.putExtra("coin", newCoin);
                    startActivity(intent);
                }
            }
        });

        SearchView search = (SearchView) getView().findViewById(R.id.searchViewSimMode);
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

    private void checkFile() {
        System.out.println("CHECKING FILE");
        boolean fileFound = false;
        try {
            FileInputStream file = getContext().openFileInput("SimModeData");// new File(getContext().getFilesDir(), "SimModeData");
            fileFound = true;
        } catch (FileNotFoundException e) {
            fileFound = false;
        }
        if (fileFound) {
            //Load Coin Data
            System.out.println("LOADING COIN DATA");
            try {
                FileInputStream fileIn = getContext().openFileInput("SimModeData");
                ObjectInputStream ois = new ObjectInputStream(fileIn);
                Coin coin;
                //createData();
                //saveData();
                coins.clear();
                for (int i = 0; i < 50; i++) {
                    System.out.println("PREPARING TO READ");
                    coin = (Coin) ois.readObject();
                    coins.add(coin);
                    System.out.println("READ COIN");
                }
                adapter.updateCoins(coins);
                saveData();
                adapter.notifyDataSetChanged();
//                adapter = new CoinAdapter(getContext(), coins);
//                listView.setAdapter(adapter);
                ois.close();
            } catch (FileNotFoundException e ){
                System.out.println("File missing");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("Class not found exception");
            } catch (NullPointerException e) {
                System.out.println("Empty...");
            }
        } else {
            System.out.println("CREATING FILE");
            //Make file
            File file = new File(getContext().getFilesDir(), "SimModeData");
            //API CALL HERE
            createData();
//            saveData();
        }
    }

    private void saveData() {
        try {
            FileOutputStream fileOut = getActivity().getApplicationContext().openFileOutput("SimModeData", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fileOut);
            //oos.write(coins.size());
            for (int i = 0; i < coins.size(); i++) {
                oos.writeObject(coins.get(i));
                System.out.println("WRITING COIN");
            }
            fileOut.close();
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        saveData();
        thread.interrupt();
    }

    @Override
    public void onResume() {
        super.onResume();
        checkFile();
    }

    private void createData() {
//        Coin coin = new Coin("bitcoin", "Bitcoin", "BTC", 1, 573.2, 1.0, 72855700, 9080883500.0, 15844176.0, 15844176.0, 0.04);
//        coins.clear();
//        for (int i = 0; i < 1; i++) {
//            coins.add(coin);
//        }
        Runnable r = new Runnable() {
            @Override
            public void run() {
                CoinMarketAPI api = new CoinMarketAPI(50);
                coins = api.getAllCoinData();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        saveData();
                        checkFile();
//                        adapter.updateCoins(coins);
                        adapter.notifyDataSetChanged();
//                        adapter = new CoinAdapter(getContext(), coins);
//                        listView.setAdapter(adapter);
                        System.out.println("API CALL COMPLETE");
                    }
                });
            }
        };

        Thread t = new Thread(r);
        t.start();

    }
}
