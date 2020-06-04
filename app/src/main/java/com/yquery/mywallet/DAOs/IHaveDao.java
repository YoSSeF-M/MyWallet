package com.yquery.mywallet.DAOs;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.yquery.mywallet.Entities.IHaveEntity;

import java.util.List;

@Dao
public interface IHaveDao {

    @Query("SELECT * FROM IHaveEntity")
    List<IHaveEntity> getAll();

    @Insert
    void insert(IHaveEntity iHaveEntity);

    @Query("DELETE FROM IHaveEntity")
    void deleteAll();

}
