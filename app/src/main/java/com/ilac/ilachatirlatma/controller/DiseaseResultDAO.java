package com.ilac.ilachatirlatma.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ilac.ilachatirlatma.DrugDatabase;
import com.ilac.ilachatirlatma.pojos.DiseaseResult;
import com.ilac.ilachatirlatma.pojos.DiseaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DiseaseResultDAO {

    private DrugDatabase drugDatabase;

    private static final String TABLE_DISEASE_RESULT = "diseaseResult";
    //-- Tablo İsimleri


    // Yaygın Kullanılan Tablo Alan Adları
    private static final String KEY_ID = "id";
    //-- Yaygın Kullanılan Tablo Alan Adları

    // DiseaseUser Tablosu - Sütun Adları
    private static final String KEY_DISEASE_RESULT_VALUE = "diseaseResultValue";
    private static final String KEY_DISEASE_USER_ID = "diseaseUserID";
    //-- DiseaseUser Tablosu - Sütun Adları


    public DiseaseResultDAO(DrugDatabase drugDatabase) {
        this.drugDatabase = drugDatabase;
    }

    public long insertDiseaseUser(DiseaseResult diseaseResult) { // V oluşturma kısmı
        SQLiteDatabase db = drugDatabase.getWritableDatabase(); // Veritabanından yazma izni aldık
        ContentValues values = new ContentValues(); //Yazacağımız değerleri atacağımız nesneyi oluşturduk
        values.put(KEY_DISEASE_RESULT_VALUE, diseaseResult.getDiseaseResultValue()); // değerleri atıyoruz
        values.put(KEY_DISEASE_USER_ID, diseaseResult.getDiseaseUserId());
        // Eklediğimiz verinin id'sini çekiyoruz
        long userId = db.insert(TABLE_DISEASE_RESULT, null, values); // veritabanının user tablosuna veriyi ekliyoruz ve eklenen değerin Id bilgisini çekiyoruz
        return userId; // eklenen verinin id değerini döndürüyoruz
    }


    public List<DiseaseResult> getAllDiseaseResult() {
        List<DiseaseResult> diseaseResultArrayList = new ArrayList<DiseaseResult>();
        String selectQuery = "SELECT  * FROM " + TABLE_DISEASE_RESULT;

        Log.e("SQL : ", selectQuery);

        SQLiteDatabase db = drugDatabase.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                DiseaseResult diseaseResult = new DiseaseResult();
                diseaseResult.setDiseaseResultId(c.getInt((c.getColumnIndex(KEY_ID))));
                diseaseResult.setDiseaseUserId((c.getInt(c.getColumnIndex(KEY_DISEASE_USER_ID))));
                diseaseResult.setDiseaseResultValue(c.getString(c.getColumnIndex(KEY_DISEASE_RESULT_VALUE)));
                diseaseResultArrayList.add(diseaseResult);
            } while (c.moveToNext());
        }
        return diseaseResultArrayList;
    }

    public List<DiseaseResult> getAllDiseaseResultById(int userId) {
        List<DiseaseResult> diseaseResultArrayList = new ArrayList<DiseaseResult>();
        String selectQuery = "SELECT  * FROM " + TABLE_DISEASE_RESULT+" WHERE " + KEY_DISEASE_USER_ID + " = \""+userId+"\"";

        Log.e("SQL : ", selectQuery);

        SQLiteDatabase db = drugDatabase.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                DiseaseResult diseaseResult = new DiseaseResult();
                diseaseResult.setDiseaseResultId(c.getInt((c.getColumnIndex(KEY_ID))));
                diseaseResult.setDiseaseUserId((c.getInt(c.getColumnIndex(KEY_DISEASE_USER_ID))));
                diseaseResult.setDiseaseResultValue(c.getString(c.getColumnIndex(KEY_DISEASE_RESULT_VALUE)));
                diseaseResultArrayList.add(diseaseResult);
            } while (c.moveToNext());
        }
        return diseaseResultArrayList;
    }

    public int updateDiseaseResult(DiseaseResult diseaseResult) {
        SQLiteDatabase db = drugDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DISEASE_USER_ID, diseaseResult.getDiseaseUserId());
        values.put(KEY_DISEASE_RESULT_VALUE, diseaseResult.getDiseaseResultValue());
        // updating row
        return db.update(TABLE_DISEASE_RESULT, values, KEY_ID + " = ?",
                new String[] { String.valueOf(diseaseResult.getDiseaseUserId()) });
    }

    public void deleteDiseaseResult(long diseaseUserId) {
        SQLiteDatabase db = drugDatabase.getWritableDatabase();
        db.delete(TABLE_DISEASE_RESULT, KEY_ID + " = ?",
                new String[] { String.valueOf(diseaseUserId) });
    }



    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


}
