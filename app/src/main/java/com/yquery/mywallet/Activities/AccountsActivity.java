package com.yquery.mywallet.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    protected Button plusMinus;
    protected EditText addedSubtractedValueEditText;
    FloatingActionButton floatingActionButton;

    AccountsEntity accountsEntity;

    String nameValue;
    String valueValue;

    MenuItem deleteItem;

    String plusOrMinus;

    boolean addSub = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_accounts);
        initView();

        accountsEntity = (AccountsEntity) getIntent().getSerializableExtra("account");

        plusMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                plusOrMinus = plusMinus.getText().toString();

                if (plusOrMinus.equals("Add")) {
                    plusMinus.setText("Subtract");

                    addSub = false;

                } else {
                    plusMinus.setText("Add");

                    addSub = true;

                }
            }
        });

        if (accountsEntity == null) {

            addedSubtractedValueEditText.setVisibility(View.GONE);
            plusMinus.setVisibility(View.GONE);

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
        } else {

            addedSubtractedValueEditText.setVisibility(View.VISIBLE);
            plusMinus.setVisibility(View.VISIBLE);

            accountNameEditText.setText(accountsEntity.getName());
            accountValueEditText.setText(accountsEntity.getValue());

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    nameValue = accountNameEditText.getText().toString().trim();
                    valueValue = accountValueEditText.getText().toString().trim();

                    if (addedSubtractedValueEditText.getText().toString().isEmpty()) {


                        if (!nameValue.isEmpty() && !valueValue.isEmpty()) {

                            accountsEntity.setName(nameValue);
                            accountsEntity.setValue(valueValue);

                            MyWalletDatabase.getDatabase(getApplicationContext()).accountsDao().updateAccount(accountsEntity);

                            Toast.makeText(AccountsActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                        plusOrMinus = plusMinus.getText().toString();
                        if (plusOrMinus.equals("Add")) {
                            addSub = true;
                        } else {
                            addSub = false;
                        }

                        double totalMoney;
                        if (!nameValue.isEmpty() && !valueValue.isEmpty()) {

                            if (addSub){
                                totalMoney = Double.parseDouble(valueValue) + Double.parseDouble(addedSubtractedValueEditText.getText().toString());
                            }else{
                                totalMoney = Double.parseDouble(valueValue) - Double.parseDouble(addedSubtractedValueEditText.getText().toString());
                            }

                            accountsEntity.setName(nameValue);
                            accountsEntity.setValue(String.valueOf(totalMoney));

                            MyWalletDatabase.getDatabase(getApplicationContext()).accountsDao().updateAccount(accountsEntity);

                            Toast.makeText(AccountsActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                            finish();

                        }

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

        if (accountsEntity == null) {
            deleteItem.setVisible(false);
        } else {
            deleteItem.setVisible(true);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.deleteAccount_menuItem) {

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
        plusMinus = (Button) findViewById(R.id.plusMinus);
        addedSubtractedValueEditText = (EditText) findViewById(R.id.addedSubtractedValueEditText);
    }
}