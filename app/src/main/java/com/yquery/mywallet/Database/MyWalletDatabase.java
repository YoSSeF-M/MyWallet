package com.yquery.mywallet.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.yquery.mywallet.DAOs.IHaveDao;
import com.yquery.mywallet.Entities.IHaveEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {IHaveEntity.class}, version = 2, exportSchema = false)
public abstract class MyWalletDatabase extends RoomDatabase {

    public abstract IHaveDao iHaveDao();

    private static volatile MyWalletDatabase INSTANCE;
    private static final int NO_OF_THREADS = 4;
    static final ExecutorService writeExecuter = Executors.newFixedThreadPool(NO_OF_THREADS);



    public static MyWalletDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (MyWalletDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MyWalletDatabase.class, "MyWallet")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }



}
