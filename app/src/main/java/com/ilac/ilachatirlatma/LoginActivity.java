package com.ilac.ilachatirlatma;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUserName, editTextPassword;
    TextInputLayout layoutTextInputUserName, layoutTextInputPassword;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tanimlamalar();

    }

    private void tanimlamalar() {
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        layoutTextInputUserName = findViewById(R.id.layoutTextInputUserName);
        layoutTextInputPassword = findViewById(R.id.layoutTextInputPassword);
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

    public void login(View view){
        if(validateText(editTextUserName.getText().toString(), layoutTextInputUserName) && validateText(editTextPassword.getText().toString(),layoutTextInputPassword)){
            Toast.makeText(this,"Başarılı",Toast.LENGTH_SHORT);
        }
    }
}
