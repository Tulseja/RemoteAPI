package com.remoteapi.nikhilkumar.remoteapi.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.List;



public class CacheManager {

    private static CacheManager mCacheManager;
    private Context mContext;
    private SharedPreferences mSharedPref;

    public static synchronized CacheManager getCacheManagerInstance(Context context) {
        if (mCacheManager == null) {
            mCacheManager = new CacheManager();
            if (context != null) {
                mCacheManager.mContext = context;
                mCacheManager.mSharedPref = mCacheManager.mContext.getApplicationContext().getSharedPreferences( "localCache", 0 );
            }

        }
        return mCacheManager;
    }


    public static synchronized void destroyCacheManager(CacheManager CacheManagerInstance) {
        if (mCacheManager != null) {
            mCacheManager.mContext = null;
            SharedPreferences.Editor editor = mCacheManager.mSharedPref.edit();
            editor.clear();
            mCacheManager = null;

        }
        CacheManagerInstance=null;
    }

    public boolean saveData(String key, String value) {
        if (!TextUtils.isEmpty( key ) && value != null) {

            SharedPreferences.Editor editor = mCacheManager.mSharedPref.edit();
            editor.putString( key, value );
            editor.apply();
            return true;

        } else {
            return false;
        }

    }

    public String retrieveData(String key) {
        if (!TextUtils.isEmpty( key )) {
            return mCacheManager.mSharedPref.getString( key,"" );
        } else {
            return " ";
        }
    }
    public boolean clearDataFoKey(String key) {
        if (!TextUtils.isEmpty( key ) ) {

            SharedPreferences.Editor editor = mCacheManager.mSharedPref.edit();

            editor.putString( key, "" );
            editor.apply();
            return true;

        } else {
            return false;
        }

    }

    public boolean clearDataFromCache() {

        SharedPreferences.Editor editor = mCacheManager.mSharedPref.edit();
        editor.clear();
        editor.apply();
        return true;

    }



}