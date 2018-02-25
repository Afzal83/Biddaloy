package com.dgpro.biddaloy.store;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Babu on 2/12/2018.
 */

public class AppSharedPreferences {
    public static  void saveStringToSharePreference(Context mContext,String key,String value){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        prefs.edit().putString(key,value).apply();
    }
    public static  void saveBooleanToSharedPreference(Context mContext,String key,Boolean value){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        prefs.edit().putBoolean(key, value).apply();
    }
    public static String readStringFromSharedPreference(Context mContext,String key){
        String retValue = "";
        final SharedPreferences mPreference= PreferenceManager.getDefaultSharedPreferences(mContext);
        retValue = mPreference.getString(key,"");
        return retValue;
    }
    public static Boolean readBoolean(Context mContext,String key){
        boolean retValue;
        final SharedPreferences mPreference= PreferenceManager.getDefaultSharedPreferences(mContext);
        retValue = mPreference.getBoolean(key,false);
        return retValue;
    }
}
