package com.ilac.ilachatirlatma.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ilac.ilachatirlatma.DrugDatabase;
import com.ilac.ilachatirlatma.pojos.Disease;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class DiseaseDAO {

    private DrugDatabase drugDatabase;

    private static final String TABLE_DISEASE = "disease";
    //-- Tablo İsimleri

    // Yaygın Kullanılan Tablo Alan Adları
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";
    //-- Yaygın Kullanılan Tablo Alan Adları

    // Disease Tablosu - Sütun Adları
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_DISEASE_NAME = "diseaseName";
    private static final String KEY_DISEASE_VALUE = "diseaseValue";
    //-- Disease Tablosu - Sütun Adları


    public DiseaseDAO(DrugDatabase drugDatabase) {
        this.drugDatabase = drugDatabase;
    }

    public long insertDisease(Disease disease) { // V oluşturma kısmı
        SQLiteDatabase db = drugDatabase.getWritableDatabase(); // Veritabanından yazma izni aldık
        ContentValues values = new ContentValues(); //Yazacağımız değerleri atacağımız nesneyi oluşturduk
        values.put(KEY_DISEASE_NAME, disease.getDiseaseName()); // değerleri atıyoruz
        values.put(KEY_USER_ID, disease.getUserId()); // değerleri atıyoruz
        values.put(KEY_DISEASE_VALUE, disease.getDiseaseValue()); // değerleri atıyoruz
        values.put(KEY_CREATED_AT, getDateTime());
        // Eklediğimiz verinin id'sini çekiyoruz
        long diseaseId = db.insert(TABLE_DISEASE, null, values); // veritabanının user tablosuna veriyi ekliyoruz ve eklenen değerin Id bilgisini çekiyoruz
        Log.d("Disease Insert : ",  diseaseId +" : "+disease.getDiseaseName());
        return diseaseId; // eklenen verinin id değerini döndürüyoruz
    }


    public void closeDB() {
        SQLiteDatabase db = drugDatabase.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public List<Disease> getAllDiseases() {
        List<Disease> diseaseArrayList = new ArrayList<Disease>();
        String selectQuery = "SELECT  * FROM " + TABLE_DISEASE;

        Log.e("SQL : ", selectQuery);

        SQLiteDatabase db = drugDatabase.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Disease disease = new Disease();
                disease.setDiseaseId(c.getInt((c.getColumnIndex(KEY_ID))));
                disease.setDiseaseId(c.getInt((c.getColumnIndex(KEY_USER_ID))));
                disease.setDiseaseName((c.getString(c.getColumnIndex(KEY_DISEASE_NAME))));
                disease.setDiseaseName((c.getString(c.getColumnIndex(KEY_DISEASE_VALUE))));
                disease.setCreatedDate(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
                diseaseArrayList.add(disease);
            } while (c.moveToNext());
        }
        return diseaseArrayList;
    }

    public List<Disease> getAllDiseasesById(int userId) {
        List<Disease> diseaseArrayList = new ArrayList<Disease>();
        String selectQuery = "SELECT  * FROM " + TABLE_DISEASE + " WHERE " + KEY_USER_ID + " = \"" + userId + "\" ORDER BY " + KEY_ID + " DESC";

        Log.e("SQL : ", selectQuery);

        SQLiteDatabase db = drugDatabase.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Disease disease = new Disease();
                disease.setDiseaseId(c.getInt((c.getColumnIndex(KEY_ID))));
                disease.setDiseaseId(c.getInt((c.getColumnIndex(KEY_USER_ID))));
                disease.setDiseaseName((c.getString(c.getColumnIndex(KEY_DISEASE_NAME))));
                disease.setDiseaseValue((c.getString(c.getColumnIndex(KEY_DISEASE_VALUE))));
                disease.setCreatedDate(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
                diseaseArrayList.add(disease);
            } while (c.moveToNext());
        }
        return diseaseArrayList;
    }

    public Disease getDisease(int diseaseId){
        SQLiteDatabase db = drugDatabase.getReadableDatabase(); // Veritabanından okuma izni aldık.

        String selectQuery = "SELECT  * FROM " + TABLE_DISEASE + " WHERE "
                + KEY_ID + " = \"" +diseaseId+"\"" ; // Sorgumuzu yazdık

        Log.e("getDisease Query : ", selectQuery); // Sorgumuzu Console da görmemizi sağlıyor

        Cursor c = db.rawQuery(selectQuery, null); // Sorgumuzdan dönen değerleri Cursor nesnesine atıyoruz

        if (c != null){
            if(c.moveToFirst()){ // Eğer bir kayıt bulduysa (ilk kayıta gider bakar var mı diye)
                Disease disease = new Disease(c.getInt(c.getColumnIndex(KEY_ID)), c.getInt(c.getColumnIndex(KEY_USER_ID)), c.getString(c.getColumnIndex(KEY_DISEASE_NAME)), c.getString(c.getColumnIndex(KEY_DISEASE_VALUE)), c.getString(c.getColumnIndex(KEY_CREATED_AT)));
                closeDB();
                return disease; // Kullanıcımızı döndürüyoruz
            }else{
                closeDB();
                return null;
            }
        }else{
            closeDB();
            return null;
        }
    }

    public int updateDisease(Disease disease) {
        SQLiteDatabase db = drugDatabase.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DISEASE_NAME, disease.getDiseaseName());
        // updating row
        return db.update(TABLE_DISEASE, values, KEY_ID + " = ?",
                new String[]{String.valueOf(disease.getDiseaseId())});
    }

    public void deleteDisease(long diseaseId) {
        SQLiteDatabase db = drugDatabase.getWritableDatabase();
        db.delete(TABLE_DISEASE, KEY_ID + " = ?",
                new String[] { String.valueOf(diseaseId) });
    }

}
