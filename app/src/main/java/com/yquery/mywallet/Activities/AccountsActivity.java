package com.yquery.mywallet.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yquery.mywallet.Database.MyWalletDatabase;
import com.yquery.mywallet.Entities.AccountsEntity;
import com.yquery.mywallet.R;

public class AccountsActivity extends AppCompatActivity {

    protected EditText accountNameEditText;
    protected EditText accountValueEditText;
    FloatingActionButton floatingActionButton;

    AccountsEntity accountsEntity;

    String nameValue;
    String valueValue;

    MenuItem deleteItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_accounts);
        initView();

        accountsEntity = (AccountsEntity) getIntent().getSerializableExtra("account");


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
                        finish();
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
                        finish();
                    }
                }
            });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.account_menu, menu);

        deleteItem = menu.findItem(R.id.deleteAccount_menuItem);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (accountsEntity == null){
            deleteItem.setVisible(false);
        }else{
            deleteItem.setVisible(true);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.deleteAccount_menuItem){

            new AlertDialog.Builder(AccountsActivity.this)
                    .setTitle("Delete Account")
                    .setMessage("Are you sure you want to delete this Account ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MyWalletDatabase.getDatabase(getApplicationContext()).accountsDao().deleteAccount(accountsEntity);
                            finish();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).show();


        }

        return true;
    }

    private void initView() {
        accountNameEditText = (EditText) findViewById(R.id.accountNameEditText);
        accountValueEditText = (EditText) findViewById(R.id.accountValueEditText);
        floatingActionButton = findViewById(R.id.fab);
    }
}