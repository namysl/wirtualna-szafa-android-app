package com.example.wirtualnaszafa.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.wirtualnaszafa.db.all_elements.WardrobeDAO;
import com.example.wirtualnaszafa.db.all_elements.WardrobeDB;

@Database(entities = {WardrobeDB.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    public abstract WardrobeDAO wardrobeDAO();
}
