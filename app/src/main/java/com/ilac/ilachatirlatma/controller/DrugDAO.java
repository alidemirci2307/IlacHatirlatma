package com.ilac.ilachatirlatma.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ilac.ilachatirlatma.DrugDatabase;
import com.ilac.ilachatirlatma.pojos.Drug;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DrugDAO {

    private DrugDatabase drugDatabase;

    private static final String TABLE_DRUG = "drug";
    //-- Tablo İsimleri

    // Yaygın Kullanılan Tablo Alan Adları
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";
    //-- Yaygın Kullanılan Tablo Alan Adları

    // Drug Tablosu - Sütun Adları
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_DRUG_NAME = "drugName";
    private static final String KEY_DRUG_DOSE = "drugDose";
    private static final String KEY_DRUG_FREQUENCE = "drugFrequence";
    //-- Drug Tablosu - Sütun Adları


    public DrugDAO(DrugDatabase drugDatabase) {
        this.drugDatabase = drugDatabase;
    }

    public long insertDrug(Drug drug) { // V oluşturma kısmı
        SQLiteDatabase db = drugDatabase.getWritableDatabase(); // Veritabanından yazma izni aldık
        ContentValues values = new ContentValues(); //Yazacağımız değerleri atacağımız nesneyi oluşturduk
        values.put(KEY_DRUG_NAME, drug.getDrugName()); // değerleri atıyoruz
        values.put(KEY_USER_ID, drug.getUserId()); // değerleri atıyoruz
        values.put(KEY_DRUG_DOSE, drug.getDrugDose()); // değerleri atıyoruz
        values.put(KEY_DRUG_FREQUENCE, drug.getDrugFrequence()); // değerleri atıyoruz
        values.put(KEY_CREATED_AT, getDateTime());
        // Eklediğimiz verinin id'sini çekiyoruz
        long diseaseId = db.insert(TABLE_DRUG, null, values); // veritabanının user tablosuna veriyi ekliyoruz ve eklenen değerin Id bilgisini çekiyoruz
        Log.d("Drug Insert : ",  diseaseId +" : "+drug.getDrugName());
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

    public List<Drug> getAllDrugs() {
        List<Drug> diseaseArrayList = new ArrayList<Drug>();
        String selectQuery = "SELECT  * FROM " + TABLE_DRUG;

        Log.e("SQL : ", selectQuery);

        SQLiteDatabase db = drugDatabase.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Drug drug = new Drug();
                drug.setDrugId(c.getInt((c.getColumnIndex(KEY_ID))));
                drug.setUserId(c.getInt((c.getColumnIndex(KEY_USER_ID))));
                drug.setDrugName(c.getString((c.getColumnIndex(KEY_DRUG_NAME))));
                drug.setDrugDose((c.getString(c.getColumnIndex(KEY_DRUG_DOSE))));
                drug.setDrugFrequence((c.getString(c.getColumnIndex(KEY_DRUG_FREQUENCE))));
                drug.setCreatedDate(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
                diseaseArrayList.add(drug);
            } while (c.moveToNext());
        }
        return diseaseArrayList;
    }

    public List<Drug> getAllDrugsById(int userId) {
        List<Drug> diseaseArrayList = new ArrayList<Drug>();
        String selectQuery = "SELECT  * FROM " + TABLE_DRUG + " WHERE " + KEY_USER_ID + " = \"" + userId + "\" ORDER BY " + KEY_ID + " DESC";

        Log.e("SQL : ", selectQuery);

        SQLiteDatabase db = drugDatabase.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Drug drug= new Drug();
                drug.setDrugId(c.getInt((c.getColumnIndex(KEY_ID))));
                drug.setUserId(c.getInt((c.getColumnIndex(KEY_USER_ID))));
                drug.setDrugName(c.getString((c.getColumnIndex(KEY_DRUG_NAME))));
                drug.setDrugDose((c.getString(c.getColumnIndex(KEY_DRUG_DOSE))));
                drug.setDrugFrequence((c.getString(c.getColumnIndex(KEY_DRUG_FREQUENCE))));
                drug.setCreatedDate(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
                diseaseArrayList.add(drug);
            } while (c.moveToNext());
        }
        return diseaseArrayList;
    }

    public Drug getDrug(int drugId){
        SQLiteDatabase db = drugDatabase.getReadableDatabase(); // Veritabanından okuma izni aldık.

        String selectQuery = "SELECT  * FROM " + TABLE_DRUG + " WHERE "
                + KEY_ID + " = \"" +drugId+"\"" ; // Sorgumuzu yazdık

        Log.e("getDrug Query : ", selectQuery); // Sorgumuzu Console da görmemizi sağlıyor

        Cursor c = db.rawQuery(selectQuery, null); // Sorgumuzdan dönen değerleri Cursor nesnesine atıyoruz

        if (c != null){
            if(c.moveToFirst()){ // Eğer bir kayıt bulduysa (ilk kayıta gider bakar var mı diye)
                Drug drug = new Drug(
                        c.getInt(c.getColumnIndex(KEY_ID)),
                        c.getInt(c.getColumnIndex(KEY_USER_ID)),
                        c.getString(c.getColumnIndex(KEY_DRUG_NAME)),
                        c.getString(c.getColumnIndex(KEY_DRUG_DOSE)),
                        c.getString(c.getColumnIndex(KEY_DRUG_FREQUENCE)),
                        c.getString(c.getColumnIndex(KEY_CREATED_AT)));
                closeDB();
                return drug; // Kullanıcımızı döndürüyoruz
            }else{
                closeDB();
                return null;
            }
        }else{
            closeDB();
            return null;
        }
    }

    public int updateDrug(Drug drug) {
        SQLiteDatabase db = drugDatabase.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DRUG_NAME, drug.getDrugName());
        values.put(KEY_DRUG_FREQUENCE, drug.getDrugFrequence());
        values.put(KEY_DRUG_DOSE, drug.getDrugDose());
        // updating row
        return db.update(TABLE_DRUG, values, KEY_ID + " = ?",
                new String[]{String.valueOf(drug.getDrugId())});
    }

    public void deleteDrug(long drugId) {
        SQLiteDatabase db = drugDatabase.getWritableDatabase();
        db.delete(TABLE_DRUG, KEY_ID + " = ?",
                new String[] { String.valueOf(drugId) });
    }

}
