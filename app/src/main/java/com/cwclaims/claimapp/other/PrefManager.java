package com.cwclaims.claimapp.other;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefManager {

    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;

    public PrefManager(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public static void setUserId(String userId) {
        editor.putString("user_id", userId);
        editor.commit();
    }

    public static String getUserId() {
        return sharedPreferences.getString("user_id", "");
    }

    public static void setCompany(int company) {
        editor.putInt("company", company);
        editor.commit();
    }

    public static int getCompany() {
        return sharedPreferences.getInt("company", 0);
    }

    public static void setToEmail(String email) {
        editor.putString("toEmail", email);
        editor.commit();
    }

    public static String getToEmail() {
        return sharedPreferences.getString("toEmail", "");
    }

    public static void logout() {
        editor.clear();
        editor.commit();
    }

    public static void setSetting(String s) {
        editor.putString("setting", s);
        editor.commit();
    }

    public static String getSetting() {
        return sharedPreferences.getString("setting", "");
    }
}
