package com.example.wirtualnaszafa.db.collections;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SavedCollectionsDAO {
    //show all items in default manner
    @Query("SELECT * FROM SavedCollectionsDB")
    List<SavedCollectionsDB> getAll();

    //show all items in descending order by id, so new items first
    @Query("SELECT * FROM SavedCollectionsDB ORDER BY id DESC ")
    List<SavedCollectionsDB> getAllDesc();

    @Insert
    void insert(SavedCollectionsDB savedCollectionsDB);

    @Delete
    void delete(SavedCollectionsDB savedCollectionsDB);

    @Update
    void update(SavedCollectionsDB savedCollectionsDB);
}
