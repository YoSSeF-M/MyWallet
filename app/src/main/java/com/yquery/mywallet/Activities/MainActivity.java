package com.yquery.mywallet.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yquery.mywallet.Adapters.AccountsAdapter;
import com.yquery.mywallet.Database.MyWalletDatabase;
import com.yquery.mywallet.R;

public class MainActivity extends AppCompatActivity {

    protected Button iHave;
    protected RecyclerView accountsRecycler;
    protected Button addAccount;
    AccountsAdapter adapter;

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

        accountsRecycler.setLayoutManager(new GridLayoutManager(this, 2));


        addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AccountsActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        totalMoney = preferences.getString("money", "0.0");
        iHave.setText(totalMoney);

        adapter = new AccountsAdapter(this, MyWalletDatabase.getDatabase(getApplicationContext()).accountsDao().getAllAccounts());
        accountsRecycler.setAdapter(adapter);
    }

    private void initView() {
        iHave = (Button) findViewById(R.id.iHave);
        accountsRecycler = (RecyclerView) findViewById(R.id.accountsRecycler);
        addAccount = (Button) findViewById(R.id.addAccount);

    }
}
