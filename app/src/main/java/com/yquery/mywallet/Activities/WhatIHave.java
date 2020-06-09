package com.yquery.mywallet.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yquery.mywallet.Adapters.IHaveAdapter;
import com.yquery.mywallet.Database.MyWalletDatabase;
import com.yquery.mywallet.Entities.IHaveEntity;
import com.yquery.mywallet.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WhatIHave extends AppCompatActivity {

    protected RecyclerView recycler;
    protected Button plusMinus;
    protected EditText money;
    protected ImageButton save;
    protected EditText details;

    double moneyVal;
    boolean addSub = true;
    String detailsVal;
    String plusOrMinus;
    String date;
    String time;

    double totalMoney;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    List<IHaveEntity> list;

    IHaveAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_what_i_have);
        initView();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recycler.setLayoutManager(layoutManager);
        list = MyWalletDatabase.getDatabase(getApplicationContext()).iHaveDao().getAll();

        adapter = new IHaveAdapter(this, list);

        recycler.setAdapter(adapter);

        preferences = getSharedPreferences("myMoney", MODE_PRIVATE);
        editor = preferences.edit();


        totalMoney = Double.parseDouble(preferences.getString("money", "0.0"));

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


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                totalMoney = Double.parseDouble(preferences.getString("money", "0.0"));

                if (!money.getText().toString().isEmpty()) {

                    detailsVal = details.getText().toString();

                    if (detailsVal.isEmpty()) {
                        detailsVal = "No details";
                    }

                    plusOrMinus = plusMinus.getText().toString();
                    if (plusOrMinus.equals("Add")) {
                        addSub = true;
                    } else {
                        addSub = false;
                    }

                    try {
                        moneyVal = Double.parseDouble(money.getText().toString());
                    } catch (NumberFormatException ex) {

                    }

                    if (addSub) {

                        totalMoney = totalMoney + moneyVal;

                        editor.putString("money", String.valueOf(totalMoney));
                        editor.apply();
                    } else {
                        totalMoney = totalMoney - moneyVal;

                        editor.putString("money", String.valueOf(totalMoney));
                        editor.apply();
                    }

                    date = new SimpleDateFormat("EEE, dd-MMM-yyyy", Locale.getDefault()).format(new Date());
                    time = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());

                    IHaveEntity haveEntity = new IHaveEntity();

                    haveEntity.setAdded(addSub);
                    haveEntity.setEnteredMoney(moneyVal);
                    haveEntity.setDetails(detailsVal);
                    haveEntity.setDate(date);
                    haveEntity.setTime(time);

                    list.add(haveEntity);
                    adapter.notifyDataSetChanged();

                    money.setText("");
                    details.setText("");
                    plusMinus.setText("Add");

                    MyWalletDatabase.getDatabase(getApplicationContext()).iHaveDao().insert(haveEntity);

                    Toast.makeText(WhatIHave.this, "Inserted", Toast.LENGTH_SHORT).show();
                    recycler.smoothScrollToPosition(adapter.getItemCount() - 1);
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.wallet_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.deleteAll_menuItem) {

            new AlertDialog.Builder(WhatIHave.this)
                    .setTitle("Delete History")
                    .setMessage("Are you sure you want to delete ALL history ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MyWalletDatabase.getDatabase(getApplicationContext()).iHaveDao().deleteAll();
                            list.clear();
                            adapter.notifyDataSetChanged();
                            editor.putString("money", String.valueOf(0.0));
                            editor.apply();
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
        recycler = (RecyclerView) findViewById(R.id.recycler);
        plusMinus = (Button) findViewById(R.id.plusMinus);
        money = (EditText) findViewById(R.id.money);
        save = (ImageButton) findViewById(R.id.save);
        details = (EditText) findViewById(R.id.details);

    }
}
