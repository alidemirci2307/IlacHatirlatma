package com.ilac.ilachatirlatma.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ilac.ilachatirlatma.DrugDatabase;
import com.ilac.ilachatirlatma.pojos.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserDAO {
    private DrugDatabase drugDatabase;

    // Yaygın Kullanılan Tablo Alan Adları
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";
    //-- Yaygın Kullanılan Tablo Alan Adları

    private static final String TABLE_USER = "user";

    // User Tablosu - Sütun Adları
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_NAMESURNAME = "nameSurname";
    //-- User Tablosu - Sütun Adları

    public UserDAO(DrugDatabase drugDatabase) {
        this.drugDatabase = drugDatabase;
    }

    public long insertUser(User user) { // Kullanıcı oluşturma kısmı
        SQLiteDatabase db = drugDatabase.getWritableDatabase(); // Veritabanından yazma izni aldık

        ContentValues values = new ContentValues(); //Yazacağımız değerleri atacağımız nesneyi oluşturduk
        values.put(KEY_USERNAME, user.getUserName()); // değerleri atıyoruz
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_NAMESURNAME, user.getNameSurname());
        values.put(KEY_CREATED_AT, getDateTime());

        // Eklediğimiz verinin id'sini çekiyoruz
        long userId = db.insert(TABLE_USER, null, values); // veritabanının user tablosuna veriyi ekliyoruz ve eklenen değerin Id bilgisini çekiyoruz
        return userId; // eklenen verinin id değerini döndürüyoruz
    }


    public User logIn(String username, String password){
        SQLiteDatabase db = drugDatabase.getReadableDatabase(); // Veritabanından okuma izni aldık.

        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
                + KEY_USERNAME + " = \"" +username.trim()+"\" AND "+KEY_PASSWORD+" = \""+password+"\"" ; // Sorgumuzu yazdık

        Log.e("logIn Query : ", selectQuery); // Sorgumuzu Console da görmemizi sağlıyor

        Cursor c = db.rawQuery(selectQuery, null); // Sorgumuzdan dönen değerleri Cursor nesnesine atıyoruz

        if (c != null){
            if(c.moveToFirst()){ // Eğer bir kayıt bulduysa (ilk kayıta gider bakar var mı diye)
                User user = new User(c.getInt(c.getColumnIndex(KEY_ID)), c.getString(c.getColumnIndex(KEY_USERNAME)), c.getString(c.getColumnIndex(KEY_PASSWORD)), c.getString(c.getColumnIndex(KEY_NAMESURNAME)), c.getString(c.getColumnIndex(KEY_CREATED_AT)));
                closeDB();
                return user; // Kullanıcımızı döndürüyoruz
            }else{
                closeDB();
                return null;
            }
        }else{
            closeDB();
            return null;
        }
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void closeDB() {
        SQLiteDatabase db = drugDatabase.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
