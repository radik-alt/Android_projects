package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LogInActivity2 extends AppCompatActivity {

    private Button next, confirm;
    private EditText code, phone;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mForceResendingToken;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in2);
        init();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = phone.getText().toString();
                if(phoneNumber.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Введите номер", Toast.LENGTH_SHORT).show();
                } else {
                    PhoneAuthOptions options =
                            PhoneAuthOptions.newBuilder(mAuth)
                                    .setPhoneNumber(phoneNumber)       // Phone number to verify
                                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                    .setActivity(LogInActivity2.this)                 // Activity (for callback binding)
                                    .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                                    .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                }
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String c = code.getText().toString();
                if(c.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Введите код", Toast.LENGTH_SHORT).show();
                } else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, c);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(getApplicationContext(), "Error Phone!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                mVerificationId = verificationId;
                mForceResendingToken = forceResendingToken;

                Toast.makeText(getApplicationContext(), "Код отправлен!", Toast.LENGTH_SHORT).show();
                next.setVisibility(View.INVISIBLE);
                confirm.setVisibility(View.VISIBLE);
                phone.setVisibility(View.INVISIBLE);
                code.setVisibility(View.VISIBLE);
            }
        };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Проверка прошла успешно!", Toast.LENGTH_SHORT).show();
                            Intent mIntent = new Intent(LogInActivity2.this, MainActivity.class);
                            startActivity(mIntent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error Code!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    public void init (){
        mAuth = FirebaseAuth.getInstance();
        next = findViewById(R.id.next);
        confirm = findViewById(R.id.confirm);
        code = findViewById(R.id.code_ph);
        phone = findViewById(R.id.number_ph);
        progressDialog = new ProgressDialog(this);
    }
}