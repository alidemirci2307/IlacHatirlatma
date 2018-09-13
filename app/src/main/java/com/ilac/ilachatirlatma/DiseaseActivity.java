package com.ilac.ilachatirlatma;

import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ilac.ilachatirlatma.controller.DiseaseDAO;
import com.ilac.ilachatirlatma.controller.DiseaseResultDAO;
import com.ilac.ilachatirlatma.controller.DiseaseUserDAO;
import com.ilac.ilachatirlatma.model.DiseaseAll;
import com.ilac.ilachatirlatma.pojos.Disease;
import com.ilac.ilachatirlatma.pojos.DiseaseResult;
import com.ilac.ilachatirlatma.pojos.DiseaseUser;
import com.ilac.ilachatirlatma.pojos.User;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class DiseaseActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DrugDatabase drugDatabase;
    ArrayList<DiseaseAll> diseaseAllArrayList = new ArrayList<DiseaseAll>();
    DiseaseDAO diseaseDAO;
    DiseaseUserDAO diseaseUserDAO;
    DiseaseResultDAO diseaseResultDAO;
    FloatingActionButton floatingActionButton;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease);
        tanimlamalar();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
                Toasty.info(getApplicationContext(),"Tıkladın").show();
            }
        });

    }

    private void showInputDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.input_dialog);
        final TextInputLayout textInputLayoutDiseaseName = dialog.findViewById(R.id.layoutTextInputDiseaseName);
        final TextInputLayout textInputLayoutDiseaseResultValue = dialog.findViewById(R.id.layoutTextInputDiseaseResultValue);
        final EditText editTextDiseaseName = dialog.findViewById(R.id.editTextDiseaseName);
        final EditText editTextDiseaseResultValue = dialog.findViewById(R.id.editTextDiseaseResultValue);
        Button buttonRun = dialog.findViewById(R.id.buttonRun);
        Button buttonCancel = dialog.findViewById(R.id.buttonCancel);

        buttonRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateText(editTextDiseaseName.getText().toString(),textInputLayoutDiseaseName) && validateText(editTextDiseaseResultValue.getText().toString(),textInputLayoutDiseaseResultValue)){
                    long id = diseaseDAO.insertDisease(new Disease(editTextDiseaseName.getText().toString()));
                    long diseaseUserId = diseaseUserDAO.insertDiseaseUser(new DiseaseUser((int) id,user.getUserId()));
                    long diseaseResultId = diseaseResultDAO.insertDiseaseUser(new DiseaseResult((int) diseaseUserId, editTextDiseaseResultValue.getText().toString()));
                    Toasty.success(getApplicationContext(), "Başarıyla eklenmiştir", Toast.LENGTH_SHORT, true).show();
                    diseaseAllArrayList.clear();
                    diseaseAllArrayList.addAll(diseaseDAO.getAllDiseases());
                    DiseaseAdapter diseaseAdapter = new DiseaseAdapter(DiseaseActivity.this, diseaseAllArrayList);
                    recyclerView.setAdapter(diseaseAdapter);
                    dialog.dismiss();
                    //recyclerView.refreshDrawableState();
                }

            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public boolean validateText(String text, TextInputLayout textInputLayout){
        if(text != null){
            if(text.trim().length() <  4){
                textInputLayout.setError("En az 4 karakter girmelisiniz");
                return false;
            }else{
                textInputLayout.setErrorEnabled(false);
                return true;
            }
        }else{
            textInputLayout.setError("Bu alan boş olamaz");
            return false;
        }
    }

    private void tanimlamalar() {
        user = (User) getIntent().getSerializableExtra("user"); //LoginActivity'den alınan bilgi
        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.floatingActionButtonInsertDisease);
        drugDatabase = new DrugDatabase(this);
        diseaseDAO = new DiseaseDAO(drugDatabase);
        diseaseResultDAO = new DiseaseResultDAO(drugDatabase);
        diseaseUserDAO = new DiseaseUserDAO(drugDatabase);


        /*
        diseaseDAO.insertDisease(new Disease("Kızamık"));
        diseaseDAO.insertDisease(new Disease("Grip"));
        diseaseDAO.insertDisease(new Disease("Kanser"));
        diseaseDAO.insertDisease(new Disease("Çok hasta"));
        diseaseDAO.insertDisease(new Disease("Ölecek"));*/
        ArrayList<DiseaseResult> diseaseResultArrayList = new ArrayList<>();
        diseaseResultArrayList.addAll(diseaseResultDAO.getAllDiseaseResultById(user.getUserId()));

        for (int i = 0; i < diseaseResultArrayList.size(); i++) {
            Disease disease = diseaseDAO.getDisease(diseaseResultArrayList.get(i).get)
            diseaseAllArrayList.add(new DiseaseAll(user,))
        }
        diseaseAllArrayList.addAll(diseaseDAO.getAllDiseases());
        DiseaseAdapter diseaseAdapter = new DiseaseAdapter(this, diseaseAllArrayList);
        recyclerView.setAdapter(diseaseAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

    }
}
