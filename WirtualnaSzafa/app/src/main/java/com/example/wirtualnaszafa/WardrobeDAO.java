package com.example.wirtualnaszafa;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//DAO = data access objects
@Dao
public interface WardrobeDAO{
    @Query("SELECT * FROM WardrobeDB")
    List<WardrobeDB> getAll();

    @Query("SELECT * FROM WardrobeDB ORDER BY id DESC ")
    List<WardrobeDB> getDesc();

//    @Query("SELECT path FROM WardrobeDB")
//    List<String> findPath();
    //TODO potrzebne sql query dla tagów i kolorów

    @Insert
    void insert(WardrobeDB wardrobeDB);

    @Delete
    void delete(WardrobeDB wardrobeDB);

    @Update
    void update(WardrobeDB wardrobeDB);
}
