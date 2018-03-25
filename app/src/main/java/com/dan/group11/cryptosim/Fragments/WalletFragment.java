package com.dan.group11.cryptosim.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dan.group11.cryptosim.Adapter.TransactionAdapter;
import com.dan.group11.cryptosim.R;
import com.dan.group11.cryptosim.Wallet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by daniyaalbeg on 20/03/2018.
 */

public class WalletFragment extends Fragment {

    private Wallet wallet;
    private ListView listView;
    private TransactionAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.wallet, container, false);
        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = (ListView) getActivity().findViewById(R.id.transaction_listview);

        checkFile();

        adapter = new TransactionAdapter(getContext(), wallet.getTransactions());
        listView.setAdapter(adapter);

    }

    private void checkFile() {
        System.out.println("CHECKING FILE");
        boolean fileFound = false;
        try {
            FileInputStream file = getContext().openFileInput("WalletData");
            fileFound = true;
        } catch (FileNotFoundException e) {
            fileFound = false;
        }
        if (fileFound) {
            //Load Coin Data
            System.out.println("LOADING WALLET DATA");
            try {
                FileInputStream fileIn = getContext().openFileInput("WalletData");
                ObjectInputStream ois = new ObjectInputStream(fileIn);
                wallet = (Wallet) ois.readObject();
                adapter = new TransactionAdapter(getContext(), wallet.getTransactions());
                listView.setAdapter(adapter);
                for (int i = 0; i < wallet.getTransactions().size(); i++) {
                    wallet.getTransactions().get(i).getCoin().getName();
                    System.out.println("----------------------------");
                }
                ois.close();
                fileIn.close();
            } catch (FileNotFoundException e ){
                System.out.println("File missing");
                e.printStackTrace();
            } catch (IOException e) {
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
                File file = new File(getActivity().getFilesDir(), "WalletData");
                FileOutputStream fileOut = getActivity().openFileOutput("WalletData", Context.MODE_PRIVATE);
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
            //wallet = new Wallet();
            FileOutputStream fileOut = getActivity().getApplicationContext().openFileOutput("WalletData", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fileOut);
            oos.writeObject(wallet);
            System.out.println("WRITING WALLET");
            oos.close();
            fileOut.close();
        } catch (FileNotFoundException e) {

        } catch (IOException e ) {
        }
    }
}
