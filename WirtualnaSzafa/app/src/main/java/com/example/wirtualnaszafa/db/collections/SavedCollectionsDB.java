package com.example.wirtualnaszafa.db.collections;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class SavedCollectionsDB implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "path_top")
    private String path_top;

    @ColumnInfo(name = "path_bot")
    private String path_bot;

    @ColumnInfo(name = "path_accesories")
    private String path_accesories;

    @ColumnInfo(name = "path_shoes")
    private String path_shoes;

    //getters & setters
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getPathTop(){
        return path_top;
    }

    public void setPathTop(String path){
        this.path_top = path;
    }

    public String getPathBot(){
        return path_bot;
    }

    public void setPathBot(String path){
        this.path_bot = path;
    }

    public String getPathAccesories(){
        return path_accesories;
    }

    public void setPathAccesories(String path){
        this.path_accesories = path;
    }

    public String getPathShoes(){
        return path_shoes;
    }

    public void setPathShoes(String path){
        this.path_shoes = path;
    }
}