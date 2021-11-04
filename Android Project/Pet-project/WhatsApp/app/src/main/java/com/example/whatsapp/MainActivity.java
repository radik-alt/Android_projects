package com.example.whatsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabsAdapter tabsAdapter;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setActionBar(toolbar);
        getActionBar().setTitle("WhatsApp");

        tabsAdapter = new TabsAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabsAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void init () {
        toolbar = findViewById(R.id.mainToolBar);
        viewPager = findViewById(R.id.tabs_pager);
        tabLayout = findViewById(R.id.main_tabs);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(currentUser == null) {
            Intent intent = new Intent(this, Welcome.class);
            startActivity(intent);
        }
    }
}