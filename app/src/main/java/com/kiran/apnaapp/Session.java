package com.kiran.apnaapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kiran.apnaapp.modals.ExpensesModal;

import java.util.List;

public class Session {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public Session(Context context) {
        sharedPreferences = context.getSharedPreferences("", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        this.editor.commit();
    }

    public void setStringValue(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getStringValue(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void setBooleanValue(String key, Boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public Boolean getBooleanValue(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public void setExpenses(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public List<ExpensesModal> getExpensesList(String key) {
        String details = sharedPreferences.getString(key, "");
        return new Gson().fromJson(details, new TypeToken<List<ExpensesModal>>() {
        }.getType());

    }

}
