package com.example.wirtualnaszafa;

import android.content.Context;

import androidx.room.Room;

public class ClientDB {
    private Context cntx;
    private static ClientDB clientInstance;
    private AppDB appDB;

    private ClientDB(Context cntx) {
        this.cntx = cntx;
        appDB = Room.databaseBuilder(cntx, AppDB.class, "WirtualnaSzafa").build();
    }

    public static synchronized ClientDB getInstance(Context cntx){
        if (clientInstance == null) {
            clientInstance = new ClientDB(cntx);
        }
        return clientInstance;
    }

    public AppDB getAppDatabase(){
        return appDB;
    }
}
