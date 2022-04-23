package com.example.wirtualnaszafa;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {WardrobeDB.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    public abstract WardrobeDAO wardrobeDAO();
}
