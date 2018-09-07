package com.ilac.ilachatirlatma;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ilac.ilachatirlatma.pojos.User;

import es.dmoral.toasty.Toasty;

public class SignUpActivity extends AppCompatActivity {

    EditText editTextUserName, editTextPassword, editTextNameSurname;
    TextInputLayout layoutTextInputUserName, layoutTextInputPassword, layoutTextInputNameSurname;
    DrugDatabase drugDatabase;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        tanimlamalar();

    }

    private void tanimlamalar() {
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextNameSurname = findViewById(R.id.editTextNameSurname);
        layoutTextInputUserName = findViewById(R.id.layoutTextInputUserName);
        layoutTextInputPassword = findViewById(R.id.layoutTextInputPassword);
        layoutTextInputNameSurname = findViewById(R.id.layoutTextInputNameSurname);
        drugDatabase = new DrugDatabase(getApplicationContext());
    }

    private boolean validateText(String text, TextInputLayout textInputLayout){
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

    public void signUp(View view){
        if(validateText(editTextUserName.getText().toString(), layoutTextInputUserName) &&
                validateText(editTextPassword.getText().toString(),layoutTextInputPassword) &&
                validateText(editTextNameSurname.getText().toString(),layoutTextInputNameSurname)){
            long userId = drugDatabase.createUser(new User(editTextUserName.getText().toString().trim(), editTextPassword.getText().toString(), editTextNameSurname.getText().toString()));
            Toasty.success(this, "Başarı ile kayıt oldunuz! Id : "+userId, Toast.LENGTH_SHORT, true).show();
        }
    }


}
