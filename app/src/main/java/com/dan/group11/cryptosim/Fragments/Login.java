package com.dan.group11.cryptosim.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dan.group11.cryptosim.R;

/**
 * Created by daniyaalbeg on 20/03/2018.
 */

public class Login extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.log_in, container, false);
        return myView;
    }
}
