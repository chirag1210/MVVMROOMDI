package com.chirag.myapplication.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "table_movies", indices = {@Index(value = "title", unique = true)})
public class MovieModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "title")
    @SerializedName("Title")
    private String title;

    @ColumnInfo(name = "year")
    @SerializedName("Year")
    private String year;

    @ColumnInfo(name = "release")
    @SerializedName("Released")
    private String released;

    @ColumnInfo(name = "director")
    @SerializedName(value = "Director", alternate = "Director ")
    private String director;

    /*
     * Constructor
     * */

    public MovieModel(int id, String title, String year, String released, String director) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.released = released;
        this.director = director;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getReleased() {
        return released;
    }

    public String getDirector() {
        return director;
    }
}
