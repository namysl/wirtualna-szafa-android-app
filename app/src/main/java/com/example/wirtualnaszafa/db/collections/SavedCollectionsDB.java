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

    public String getPath_top(){
        return path_top;
    }

    public void setPath_top(String path){
        this.path_top = path;
    }

    public String getPath_bot(){
        return path_bot;
    }

    public void setPath_bot(String path){
        this.path_bot = path;
    }

    public String getPath_accesories(){
        return path_accesories;
    }

    public void setPath_accesories(String path){
        this.path_accesories = path;
    }

    public String getPath_shoes(){
        return path_shoes;
    }

    public void setPath_shoes(String path){
        this.path_shoes = path;
    }
}