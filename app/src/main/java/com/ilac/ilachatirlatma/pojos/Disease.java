package com.ilac.ilachatirlatma.pojos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Disease {
    private int id;
    private String diseaseName;
    private String createdDate;

    public Disease() {
    }

    public Disease(int id, String diseaseName, String createdDate) {
        this.id = id;
        this.diseaseName = diseaseName;
        this.createdDate = createdDate;
    }

    public Disease(int id, String diseaseName) {
        this.id = id;
        this.diseaseName = diseaseName;
        this.createdDate = getDateTime();
    }

    public Disease(String diseaseName) {
        this.diseaseName = diseaseName;
        this.createdDate = getDateTime();
    }

    @Override
    public String toString() {
        return diseaseName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
