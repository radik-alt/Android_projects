package com.example.firstulstu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText one,two;
    private Double one_ed, two_ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        one = findViewById(R.id.first);
        two = findViewById(R.id.two);
    }

    public boolean valid () {

        try {
            String str1 = one.getText().toString();
            String str2 = two.getText().toString();
            if (str1.equalsIgnoreCase("")){
                return false;
            }else if (str2.equalsIgnoreCase("")) {
                return false;
            }
        } catch (Exception e){
            Log.d("Log", "Бывает2)");
        }

        return true;
    }

    public boolean valid_delen () {

        String str1 = one.getText().toString();
        String str2 = two.getText().toString();

        try {
            one_ed = Double.parseDouble(str1);
            two_ed = Double.parseDouble(str2);
            if (one_ed != 0){
                if (two_ed != 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            Log.d("Log", "Бывает)");
        }

        return false;
    }

    public void click(View view) {

        try {
            one_ed = Double.parseDouble(one.getText().toString());
            two_ed = Double.parseDouble(two.getText().toString());
        } catch (Exception e){

        }

        Intent intent;
        switch (view.getId()){

            case R.id.plus:
                if (valid()){
                    double res = one_ed + two_ed;
                    intent = new Intent(MainActivity.this, Res.class);
                    intent.putExtra("rest", res);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Некоректный ввод!", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.minus:
                if (valid()){
                    double res = one_ed - two_ed;
                    intent = new Intent(this, Res.class);
                    intent.putExtra("rest", res);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Некоректный ввод!", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.delen:
                if (valid()){
                    if (valid_delen()) {
                        double res = one_ed / two_ed;
                        intent = new Intent(this, Res.class);
                        intent.putExtra("rest", res);
                        startActivity(intent);
                    } else if (two_ed == 0){
                        Toast.makeText(getApplicationContext(), "На 0 делить нельзя!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Некоректный ввод!", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.multiply:
                if (valid()){
                    double res = one_ed * two_ed;
                    intent = new Intent(this, Res.class);
                    intent.putExtra("rest", res);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Некоректный ввод!", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }
}