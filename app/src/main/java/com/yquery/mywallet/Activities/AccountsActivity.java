package com.yquery.mywallet.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yquery.mywallet.Database.MyWalletDatabase;
import com.yquery.mywallet.Entities.AccountsEntity;
import com.yquery.mywallet.R;

public class AccountsActivity extends AppCompatActivity {

    protected EditText accountNameEditText;
    protected EditText accountValueEditText;
    FloatingActionButton floatingActionButton;

    String nameValue;
    String valueValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_accounts);
        initView();

        final AccountsEntity accountsEntity = (AccountsEntity) getIntent().getSerializableExtra("account");

        if (accountsEntity == null){
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    nameValue = accountNameEditText.getText().toString().trim();
                    valueValue = accountValueEditText.getText().toString().trim();

                    if (!nameValue.isEmpty() && !valueValue.isEmpty()) {

                        AccountsEntity accountsEntity = new AccountsEntity();

                        accountsEntity.setName(nameValue);
                        accountsEntity.setValue(valueValue);

                        MyWalletDatabase.getDatabase(getApplicationContext()).accountsDao().insertAccount(accountsEntity);

                        Toast.makeText(AccountsActivity.this, "Inserted", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }else {
            accountNameEditText.setText(accountsEntity.getName());
            accountValueEditText.setText(accountsEntity.getValue());

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    nameValue = accountNameEditText.getText().toString().trim();
                    valueValue = accountValueEditText.getText().toString().trim();

                    if (!nameValue.isEmpty() && !valueValue.isEmpty()) {

                        accountsEntity.setName(nameValue);
                        accountsEntity.setValue(valueValue);

                        MyWalletDatabase.getDatabase(getApplicationContext()).accountsDao().updateAccount(accountsEntity);

                        Toast.makeText(AccountsActivity.this, "Updated", Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }
    }

    private void initView() {
        accountNameEditText = (EditText) findViewById(R.id.accountNameEditText);
        accountValueEditText = (EditText) findViewById(R.id.accountValueEditText);
        floatingActionButton = findViewById(R.id.fab);
    }
}