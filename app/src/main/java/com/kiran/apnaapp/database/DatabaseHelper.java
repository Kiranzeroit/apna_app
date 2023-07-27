package com.kiran.apnaapp.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteTransactionListener;

import androidx.annotation.Nullable;

import com.kiran.apnaapp.modals.DetailsModal;
import com.kiran.apnaapp.modals.TripModal;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "apna_app_database.db";
    private static final int DATABASE_VERSION = 1;
    public DatabaseHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE userDetails(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, mobile TEXT, city TEXT, email TEXT, password TEXT, confirmPassword TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE planDetails(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, budget TEXT, startDate TEXT, endDate TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS userDetails");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS planDetails");
        onCreate(sqLiteDatabase);
    }

    public void saveUserDetails(String name, String mobile, String city, String email, String password, String confirmPassword){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("mobile", mobile);
        values.put("city", city);
        values.put("email", email);
        values.put("password", password);
        values.put("confirmPassword", confirmPassword);
        sqLiteDatabase.insert("userDetails",null, values);
        sqLiteDatabase.close();
    }
    @SuppressLint("Range")
    public boolean isUserExist(String email, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM userDetails WHERE email = ? and password = ?", new String[]{email, password});
        if (cursor.moveToFirst()) {

            DetailsModal detailsModal = new DetailsModal();
            detailsModal.email = cursor.getString(cursor.getColumnIndex("email"));
            detailsModal.password = cursor.getString(cursor.getColumnIndex("password"));

        }

        if (cursor.getCount() == 0) {
            cursor.close();
            return false;
        } else {
            cursor.close();
            return true;
        }
    }

    @SuppressLint("Range")
    public DetailsModal getUserDetails(){
        DetailsModal detailsModal = new DetailsModal();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM userDetails", null);
        if (cursor.moveToFirst()){
            detailsModal.name= cursor.getString(cursor.getColumnIndex("name"));
            detailsModal.mobile= cursor.getString(cursor.getColumnIndex("mobile"));
            detailsModal.city= cursor.getString(cursor.getColumnIndex("city"));
            detailsModal.email= cursor.getString(cursor.getColumnIndex("email"));
            detailsModal.password= cursor.getString(cursor.getColumnIndex("password"));
            detailsModal.confirmPassword= cursor.getString(cursor.getColumnIndex("confirmPassword"));
        }
        return detailsModal;
    }

    public void saveTripDetails(String name, String budget, String startDate, String endDate){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("budget", budget);
        values.put("startDate", startDate);
        values.put("endDate", endDate);
        sqLiteDatabase.insert("planDetails","", values);
        sqLiteDatabase.close();
    }

    @SuppressLint("Range")
    public TripModal getTripDetails(){
        TripModal tripModal= new TripModal();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM planDetails", null);
        if (cursor.moveToFirst()){
            tripModal.name= cursor.getString(cursor.getColumnIndex("name"));
            tripModal.budget= cursor.getString(cursor.getColumnIndex("budget"));
            tripModal.startDate= cursor.getString(cursor.getColumnIndex("startDate"));
            tripModal.endDate= cursor.getString(cursor.getColumnIndex("endDate"));
        }
        return tripModal;
    }

    @SuppressLint("Range")
    public List<TripModal> getUsersTargetList() {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + "planDetails", null);
        List<TripModal> item_data = new ArrayList<>();
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    TripModal obj = new TripModal();
                    obj.name = cursor.getString(cursor.getColumnIndex("name"));
                    obj.budget = cursor.getString(cursor.getColumnIndex("budget"));
                    obj.startDate = cursor.getString(cursor.getColumnIndex("startDate"));
                    obj.endDate = cursor.getString(cursor.getColumnIndex("endDate"));
                    item_data.add(obj);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return item_data;
    }
}
