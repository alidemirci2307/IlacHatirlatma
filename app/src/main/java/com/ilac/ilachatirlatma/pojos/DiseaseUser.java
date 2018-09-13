package com.ilac.ilachatirlatma.pojos;

public class DiseaseUser {

    private int diseaseUserId;
    private int diseaseId;
    private int userId;

    public DiseaseUser() {
    }

    public DiseaseUser(int diseaseId, int userId) {
        this.diseaseId = diseaseId;
        this.userId = userId;
    }

    public DiseaseUser(int diseaseUserId, int diseaseId, int userId) {
        this.diseaseUserId = diseaseUserId;
        this.diseaseId = diseaseId;
        this.userId = userId;
    }

    public int getDiseaseUserId() {
        return diseaseUserId;
    }

    public void setDiseaseUserId(int diseaseUserId) {
        this.diseaseUserId = diseaseUserId;
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
}
