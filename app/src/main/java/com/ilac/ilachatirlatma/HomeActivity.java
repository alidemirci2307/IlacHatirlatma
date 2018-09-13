package com.ilac.ilachatirlatma;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.ilac.ilachatirlatma.controller.DiseaseDAO;
import com.ilac.ilachatirlatma.pojos.User;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolbar; // toolbarı nesnesini oluşturduk
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView textViewBaslik,textViewUserName,textViewNameSurname;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tanimlamalar(); // gerekli tanımlamaları yapıyoruz
    }

    private void tanimlamalar() {
        user = (User) getIntent().getSerializableExtra("user"); //LoginActivity'den alınan bilgi

        // Toolbar Ayarları
        toolbar = findViewById(R.id.toolbar); // Gerekli tanımlamalar yaptık
        toolbar.setTitle(user.getNameSurname());
        toolbar.setSubtitle(user.getUserName());
        // -- Toolbar ayarı

        //DrawerLayout Ayarları
        drawerLayout = findViewById(R.id.drawer_layout); // Gerekli tanımlamalar yaptık
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle); //
        drawerToggle.syncState();
        //DrawerLayout Ayarları

        // Navigation View
        navigationView = findViewById(R.id.navigationView); // Gerekli tanımlamalar yaptık
        navigationView.setItemIconTintList(null); // NavigationView'in menüsünde bulunan resimlerin renkli gösterilmesi için
        navigationView.setNavigationItemSelectedListener(this);
        //--Navigation View

        //TextView
        textViewBaslik = findViewById(R.id.textViewDeneme);
        //-TextView




    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String itemAdi = (String) item.getTitle();
        textViewBaslik.setText(itemAdi);
        navigationViewClose();
        Intent intent;
        switch (item.getItemId()){
            case R.id.item_home :
                intent = new Intent(this,DiseaseActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
                break;
            case R.id.item_alarm :
                break;
            case R.id.item_drug :
                break;
            case R.id.item_medical :
                break;
            case R.id.item_statistics :
                break;
            case R.id.item_logOut :
                intent = new Intent();
                intent.setClass(getApplicationContext(),LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                break;
        }
        return true;
    }

    private void navigationViewClose() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void navigationViewOpen() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() { //Uygulama açıkken geri tuşuna bastığımızda
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            navigationViewClose();
        }else
            super.onBackPressed();
    }
}
