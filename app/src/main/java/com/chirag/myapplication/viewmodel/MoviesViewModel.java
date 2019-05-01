package com.chirag.myapplication.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.chirag.myapplication.TopMoviesApp;
import com.chirag.myapplication.model.MovieModel;
import com.chirag.myapplication.model.TopMoviesModel;
import com.chirag.myapplication.model.TopMoviesRequest;
import com.chirag.myapplication.repo.TopMoviesRepo;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesViewModel extends AndroidViewModel {

    private static final String TAG = "MoviesViewModel";

    /**
     * Context instance
     */
    private Context mContext;

    /*
     * Instance of LiveData
     * */
    private MutableLiveData<List<MovieModel>> dataObserver = new MutableLiveData<>();

    /*
     * Inject the Instance of TopMoviesRepo
     * */
    private TopMoviesRepo topMoviesRepo;
    /**
     * Constructor
     *
     * @param application
     */
    public MoviesViewModel(@NonNull Application application) {
        super(application);
        mContext = application.getBaseContext();

        topMoviesRepo = ((TopMoviesApp) application).provideRepo();
    }

    /**
     * Get data observer after received data
     * @return
     */
    public LiveData<List<MovieModel>> getDataObserver() {
        return topMoviesRepo.getMediatorLiveData();
    }

    /**
     * request to call movie api
     */
    public void requestMovieList()
    {
        topMoviesRepo.requestMoviesAPI();
    }

}
