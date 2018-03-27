package com.dan.group11.cryptosim;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

import com.dan.group11.cryptosim.Fragments.CoinPrices1;
import com.dan.group11.cryptosim.Fragments.Settings;
import com.dan.group11.cryptosim.Fragments.SimMode;
import com.dan.group11.cryptosim.Fragments.WalletFragment;

import com.dan.group11.cryptosim.Activites.*;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String PREFS_NAME = "MyApp_Settings";
    private PopupWindow popupWindow;
    private TextView message;
    private DrawerLayout drawerLayout;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);=//       fab.setOnClickListener(new View.OnClickListener() {
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (sharedPref.contains("market_crash")) {
            if (sharedPref.getBoolean("market_crash", true)) {
                //Place preferences
            }
        } else {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("market_crash", false);
        }

        setTitle("Coin Prices");
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.main, new CoinPrices1()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
//            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//
//            View popUpView = inflater.inflate(R.layout.popout, null);
//            PopupWindow popupWindow = new PopupWindow(popUpView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//
//            TextView textView = (TextView) findViewById(R.id.popup);
//            textView.setText("HELLO");
//
//            button = (Button) findViewById(R.id.popup_button);
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    popupWindow.dismiss();
//                    System.out.println("close help");
//                }
//            });
//
//            popupWindow.showAtLocation(drawerLayout, Gravity.CENTER,0,0);
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

        if (id == R.id.nav__sim_mode) {
            setTitle("Sim Mode");
            fm.beginTransaction().replace(R.id.main, new SimMode()).commit();
        } else if (id == R.id.nav_coin_prices) {
            setTitle("Coin Prices");
//            Intent intent = new Intent(this, CoinPrices.class);
//            startActivity(intent);
            fm.beginTransaction().replace(R.id.main, new CoinPrices1()).commit();
        } else if (id == R.id.nav_wallet) {
            setTitle("Wallet");
            fm.beginTransaction().replace(R.id.main, new WalletFragment()).commit();
        } else if (id == R.id.nav_settings) {
            setTitle("Settings");
            fm.beginTransaction().replace(R.id.main, new Settings()).commit();
        } else if (id == R.id.nav_log_in) {
//            fm.beginTransaction().replace(R.id.main, new Login()).commit();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}