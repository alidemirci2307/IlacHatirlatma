package com.ilac.ilachatirlatma.pojos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Disease {
    private int diseaseId;
    private int userId;
    private String diseaseName;
    private String diseaseValue;
    private String createdDate;

    public Disease() {
    }

    public Disease(int diseaseId, int userId, String diseaseName, String diseaseValue, String createdDate) {
        this.diseaseId = diseaseId;
        this.userId = userId;
        this.diseaseName = diseaseName;
        this.diseaseValue = diseaseValue;
        this.createdDate = createdDate;
    }

    public Disease(int userId, String diseaseName, String diseaseValue, String createdDate) {
        this.userId = userId;
        this.diseaseName = diseaseName;
        this.diseaseValue = diseaseValue;
        this.createdDate = createdDate;
    }

    public Disease(int diseaseId, String diseaseName, String diseaseValue) {
        this.diseaseId = diseaseId;
        this.diseaseName = diseaseName;
        this.diseaseValue = diseaseValue;
        this.createdDate = createdDate;
    }


    public int getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(int diseaseId) {
        this.diseaseId = diseaseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseValue() {
        return diseaseValue;
    }

    public void setDiseaseValue(String diseaseValue) {
        this.diseaseValue = diseaseValue;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

}
