package com.ilac.ilachatirlatma.pojos;

public class Drug {

    private int drugId;
    private int userId;
    private String drugName;
    private String drugDose;
    private String drugFrequence;
    private String createdDate;

    public Drug() {
    }

    public Drug(int drugId, int userId, String drugName, String drugDose, String drugFrequence, String createdDate) {
        this.drugId = drugId;
        this.userId = userId;
        this.drugName = drugName;
        this.drugDose = drugDose;
        this.drugFrequence = drugFrequence;
        this.createdDate = createdDate;
    }

    public Drug(int userId, String drugName, String drugDose, String drugFrequence, String createdDate) {
        this.userId = userId;
        this.drugName = drugName;
        this.drugDose = drugDose;
        this.drugFrequence = drugFrequence;
        this.createdDate = createdDate;
    }

    public int getDrugId() {
        return drugId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugDose() {
        return drugDose;
    }

    public void setDrugDose(String drugDose) {
        this.drugDose = drugDose;
    }

    public String getDrugFrequence() {
        return drugFrequence;
    }

    public void setDrugFrequence(String drugFrequence) {
        this.drugFrequence = drugFrequence;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
