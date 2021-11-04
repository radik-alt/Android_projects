package com.example.firstulstu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Res extends AppCompatActivity {

    private TextView txt;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res);
        txt = findViewById(R.id.text);

        Intent intent = getIntent();
        double s = intent.getDoubleExtra("rest", 0);
        txt.setText(String.valueOf(s));

    }


    public void click(View view) {

        switch (view.getId()){
            case R.id.close:
                finish();
                break;
        }
    }
}