package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.whatsapp.Authenticate.LogIN;
import com.example.whatsapp.ItemMenues.Setting;
import com.example.whatsapp.Messager.MessageWindow;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.internal.ToolbarUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.example.whatsapp.Authenticate.LogIN;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabsAdapter tabsAdapter;
    private FirebaseUser curentUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference rootDB;
    private String currentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();

        setSupportActionBar(toolbar);//ставим toolbar
        toolbar.setTitle("Radik_dev");

        // подключение адпатера табов
        viewPager.setAdapter(tabsAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    // инициализация
    private void init () {
        firebaseAuth = FirebaseAuth.getInstance();
        curentUser = firebaseAuth.getCurrentUser();
        rootDB = FirebaseDatabase.getInstance().getReference();
        tabLayout = findViewById(R.id.main_tabs);
        viewPager = findViewById(R.id.pager);
        toolbar = findViewById(R.id.toolbar);
        tabsAdapter = new TabsAdapter(getSupportFragmentManager());
    }


    // меню с xml option_menu.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);

        return true;
    }

    // обработка item меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.search_menu:
                break;

            case R.id.group_menu:
                createGroup();
                break;

            case R.id.messageWind:
                Intent mes = new Intent(Home.this, MessageWindow.class);
                startActivity(mes);
                break;

            case R.id.setting_menu:
                Intent setting = new Intent(Home.this, Setting.class);
                startActivity(setting);
                break;

            case R.id.logout_menu:
                firebaseAuth.signOut();
                Intent signOut = new Intent(Home.this, LogIN.class);
                startActivity(signOut);
                break;
        }

        return true;
    }


    // загрузка в Firbase группы по childre Groups
    // и уникальному индиффактору пользователя (он выдается ему при регистрации)
    // создание группы проходит при введение в editetext названия
    // createGroup() - это алертдиалог, а createNewGroup() - уже загрузка в firebase группы
    private void createGroup() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(Home.this, R.style.Theme_WhatSApp);
        dialog.setTitle("Создать группу ");
        EditText editText = new EditText(Home.this);
        editText.setHint("Название группы");
        dialog.setView(editText);

        dialog.setPositiveButton("Создать", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String edxDialog = editText.getText().toString();
                if (!TextUtils.isEmpty(edxDialog)){
                    createNewGroup(edxDialog);
                }
            }
        });

        dialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        dialog.show();
    }

    // загрузка в firebase группы
    private void createNewGroup(String edxDialog) {

        rootDB.child("Groups").child(currentId).child(edxDialog).setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(), "Группа создана!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // при открытие приложения можно остаться в Home.class,
    // если currentuser != null (уникальный индифактор пользователя)
    // или перейти к окну регистрации LogIn.class
    @Override
    protected void onStart() {
        super.onStart();
        if (curentUser == null) {
            Intent intent = new Intent(Home.this, LogIN.class);
            startActivity(intent);
        } else {
            verificateUser();
        }
    }

    // здесь идет проверка если у пользователя по уникальному индифактору
    // не стоит имя и статус, то идет переброс на экран настроек
    // либо выводиться toast с твоим именем
    private void verificateUser() {

        String currentUserId = firebaseAuth.getCurrentUser().getUid();

        rootDB.child("User").child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("name").exists()){
                    String user = snapshot.child("name").getValue().toString();
                    Toast.makeText(getApplicationContext(), "Привет, " + user, Toast.LENGTH_SHORT).show();
                } else {
                    Intent settingIntent = new Intent(Home.this, Setting.class);
                    startActivity(settingIntent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}