package com.dan.group11.cryptosim.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.dan.group11.cryptosim.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by daniyaalbeg on 20/03/2018.
 */

public class Settings extends Fragment {

    public static final String PREFS_NAME = "MyApp_Settings";
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.settings, container, false);
        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPref = getActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = sharedPref.edit();

        Switch switchCheck = (Switch) getView().findViewById(R.id.switch_market);
        switchCheck.setChecked(sharedPref.getBoolean("market_crash", true));

        switchCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                sharedPref.getBoolean("market_crash", true);
                if (b) {
                    editor.putBoolean("market_crash", true);
                    editor.commit();
                    System.out.println(sharedPref.getBoolean("market_crash", true));
                } else {
                    editor.putBoolean("market_crash", false);
                    editor.commit();
                    System.out.println(sharedPref.getBoolean("market_crash", true));
                }
            }
        });
    }
}
