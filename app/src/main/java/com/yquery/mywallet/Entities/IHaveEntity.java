package com.yquery.mywallet.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "IHaveEntity")
public class IHaveEntity {

    @PrimaryKey(autoGenerate = true)
    public int transactionID;

    @ColumnInfo
    public double enteredMoney;

    @ColumnInfo
    public boolean added;

    @ColumnInfo
    public String details;

    @ColumnInfo
    public String date;

    @ColumnInfo
    public String time;

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public double getEnteredMoney() {
        return enteredMoney;
    }

    public void setEnteredMoney(double enteredMoney) {
        this.enteredMoney = enteredMoney;
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
