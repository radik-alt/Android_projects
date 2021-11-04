package com.example.secondulstu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<String> list;
    private ArrayAdapter<String> arrayAdapter;
    private EditText edx;
    private Button one, two, three;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
                    String str = "";
                    for (int i = 0; i < checkedItems.size(); i++) {
                        if (checkedItems.valueAt(i)) {
                            str += (listView.getAdapter().getItem(checkedItems.keyAt(i)).toString()) + "\n";
                        }
                    }
                    if (!str.equalsIgnoreCase(""))
                        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    for (int i = 0; i < list.size(); i++) {
                        listView.setItemChecked(i, true);
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    for (int i = 0; i < list.size(); i++) {
                        listView.setItemChecked(i, false);
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void init () {
        try {
            one = findViewById(R.id.one);
            two = findViewById(R.id.two);
            three = findViewById(R.id.three);
            listView = findViewById(R.id.list);
            edx = findViewById(R.id.edit);
        }catch (Exception e){
            Log.d("Log", "Нельзя так!");
        }
        list = new ArrayList<>();
        adapter();
    }

    public void adapter(){
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, list);
        listView.setAdapter(arrayAdapter);
    }

    public void click(View view) {
        String str = edx.getText().toString();
        try {
            if (!str.equalsIgnoreCase("")) {
                list.add(str);
                arrayAdapter.notifyDataSetChanged();
                edx.setText("");
            }
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("list", (ArrayList<String>) list);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        list = savedInstanceState.getStringArrayList("list");
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, list);
        listView.setAdapter(arrayAdapter);
    }
}