package com.yquery.mywallet.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yquery.mywallet.Adapters.AccountsAdapter;
import com.yquery.mywallet.Database.MyWalletDatabase;
import com.yquery.mywallet.Entities.IHaveEntity;
import com.yquery.mywallet.R;

public class MainActivity extends AppCompatActivity {

    protected Button iHave;
    protected RecyclerView accountsRecycler;
    protected Button addAccount;
    protected TextView lastTransactionMain;
    protected CardView cardViewLastItem;
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

        accountsRecycler.setLayoutManager(new LinearLayoutManager(this));


        addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AccountsActivity.class));
            }
        });

        cardViewLastItem.setOnClickListener(new View.OnClickListener() {
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
        iHave.setText(totalMoney + " EGP");

        adapter = new AccountsAdapter(this, MyWalletDatabase.getDatabase(getApplicationContext()).accountsDao().getAllAccounts());
        accountsRecycler.setAdapter(adapter);

        if (!totalMoney.equals("0.0")) {

            cardViewLastItem.setVisibility(View.VISIBLE);

            IHaveEntity iHaveEntity = MyWalletDatabase.getDatabase(getApplicationContext()).iHaveDao().lastWalletItem();

            if (iHaveEntity.isAdded()) {
                lastTransactionMain.setText("You added : " + iHaveEntity.getEnteredMoney() + " EGP , at : " + iHaveEntity.getTime());
            } else {
                lastTransactionMain.setText("You paid : " + iHaveEntity.getEnteredMoney() + " EGP , at : " + iHaveEntity.getTime());
            }

        } else {
            cardViewLastItem.setVisibility(View.GONE);
        }

    }

    private void initView() {
        iHave = (Button) findViewById(R.id.iHave);
        accountsRecycler = (RecyclerView) findViewById(R.id.accountsRecycler);
        addAccount = (Button) findViewById(R.id.addAccount);
        lastTransactionMain = (TextView) findViewById(R.id.lastTransactionMain);
        cardViewLastItem = (CardView) findViewById(R.id.cardViewLastItem);

    }
}
