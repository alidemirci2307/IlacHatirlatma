package com.ilac.ilachatirlatma;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ilac.ilachatirlatma.controller.UserDAO;
import com.ilac.ilachatirlatma.pojos.User;

import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUserName, editTextPassword;
    TextInputLayout layoutTextInputUserName, layoutTextInputPassword;
    DrugDatabase drugDatabase;
    UserDAO userDAO;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            tanimlamalar();
        }

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void tanimlamalar() {
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        layoutTextInputUserName = findViewById(R.id.layoutTextInputUserName);
        layoutTextInputPassword = findViewById(R.id.layoutTextInputPassword);
        drugDatabase = new DrugDatabase(this);
        userDAO = new UserDAO(drugDatabase);

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
            User user = userDAO.logIn(editTextUserName.getText().toString().trim(), editTextPassword.getText().toString());
            if(user != null){
                Toasty.success(this, "Başarıyla giriş yaptınız!", Toast.LENGTH_SHORT, true).show();
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.setClass(getApplicationContext(),HomeActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
                finish();
            }else{
                Toasty.error(this, "Kullanıcı adı ya da şifre hatalıdır!", Toast.LENGTH_SHORT, true).show();
            }

        }
    }

    public void signUp(View view){
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.setClass(this,SignUpActivity.class);
        startActivity(intent);
    }
}
