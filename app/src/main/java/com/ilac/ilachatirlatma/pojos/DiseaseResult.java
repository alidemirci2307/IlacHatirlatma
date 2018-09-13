package com.ilac.ilachatirlatma.pojos;

public class DiseaseResult {
    private int diseaseResultId;
    private int diseaseUserId;
    private String diseaseResultValue;

    public DiseaseResult() {
    }

    public DiseaseResult(int diseaseUserId, String diseaseResultValue) {
        this.diseaseUserId = diseaseUserId;
        this.diseaseResultValue = diseaseResultValue;
    }

    public DiseaseResult(int diseaseResultId, int diseaseUserId, String diseaseResultValue) {
        this.diseaseResultId = diseaseResultId;
        this.diseaseUserId = diseaseUserId;
        this.diseaseResultValue = diseaseResultValue;
    }

    public int getDiseaseResultId() {
        return diseaseResultId;
    }

    public void setDiseaseResultId(int diseaseResultId) {
        this.diseaseResultId = diseaseResultId;
    }

    public int getDiseaseUserId() {
        return diseaseUserId;
    }

    public void setDiseaseUserId(int diseaseUserId) {
        this.diseaseUserId = diseaseUserId;
    }

    public String getDiseaseResultValue() {
        return diseaseResultValue;
    }

    public void setDiseaseResultValue(String diseaseResultValue) {
        this.diseaseResultValue = diseaseResultValue;
    }
}
