package com.example.wirtualnaszafa.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.wirtualnaszafa.db.all_elements.WardrobeDAO;
import com.example.wirtualnaszafa.db.all_elements.WardrobeDB;
import com.example.wirtualnaszafa.db.collections.SavedCollectionsDAO;
import com.example.wirtualnaszafa.db.collections.SavedCollectionsDB;

@Database(entities = {WardrobeDB.class, SavedCollectionsDB.class}, version = 2)
public abstract class AppDB extends RoomDatabase {
    public abstract WardrobeDAO wardrobeDAO();
    public abstract SavedCollectionsDAO savedCollectionsDAO();
}
