package com.example.whatsapp.Authenticate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.whatsapp.Home;
import com.example.whatsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LogIN extends AppCompatActivity {

    private Button next, confirm, email;
    private EditText code, phone;
    private FirebaseAuth firebaseAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks phoneProvider;
    private String mVerficate;
    private PhoneAuthProvider.ForceResendingToken token;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        init();

        // идет отправка по номеру телефона код верификации от firebase
        // если editText не пуст
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = phone.getText().toString();
                if(phoneNumber.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Введите номер", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.setTitle("Проверка номера!");
                    loadingBar.setMessage("Ну... подожди!");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    firebaseAuth = FirebaseAuth.getInstance(); // авториазция в firebase
                    PhoneAuthOptions options =
                            PhoneAuthOptions.newBuilder(firebaseAuth)
                                    .setPhoneNumber(phoneNumber)       // Phone number to verify
                                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                    .setActivity(LogIN.this)                 // Activity (for callback binding)
                                    .setCallbacks(phoneProvider)          // OnVerificationStateChangedCallbacks
                                    .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);

                }
            }
        });

        // проверка номера телефона, если все хорошо, то отправляется код
        // и editeText и button для отправки номера телефона становятся INVISIBLE
        // а editeText и button для отправки кода становятся VISIBLE
        phoneProvider = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                loadingBar.dismiss();
                Toast.makeText(getApplicationContext(), "Ошибка!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verficate, @NonNull PhoneAuthProvider.ForceResendingToken Token) {
                super.onCodeSent(verficate, Token);

                loadingBar.dismiss();
                mVerficate = verficate;
                Token = token;
                Toast.makeText(getApplicationContext(), "Код отправлен!", Toast.LENGTH_SHORT).show();
                next.setVisibility(View.INVISIBLE);
                confirm.setVisibility(View.VISIBLE);
                phone.setVisibility(View.INVISIBLE);
                code.setVisibility(View.VISIBLE);
            }
        };

        // кнопка отправки кода
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String c = code.getText().toString();
                if(TextUtils.isEmpty(c)){
                    Toast.makeText(getApplicationContext(), "Введите код", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.setTitle("Проверка кода!");
                    loadingBar.setMessage("Ну... подожди!");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerficate, c);
                    signInWithPhoneAuthCredential (credential);
                }
            }
        });

        // вход по email, переход на новый экран регистрации
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIN.this, RegistrActivity.class);
                startActivity(intent);
            }
        });
    }

    // проверка кода, если все успешно, то переход на главный экарн
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loadingBar.dismiss();
                            Toast.makeText(getApplicationContext(), "Проверка прошла успешно!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LogIN.this, Home.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Ошибка проверки!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    // инициализация
    public void init (){
        next = findViewById(R.id.next);
        confirm = findViewById(R.id.confirm);
        code = findViewById(R.id.code_ph);
        phone = findViewById(R.id.number_ph);
        loadingBar = new ProgressDialog(this);
        email = findViewById(R.id.email_btn);
    }
}