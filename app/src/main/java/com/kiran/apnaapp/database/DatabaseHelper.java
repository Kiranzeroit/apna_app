package com.kiran.apnaapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.kiran.apnaapp.modals.DetailsModal;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "apna_app_database.db";
    private static final int DATABASE_VERSION = 1;
    public DatabaseHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE userDetails(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, mobile TEXT, city TEXT, email TEXT, password TEXT, confirmPassword TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS userDetails");

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
    public boolean isUserExist(String email) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM userDetails WHERE email = ?", new String[]{email});
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
}
