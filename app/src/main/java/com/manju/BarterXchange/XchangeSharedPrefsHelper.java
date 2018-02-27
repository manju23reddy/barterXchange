package com.manju.BarterXchange;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mkodandx on 10/29/2015.
 */
public class XchangeSharedPrefsHelper {

    SharedPreferences mAppSharedPrefs = null;
    Context mAppContext = null;

    public XchangeSharedPrefsHelper(Context aAppContext)
    {
        mAppContext = aAppContext;
        mAppSharedPrefs = mAppContext.getSharedPreferences(Constants.SHARED_PRES_NAME, mAppContext.MODE_PRIVATE);
    }

    public boolean updateSharedPrefs(XchangeSavedDataHolder aXchangeWatchObject)
    {
        try {
            SharedPreferences.Editor editor = mAppSharedPrefs.edit();

            editor.putString(Constants.SAVED_FROM_CUR, aXchangeWatchObject.getFrom());
            editor.putString(Constants.SAVED_TO_CUR, aXchangeWatchObject.getTo());
            editor.putString(Constants.SAVED_MAX_VALUE, Float.toString(aXchangeWatchObject.getMax()));
            editor.putString(Constants.SAVED_MIN_VALUE, Float.toString(aXchangeWatchObject.getMin()));
            editor.putString(Constants.SAVED_IS_ACTIVE, Boolean.toString(aXchangeWatchObject.getActive()));
            float previous_data = getePreviousQuote();
            if (previous_data > 0) {
                editor.putFloat(Constants.SAVED_PREVIOUS_QUOTE, previous_data);
            }
            else{

                editor.putFloat(Constants.SAVED_PREVIOUS_QUOTE, -1);
            }
            editor.commit();
            return true;
        }
        catch (Exception ee)
        {
            return false;
        }
    }

    public boolean updatePreviousQuote(float aPreviousQuote)
    {
        try {
            SharedPreferences.Editor editor = mAppSharedPrefs.edit();

            editor.putFloat(Constants.SAVED_PREVIOUS_QUOTE, aPreviousQuote);

            editor.commit();
            return true;
        }
        catch (Exception ee)
        {
            return false;
        }
    }

    public float getePreviousQuote()
    {
        try {
            if (mAppSharedPrefs.contains(Constants.SAVED_PREVIOUS_QUOTE)){
                return mAppSharedPrefs.getFloat(Constants.SAVED_PREVIOUS_QUOTE, -1);
            }
            return -1;

        }
        catch (Exception ee)
        {
            return -1;
        }
    }

    public XchangeSavedDataHolder getSharedPrefsData()
    {

        XchangeSavedDataHolder dataHolder = null;

        try {
            dataHolder = new XchangeSavedDataHolder();
            if (mAppSharedPrefs.contains(Constants.SAVED_FROM_CUR))
            {
                dataHolder.setFrom(mAppSharedPrefs.getString(Constants.SAVED_FROM_CUR, ""));
            }
            else{
                dataHolder = null;
                return dataHolder;
            }
            if (mAppSharedPrefs.contains(Constants.SAVED_TO_CUR))
            {
                dataHolder.setTo(mAppSharedPrefs.getString(Constants.SAVED_TO_CUR, ""));
            }
            if (mAppSharedPrefs.contains(Constants.SAVED_MIN_VALUE)){
                dataHolder.setMin(Float.parseFloat(mAppSharedPrefs.getString(Constants.SAVED_MIN_VALUE, "")));
            }
            if (mAppSharedPrefs.contains(Constants.SAVED_MAX_VALUE)){
                dataHolder.setMax(Float.parseFloat(mAppSharedPrefs.getString(Constants.SAVED_MAX_VALUE, "")));
            }
            if (mAppSharedPrefs.contains(Constants.SAVED_IS_ACTIVE)){
                dataHolder.setActive(Boolean.parseBoolean(mAppSharedPrefs.getString(Constants.SAVED_IS_ACTIVE, "")));
            }
            /*if (mAppSharedPrefs.contains(Constants.SAVED_FROM_CUR) && mAppSharedPrefs.contains(Constants.SAVED_TO_CUR) &&
                    mAppSharedPrefs.contains(Constants.SAVED_MIN_VALUE) && mAppSharedPrefs.contains(Constants.SAVED_MAX_VALUE) && mAppSharedPrefs.contains(Constants.SAVED_IS_ACTIVE)){
                dataHolder = new XchangeSavedDataHolder(mAppSharedPrefs.getString(Constants.SAVED_FROM_CUR, ""),
                mAppSharedPrefs.getString(Constants.SAVED_TO_CUR, ""),
                Float.parseFloat(mAppSharedPrefs.getString(Constants.SAVED_MAX_VALUE, "")),
                Float.parseFloat(mAppSharedPrefs.getString(Constants.SAVED_MIN_VALUE, "")),
                Boolean.parseBoolean(mAppSharedPrefs.getString(Constants.SAVED_IS_ACTIVE, "")));
            }*/

        }
        catch (Exception ee)
        {
            dataHolder = null;
        }
        return  dataHolder;
    }


}
