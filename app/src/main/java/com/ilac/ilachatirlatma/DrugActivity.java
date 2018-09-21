package com.ilac.ilachatirlatma;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ilac.ilachatirlatma.controller.DiseaseDAO;
import com.ilac.ilachatirlatma.controller.DrugDAO;
import com.ilac.ilachatirlatma.pojos.Disease;
import com.ilac.ilachatirlatma.pojos.Drug;
import com.ilac.ilachatirlatma.pojos.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class DrugActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DrugDatabase drugDatabase;
    ArrayList<Drug> drugArrayList = new ArrayList<Drug>();
    DrugDAO drugDAO;
    FloatingActionButton floatingActionButton;
    User user;

    Dialog dialog;
    TextInputLayout layoutTextInputDrugName;
    TextInputLayout layoutTextInputDrugDose;
    EditText editTextDrugName;
    EditText editTextDrugDose;
    EditText editTextDrugFrequence;
    EditText editTextDrugTime;
    CheckBox checkbox1;
    CheckBox checkbox2;
    CheckBox checkbox3;
    Spinner spinnerDrugDoseType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug);
        tanimlamalar();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
               // Toasty.info(getApplicationContext(),"Tıkladın").show();
            }
        });

    }

    private void showInputDialog() {
        dialog  = new Dialog(this);
        dialog.setContentView(R.layout.input_dialog_drug);
        layoutTextInputDrugName = dialog.findViewById(R.id.layoutTextInputDrugName);
        layoutTextInputDrugDose = dialog.findViewById(R.id.layoutTextInputDrugDose);
        editTextDrugName = dialog.findViewById(R.id.editTextDrugName);
        editTextDrugDose = dialog.findViewById(R.id.editTextDrugDose);
        editTextDrugFrequence = dialog.findViewById(R.id.editTextDrugFrequence);
        editTextDrugTime = dialog.findViewById(R.id.editTextDrugTime);
        checkbox1 = dialog.findViewById(R.id.checkbox1);
        checkbox2 = dialog.findViewById(R.id.checkbox2);
        checkbox3 = dialog.findViewById(R.id.checkbox3);
        spinnerDrugDoseType = dialog.findViewById(R.id.spinnerDrugDoseType);

        checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    editTextDrugFrequence.setText("");
                    editTextDrugTime.setText("");
                }
            }
        });

        checkbox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    editTextDrugFrequence.setText("");
                    editTextDrugTime.setText("");
                }
            }
        });

        checkbox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    editTextDrugFrequence.setText("");
                    editTextDrugTime.setText("");
                }
            }
        });

        editTextDrugFrequence.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if(charSequence.toString().trim().length() > 0){
                    checkbox1.setChecked(false);
                    checkbox2.setChecked(false);
                    checkbox3.setChecked(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextDrugTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if(charSequence.toString().trim().length() > 0){
                    checkbox1.setChecked(false);
                    checkbox2.setChecked(false);
                    checkbox3.setChecked(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Button buttonRun = dialog.findViewById(R.id.buttonRun);
        Button buttonCancel = dialog.findViewById(R.id.buttonCancel);

        buttonRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateText(editTextDrugName.getText().toString(),layoutTextInputDrugName,3) && validateText(editTextDrugDose.getText().toString(),layoutTextInputDrugDose,1)){

                    if(checkbox1.isChecked() || checkbox2.isChecked() || checkbox3.isChecked()){
                        String frequence = "";
                        if(checkbox1.isChecked()){
                            frequence = "1";
                        }else{
                            frequence = "0";
                        }

                        if(checkbox2.isChecked()){
                            frequence += "1";
                        }else{
                            frequence += "0";
                        }

                        if(checkbox3.isChecked()){
                            frequence += "1";
                        }else{
                            frequence += "0";
                        }

                        long id = drugDAO.insertDrug(new Drug(user.getUserId(), editTextDrugName.getText().toString(), editTextDrugDose.getText().toString() +" "+String.valueOf(spinnerDrugDoseType.getSelectedItem()), frequence,getDateTime()));
                        Toasty.success(getApplicationContext(), "Başarıyla eklenmiştir.", Toast.LENGTH_SHORT, true).show();
                    }else if(editTextDrugFrequence.getText().toString().length() > 0 && editTextDrugTime.getText().toString().length() > 3){
                        String frequence = editTextDrugFrequence.getText().toString().trim() + " " + editTextDrugTime.getText().toString().trim();
                        long id = drugDAO.insertDrug(new Drug(user.getUserId(), editTextDrugName.getText().toString(), editTextDrugDose.getText().toString() +" "+String.valueOf(spinnerDrugDoseType.getSelectedItem()), frequence,getDateTime()));
                        Toasty.success(getApplicationContext(), "Başarıyla eklenmiştir.", Toast.LENGTH_SHORT, true).show();
                    }else{
                        long id = drugDAO.insertDrug(new Drug(user.getUserId(), editTextDrugName.getText().toString(), editTextDrugDose.getText().toString() +" "+String.valueOf(spinnerDrugDoseType.getSelectedItem()), "",getDateTime()));
                        Toasty.success(getApplicationContext(), "Başarıyla eklenmiştir."+checkbox1.isSelected() +" " + checkbox2.isSelected() + " " + checkbox3.isSelected(), Toast.LENGTH_SHORT, true).show();
                    }




                    //Toasty.success(getApplicationContext(), "Başarıyla eklenmiştir. ", Toast.LENGTH_SHORT, true).show();
                    drugArrayList.clear();
                    drugArrayList.addAll(drugDAO.getAllDrugsById(user.getUserId()));
                    DrugAdapter drugAdapter= new DrugAdapter(DrugActivity.this, drugArrayList);
                    recyclerView.setAdapter(drugAdapter);
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
    public boolean validateText(String text, TextInputLayout textInputLayout,int length){
        if(text != null){
            if(text.trim().length() <  length){
                textInputLayout.setError("En az "+length+" karakter girmelisiniz");
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
        //Toolbar Ayarı
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("İlaçlar");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //--Toolbar ayarı
        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.floatingActionButtonInsertDrug);
        drugDatabase = new DrugDatabase(this);
        drugDAO = new DrugDAO(drugDatabase);

        /*
        diseaseDAO.insertDisease(new Disease("Kızamık"));
        diseaseDAO.insertDisease(new Disease("Grip"));
        diseaseDAO.insertDisease(new Disease("Kanser"));
        diseaseDAO.insertDisease(new Disease("Çok hasta"));
        diseaseDAO.insertDisease(new Disease("Ölecek"));*/

        drugArrayList.addAll(drugDAO.getAllDrugsById(user.getUserId()));
        DrugAdapter drugAdapter= new DrugAdapter(this, drugArrayList);
        recyclerView.setAdapter(drugAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

    }
}
