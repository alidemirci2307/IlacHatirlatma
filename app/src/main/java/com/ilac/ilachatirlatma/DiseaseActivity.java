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
import com.ilac.ilachatirlatma.pojos.Disease;
import com.ilac.ilachatirlatma.pojos.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class DiseaseActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DrugDatabase drugDatabase;
    ArrayList<Disease> diseaseArrayList = new ArrayList<Disease>();
    DiseaseDAO diseaseDAO;
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
                    long id = diseaseDAO.insertDisease(new Disease(user.getUserId(), editTextDiseaseName.getText().toString(), editTextDiseaseResultValue.getText().toString(), getDateTime()));
                    Toasty.success(getApplicationContext(), "Başarıyla eklenmiştir. Id : " + id, Toast.LENGTH_SHORT, true).show();
                    diseaseArrayList.clear();
                    diseaseArrayList.addAll(diseaseDAO.getAllDiseasesById(user.getUserId()));
                    DiseaseAdapter diseaseAdapter = new DiseaseAdapter(DiseaseActivity.this, diseaseArrayList);
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

    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
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



        /*
        diseaseDAO.insertDisease(new Disease("Kızamık"));
        diseaseDAO.insertDisease(new Disease("Grip"));
        diseaseDAO.insertDisease(new Disease("Kanser"));
        diseaseDAO.insertDisease(new Disease("Çok hasta"));
        diseaseDAO.insertDisease(new Disease("Ölecek"));*/
        ArrayList<Disease> diseaseResultArrayList = new ArrayList<>();

        diseaseArrayList.addAll(diseaseDAO.getAllDiseasesById(user.getUserId()));
        DiseaseAdapter diseaseAdapter = new DiseaseAdapter(this, diseaseArrayList);
        recyclerView.setAdapter(diseaseAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

    }
}
