package com.yquery.mywallet.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.yquery.mywallet.R;

public class MainActivity extends AppCompatActivity {

    protected Button iHave;

    SharedPreferences preferences;

    String totalMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        initView();

        preferences = getSharedPreferences("myMoney", MODE_PRIVATE);

        iHave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WhatIHave.class));
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        totalMoney = preferences.getString("money", "0.0");

        iHave.setText(totalMoney);
    }

    private void initView() {
        iHave = (Button) findViewById(R.id.iHave);
    }
}
