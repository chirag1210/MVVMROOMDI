package com.chirag.myapplication.db;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.chirag.myapplication.model.MovieModel;

import java.util.List;

@Dao
public interface TopmoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] inserMoviesData(List<MovieModel> movieModelsList);

    @Query("SELECT * FROM table_movies")
    LiveData<List<MovieModel>> readCachedMoviesData();
}
