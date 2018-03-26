package com.dan.group11.cryptosim.Adapter;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.dan.group11.cryptosim.Coin;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniyaalbeg on 26/03/2018.
 */

public class CoinFilter extends Filter {

    /**
     * Copycat constructor
     * @param list  the original list to be used
     */
    public CoinFilter (List<Coin> list, String reflectMethodName, CoinAdapter adapter) {
        super ();

        mInternalList = new ArrayList<>(list);
        mAdapterUsed  = adapter;

//        try {
//            ParameterizedType stringListType = (ParameterizedType)
//                    getClass().getField("mInternalList").getGenericType();
//            mCompairMethod = stringListType.getActualTypeArguments()[0].getClass().getMethod(reflectMethodName);
//        }
//        catch (Exception ex) {
//            Log.w("GenericListFilter", ex.getMessage(), ex);
//
//            try {
//                if (mInternalList.size() > 0) {
//                    Coin type = mInternalList.get(0);
//                    mCompairMethod = type.getClass().getMethod(reflectMethodName);
//                }
//            }
//            catch (Exception e) {
//                Log.e("GenericListFilter", e.getMessage(), e);
//            }
//
//        }
    }

    /**
     * Let's filter the data with the given constraint
     * @param constraint
     * @return
     */
    @Override protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        List<Coin> filteredContents = new ArrayList<>();

        if ( constraint.length() > 0 ) {
            try {
                for (int i = 0; i < mInternalList.size(); i++) {
                        if (mInternalList.get(i).getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            filteredContents.add(mInternalList.get(i));
                            System.out.println("found");
                        }
                    }
            }
            catch (Exception ex) {
            }
        }
        else {
            filteredContents.addAll(mInternalList);
        }

        results.values = filteredContents;
        results.count  = filteredContents.size();
        return results;
    }

    /**
     * Publish the filtering adapter list
     * @param constraint
     * @param results
     */
    @Override protected void publishResults(CharSequence constraint, FilterResults results) {
        mAdapterUsed.clear();
        mAdapterUsed.addAll((List<Coin>) results.values);

        if ( results.count == 0 ) {
            mAdapterUsed.notifyDataSetInvalidated();
        }
        else {
            mAdapterUsed.notifyDataSetChanged();
        }
    }

    // class properties
    private CoinAdapter mAdapterUsed;
    private List<Coin> mInternalList;
}
