package com.dan.group11.cryptosim.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dan.group11.cryptosim.R;
import com.dan.group11.cryptosim.Transaction;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by daniyaalbeg on 25/03/2018.
 */

public class TransactionAdapter extends ArrayAdapter {

    private Context context;
    private List<Transaction> transactions;

    public TransactionAdapter(@NonNull Context context, List<Transaction> transactions) {
        super(context, 0, transactions);
        this.context = context;
        this.transactions = transactions;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Transaction transaction = transactions.get(i);

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.transaction_listview, viewGroup, false);
        }

        int imageName = getContext().getResources().getIdentifier(transaction.getCoin().getSymbol().toLowerCase(), "mipmap", getContext().getPackageName());
        ImageView transactionImage = (ImageView) view.findViewById(R.id.imageTransactionCoin);
        transactionImage.setImageResource(imageName);

        TextView transactionDate = (TextView) view.findViewById(R.id.transactionDate);
        transactionDate.setText(String.valueOf(transaction.getTransactionDate()));

        TextView transactionCoinName = (TextView) view.findViewById(R.id.textCoinNameTransaction);
        transactionCoinName.setText(String.valueOf(transaction.getCoin().getName()));

        TextView transactionCoinID = (TextView) view.findViewById(R.id.textCoinIDTransaction);
        transactionCoinID.setText(String.valueOf(transaction.getCoin().getSymbol()));

        TextView transactionCoinPrice = (TextView) view.findViewById(R.id.textCoinAmountTransaction);
        transactionCoinPrice.setText(String.valueOf(transaction.getAmount()));
        transactionCoinPrice.setTextColor(transaction.isBought() ? Color.GREEN: Color.RED);

        return view;
    }
}
