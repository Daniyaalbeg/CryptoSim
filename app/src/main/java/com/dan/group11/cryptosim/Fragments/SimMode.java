package com.dan.group11.cryptosim.Fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.dan.group11.cryptosim.Activites.CoinDetailedInfoSimMode;
import com.dan.group11.cryptosim.Adapter.CoinAdapter;
import com.dan.group11.cryptosim.Coin;
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

/**
 * Created by daniyaalbeg on 20/03/2018.
 */

public class SimMode extends Fragment {

    ArrayAdapter adapter;
    ListView listView;
    View myView, headerView;
    List<Coin> coins;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.sim_mode, container, false);
        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //createData();
        checkFile();

        adapter = new CoinAdapter(getActivity().getApplicationContext(), coins);

        listView = (ListView) getView().findViewById(R.id.listViewSimMode);
        headerView = getLayoutInflater().inflate(R.layout.coin_price_header, listView, false);
        listView.addHeaderView(headerView);
        listView.setAdapter(adapter);

        SimModeGenerator generator = new SimModeGenerator(coins, adapter);

        Thread thread = new Thread(generator);
        thread.start();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object coin = listView.getItemAtPosition((int)l);
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
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void checkFile() {
        boolean fileFound = false;
        try {
            FileInputStream file = getContext().openFileInput("SimModeData");// new File(getContext().getFilesDir(), "SimModeData");
            fileFound = true;
        } catch (FileNotFoundException e) {
            fileFound = false;
        }
        if (fileFound) {
            //Load Coin Data
            try {
                FileInputStream fileIn = getContext().openFileInput("SimModeData");
                ObjectInputStream ois = new ObjectInputStream(fileIn);
                Coin coin;
                createData();
                for (int i = 0; i < 10; i++) {
                    saveData();
                }
                while (true) {
                    System.out.println("PREPARING TO READ");
                    coin = (Coin) ois.readObject();
                    coins.add(coin);
                    System.out.println("READ COIN");
                }
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
            //Make file
            File file = new File(getContext().getFilesDir(), "SimModeData");
            //API CALL HERE
            createData();
            saveData();
        }
    }

    private void saveData() {
        try {
            FileOutputStream fileOut = getContext().openFileOutput("SimModeData", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fileOut);
            //oos.write(coins.size());
            for (int i = 0; i < 10; i++) {
                oos.writeObject(coins.get(0));
                System.out.println("WRITING COIN");
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e ) {

        }
    }

    private void createData() {
        Coin coin = new Coin("bitcoin", "Bitcoin", "BTC", 1, 573.2, 1.0, 72855700, 9080883500.0, 15844176.0, 15844176.0, 0.04);

        coins = new ArrayList<>();
//        coins.add(coin);
        for (int i = 0; i < 1; i++) {
            coins.add(coin);
        }
    }
}
