package com.example.whatsapp.Authenticate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whatsapp.Home;
import com.example.whatsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrActivity extends AppCompatActivity {

    private Button registr, ent;
    private TextView enter;
    private EditText email, pass;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registr);
        init();

        // обрабатчик кнопки регистрации
        registr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAcount();
            }
        });

        // при нажатие на текст (входа), то уходит сам текст,
        // убирается кнопка регистрации и появляется кнопка входа
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ent.setVisibility(View.VISIBLE);
                registr.setVisibility(View.INVISIBLE);
                enter.setVisibility(View.INVISIBLE);
            }
        });

        // кнопка входа по email и паролю
        ent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logInAccount();
            }
        });
    }

    // регистрация пользователя по паролю и email
    // проверка на пустую строку и работа с firebase через createUserWithEmailAndPassword
    // ну и в случае успеха отправка на главный экарн
    private void createAcount(){
        String emailS = email.getText().toString();
        String passS = pass.getText().toString();

        if (emailS.equalsIgnoreCase("")){
            Toast.makeText(getApplicationContext(), "Введите email!", Toast.LENGTH_SHORT).show();
        } else if (passS.equalsIgnoreCase("")){
            Toast.makeText(getApplicationContext(), "Введите пароль!", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.createUserWithEmailAndPassword(emailS, passS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(RegistrActivity.this, Home.class);
                        startActivity(intent);
                        finish();
                    } else {
                        String error = task.getException().toString();
                        Toast.makeText(getApplicationContext(), "Ошибка " + error, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    // здесь баг (авторизация не работает, выдает ошибку, ИСПРАВИТЬ!!!!)
    private void logInAccount () {
        String emailS = email.getText().toString();
        String passS = pass.getText().toString();

        if (emailS.equalsIgnoreCase("")){
            Toast.makeText(getApplicationContext(), "Введите email!", Toast.LENGTH_SHORT).show();
        } else if (passS.equalsIgnoreCase("")){
            Toast.makeText(getApplicationContext(), "Введите пароль!", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.signInWithEmailAndPassword(emailS, passS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Intent intent = new Intent(RegistrActivity.this, Home.class);
                        startActivity(intent);
                        finish();
                    } else {
                        String error = task.getException().toString();
                        Toast.makeText(getApplicationContext(), "Ошибка " + error, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }


    // инициализация
    private void init () {
        firebaseAuth = FirebaseAuth.getInstance();
        registr = findViewById(R.id.reg);
        enter = findViewById(R.id.enter);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        ent = findViewById(R.id.ent);
    }
}