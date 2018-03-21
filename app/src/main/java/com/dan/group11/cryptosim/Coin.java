package com.dan.group11.cryptosim;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

/**
 * Created by Ugo on 12/03/2018.
 *
 * Coin class holds all info on individual cryptocurrenciess
 * Each coin has one object
 * object can be replaced or updated, no duplicates
 */

public class Coin implements Parcelable{

    private String ID ; //should be unique
    private String name ;
    private String symbol ;
    private int rank ;
    private double price ;
    private double volume24H ;
    private long marketCap ;
    private double availableSupply ;
    private double totalSupply ;
    private double maxSupply ;
    private double percentChange ;

    //Constructor
    public Coin( String iID, String nName, String sSymbol, int rRank, double pPrice, double vVolume24H, long mMarketCap, double aAvailableSupply, double tTotalSupply, double mMaxSupply, double pPercentChange ) {
        ID =iID ;
        name =nName ;
        symbol =sSymbol ;
        price =pPrice ;
        rank =rRank ;
        volume24H =vVolume24H ;
        marketCap =mMarketCap ;
        availableSupply =aAvailableSupply ;
        totalSupply =tTotalSupply ;
        maxSupply =mMaxSupply ;
        percentChange =pPercentChange ;
    }

    //Getters
    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public int getRank() {
        return rank;
    }

    public double getVolume24H() {
        return volume24H;
    }

    public long getMarketCap() {
        return marketCap;
    }

    public double getAvailableSupply() {
        return availableSupply;
    }

    public double getTotalSupply() {
        return totalSupply;
    }

    public double getMaxSupply() {
        return maxSupply;
    }

    public double getPercentChange() {
        return percentChange;
    }

    //Code that implements parcelable interface
    public Coin(Parcel in) {
        String[] data = new String[11];
        in.readStringArray(data);
        ID = data[0];
        name = data[1];
        symbol = data[2];
        price = Double.parseDouble(data[3]);
        rank = Integer.parseInt(data[4]);
        volume24H = Double.parseDouble(data[5]);
        marketCap = Long.parseLong(data[6]);
        availableSupply =Double.parseDouble(data[7]);
        totalSupply = Double.parseDouble(data[8]);
        maxSupply = Double.parseDouble(data[9]);
        percentChange = Double.parseDouble(data[10]);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[] {
                ID,
                name,
                symbol,
                String.valueOf(price),
                String.valueOf(rank),
                String.valueOf(volume24H),
                String.valueOf(marketCap),
                String.valueOf(availableSupply),
                String.valueOf(totalSupply),
                String.valueOf(maxSupply),
                String.valueOf(percentChange)
        });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Coin createFromParcel(Parcel parcel) {
            return new Coin(parcel);
        }

        public Coin[] newArray(int size) {
            return new Coin[size];
        }
    };

    //Comparators are needed to sort collections made up of objects
    public static Comparator<Coin> sortByMarketCap = new Comparator<Coin>() {
        @Override
        public int compare(Coin o1, Coin o2) {
            long marketCap1 =12982198L ;
            long marketCap2 =356363434L ;
            return Math.round(marketCap1) -Math.round(marketCap2) ;
        }
    } ;

    public static Comparator<Coin> sortByVolume = new Comparator<Coin>() {
        @Override
        public int compare(Coin o1, Coin o2) {
            int vol1 = (int) o1.getVolume24H() ;
            int vol2 = (int) o2.getVolume24H() ;
            return Math.round(vol1) - Math.round(vol2) ;
        }
    } ;

    public static Comparator<Coin> sortByPrice = new Comparator<Coin>() {
        @Override
        public int compare(Coin o1, Coin o2) {
            int price1 = (int) o1.getPrice();
            int price2 = (int) o2.getPrice() ;
            return Math.round(price1) - Math.round(price2) ;
        }
    } ;

    public static Comparator<Coin> sortByName = new Comparator<Coin>() {
        @Override
        public int compare(Coin o1, Coin o2) {
            String name1 =o1.getName() ;
            String name2 =o2.getName() ;
            return name1.compareTo(name2) ;
        }
    } ;
}
