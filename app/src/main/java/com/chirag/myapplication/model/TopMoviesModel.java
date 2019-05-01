package com.chirag.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TopMoviesModel implements Serializable {

    @SerializedName("movies")
    private List<MovieModel> movieModelList;

    public List<MovieModel> getMovieModelList() {
        return movieModelList;
    }

}
