package com.example.wirtualnaszafa.db.all_elements;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//DAO = data access objects
@Dao
public interface WardrobeDAO{
    //show all items in default manner
    @Query("SELECT * FROM WardrobeDB")
    List<WardrobeDB> getAll();

    //show all items in descending order by id, so new items first
    @Query("SELECT * FROM WardrobeDB ORDER BY id DESC ")
    List<WardrobeDB> getAllDesc();

    //only show items with given tag, e.g. shoes
    @Query("SELECT * FROM WardrobeDB WHERE tag=:tag")
    List<WardrobeDB> getClothesByTag(String tag);

    //only show items with given color, e.g. black
    @Query("SELECT * FROM WardrobeDB WHERE color=:color")
    List<WardrobeDB> getClothesByColor(String color);

    @Insert
    void insert(WardrobeDB wardrobeDB);

    @Delete
    void delete(WardrobeDB wardrobeDB);

    @Update
    void update(WardrobeDB wardrobeDB);
}
