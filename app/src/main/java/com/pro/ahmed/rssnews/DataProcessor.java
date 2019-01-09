package com.pro.ahmed.rssnews;

import android.content.Context;
import android.content.SharedPreferences;

public class DataProcessor {
    private static Context context;
    private static DataProcessor mInstance;

    private static SharedPreferences prefs;
    private static SharedPreferences.Editor editor;
    private final static String PREFS_NAME = "Rss News";

    private DataProcessor(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public static DataProcessor getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DataProcessor(context);
        }
        return mInstance;
    }

    public static void setInt(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(String key) {
        return prefs.getInt(key, -1);
    }

    public static void setStr(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public static String getStr(String key) {
        return prefs.getString(key, "DNF");
    }

    public static void setBool(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBool(String key) {
        return prefs.getBoolean(key, true);
    }
}
