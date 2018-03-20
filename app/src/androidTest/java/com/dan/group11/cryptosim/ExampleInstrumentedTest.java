package com.dan.group11.cryptosim;

import android.support.test.runner.AndroidJUnit4;

import org.json.JSONObject;
import org.json.JSONArray ;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.ArrayList;

import jsonParsing.JSONParser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void test_CAPI_get_Coin_Data() throws Exception { //checks if API class gets JSONObject
        CoinMarketAPI capi =new CoinMarketAPI(100) ;
        Coin c = capi.getCoinData("bitcoin") ;
        assertEquals("Bitcoin", c.getName()) ;
    }
    @Test
    public void test_JSONParser_Array() throws Exception { //checks if jsonparser gets array
        JSONParser jsonParser =new JSONParser() ;
        String amount ="100" ;
        String url =("https://api.coinmarketcap.com/v1/ticker/?start=0&limit="+amount) ;
        JSONArray json = jsonParser.getJSONFromUrl(url) ;
        assertEquals("Bitcoin", json.getJSONObject(0).getString("name")) ;
    }
    @Test
    public void test_CAPI_Get_All_Coin_Data() throws Exception { //checks if API class returns arraylist
        CoinMarketAPI capi =new CoinMarketAPI(100) ;
        ArrayList<Coin> c = capi.getAllCoinData() ;
        assertEquals("Bitcoin", c.get(0).getName()) ;
    }
    @Test
    public void test_JSONParser_Object() throws Exception { //checks if JSONParse gets individual JSONObject from JSONArray
        JSONParser jsonParser =new JSONParser() ;
        String ID ="bitcoin" ;
        String url =("https://api.coinmarketcap.com/v1/ticker/"+ID+"/") ;
        JSONObject json = jsonParser.getJSONObjFromUrl(url) ;
        assertEquals("Bitcoin", json.getString("name")) ;
    }
    @Test
    public void test_CoinMarket_ViewCoinInfo() throws Exception { //checks if CoinMarket can get one coin object
        CoinMarket CM = CoinMarket.getInstance();
        Coin c = CM.viewCoinInfo("BiTcOiN");
        assertEquals("Bitcoin", c.getName());
    }
    @Test
    public void test_CoinMarket_CoinGetter() throws Exception { //checks if Coin market returns coins array properly
        CoinMarket CM = CoinMarket.getInstance();
        ArrayList<Coin> c = CM.getCoins();
        assertEquals("Bitcoin", c.get(0).getName());
    }
    @Test
    public void test_CoinMarket_Search() throws Exception { //Checks if search function works properly
        CoinMarket CM = CoinMarket.getInstance();
        ArrayList<Coin> c = CM.getCoins();
        assertEquals("Bitcoin", c.get(0).getName());
        c =CM.searchCoin("Ethereum") ;
        assertEquals(2, c.size()); //there are 2 ethereum coins
        assertNotEquals("Bitcoin", c.get(0).getName());
        assertEquals("Ethereum", c.get(0).getName());
    }
    @Test
    public void test_CoinMarket_SortVol() throws Exception { //checks if sorting by volume works properly
        CoinMarket CM =CoinMarket.getInstance() ;
        ArrayList<Coin> c1 =CM.getCoins() ;
        assertEquals("Stellar", c1.get(7).getName());
        CM.sortCoins(SortBy.byVolume);
        c1 =CM.getCoins() ;
        assertNotEquals("Stellar", c1.get(7).getName());
        CM.updateCoinInfo();
    }
    @Test
    public void test_CoinMarket_Sortprice() throws Exception { //checks if sorting by price works properly
        CoinMarket CM =CoinMarket.getInstance() ;
        ArrayList<Coin> c1 =CM.getCoins() ;
        assertEquals("Ripple", c1.get(2).getName());
        CM.sortCoins(SortBy.byPrice);
        c1 =CM.getCoins() ;
        assertNotEquals("Ripple", c1.get(2).getName());
        CM.updateCoinInfo();
    }
    @Test
    public void test_CoinMarket_SortMCAP() throws Exception { //checks if sorting by market cap works properly
        CoinMarket CM =CoinMarket.getInstance() ;
        ArrayList<Coin> c1 =CM.getCoins() ;
        assertEquals("Dash", c1.get(11).getName());
        CM.sortCoins(SortBy.byPrice);
        c1 =CM.getCoins() ;
        assertNotEquals("Dash", c1.get(11).getName());
        CM.updateCoinInfo();
    }
    //Need to do methods for filters
}
