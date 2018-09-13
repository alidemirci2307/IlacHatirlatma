package com.ilac.ilachatirlatma.model;

import com.ilac.ilachatirlatma.pojos.Disease;
import com.ilac.ilachatirlatma.pojos.DiseaseResult;
import com.ilac.ilachatirlatma.pojos.DiseaseUser;
import com.ilac.ilachatirlatma.pojos.User;

public class DiseaseAll {

    private User user;
    private Disease disease;
    private DiseaseUser diseaseUser;
    private DiseaseResult diseaseResult;

    public DiseaseAll() {
    }

    public DiseaseAll(User user, Disease disease, DiseaseUser diseaseUser, DiseaseResult diseaseResult) {
        this.user = user;
        this.disease = disease;
        this.diseaseUser = diseaseUser;
        this.diseaseResult = diseaseResult;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public DiseaseUser getDiseaseUser() {
        return diseaseUser;
    }

    public void setDiseaseUser(DiseaseUser diseaseUser) {
        this.diseaseUser = diseaseUser;
    }

    public DiseaseResult getDiseaseResult() {
        return diseaseResult;
    }

    public void setDiseaseResult(DiseaseResult diseaseResult) {
        this.diseaseResult = diseaseResult;
    }
}
