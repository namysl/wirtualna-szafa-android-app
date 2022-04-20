package com.example.wirtualnaszafa;

import android.content.Context;

import androidx.room.Room;

public class ClientDB {
    private Context mCtx;
    private static ClientDB mInstance;

    //our app database object
    private AppDB appDB;

    private ClientDB(Context mCtx) {
        this.mCtx = mCtx;

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        appDB = Room.databaseBuilder(mCtx, AppDB.class, "WirtualnaSzafa").build();
    }

    public static synchronized ClientDB getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new ClientDB(mCtx);
        }
        return mInstance;
    }

    public AppDB getAppDatabase() {
        return appDB;
    }
}
