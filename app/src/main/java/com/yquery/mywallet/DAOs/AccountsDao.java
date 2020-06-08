package com.yquery.mywallet.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.yquery.mywallet.Entities.AccountsEntity;

import java.util.List;

@Dao
public interface AccountsDao {

    @Query("SELECT * FROM AccountsEntity")
    List<AccountsEntity> getAllAccounts();

    @Insert
    void insertAccount(AccountsEntity accountsEntity);

    @Update
    void updateAccount(AccountsEntity accountsEntity);

    @Query("DELETE FROM AccountsEntity")
    void deleteAllAccounts();

    @Delete
    void deleteAccount(AccountsEntity accountsEntity);

}
