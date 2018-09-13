package com.ilac.ilachatirlatma.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ilac.ilachatirlatma.DrugDatabase;
import com.ilac.ilachatirlatma.pojos.DiseaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DiseaseUserDAO {

    private DrugDatabase drugDatabase;

    private static final String TABLE_DISEASE_USER = "diseaseUser";
    //-- Tablo İsimleri


    // Yaygın Kullanılan Tablo Alan Adları
    private static final String KEY_ID = "id";
    //-- Yaygın Kullanılan Tablo Alan Adları

    // DiseaseUser Tablosu - Sütun Adları
    private static final String KEY_DISEASE_ID = "diseaseId";
    private static final String KEY_USER_ID = "userId";
    //-- DiseaseUser Tablosu - Sütun Adları


    public DiseaseUserDAO(DrugDatabase drugDatabase) {
        this.drugDatabase = drugDatabase;
    }

    public long insertDiseaseUser(DiseaseUser diseaseUser) { // V oluşturma kısmı
        SQLiteDatabase db = drugDatabase.getWritableDatabase(); // Veritabanından yazma izni aldık
        ContentValues values = new ContentValues(); //Yazacağımız değerleri atacağımız nesneyi oluşturduk
        values.put(KEY_DISEASE_ID, diseaseUser.getDiseaseId()); // değerleri atıyoruz
        values.put(KEY_USER_ID, diseaseUser.getUserId());
        // Eklediğimiz verinin id'sini çekiyoruz
        long userId = db.insert(TABLE_DISEASE_USER, null, values); // veritabanının user tablosuna veriyi ekliyoruz ve eklenen değerin Id bilgisini çekiyoruz
        return userId; // eklenen verinin id değerini döndürüyoruz
    }


    public List<DiseaseUser> getAllDiseaseUser() {
        List<DiseaseUser> diseaseUserArrayList = new ArrayList<DiseaseUser>();
        String selectQuery = "SELECT  * FROM " + TABLE_DISEASE_USER;

        Log.e("SQL : ", selectQuery);

        SQLiteDatabase db = drugDatabase.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                DiseaseUser diseaseUser = new DiseaseUser();
                diseaseUser.setDiseaseUserId(c.getInt((c.getColumnIndex(KEY_ID))));
                diseaseUser.setDiseaseId((c.getInt(c.getColumnIndex(KEY_DISEASE_ID))));
                diseaseUser.setUserId(c.getInt(c.getColumnIndex(KEY_USER_ID)));
                diseaseUserArrayList.add(diseaseUser);
            } while (c.moveToNext());
        }
        return diseaseUserArrayList;
    }

    public int updateDiseaseUser(DiseaseUser diseaseUser) {
        SQLiteDatabase db = drugDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DISEASE_ID, diseaseUser.getDiseaseId());
        values.put(KEY_USER_ID, diseaseUser.getUserId());
        // updating row
        return db.update(TABLE_DISEASE_USER, values, KEY_ID + " = ?",
                new String[] { String.valueOf(diseaseUser.getDiseaseUserId()) });
    }

    public void deleteDiseaseUser(long diseaseUserId) {
        SQLiteDatabase db = drugDatabase.getWritableDatabase();
        db.delete(TABLE_DISEASE_USER, KEY_ID + " = ?",
                new String[] { String.valueOf(diseaseUserId) });
    }



    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


}
