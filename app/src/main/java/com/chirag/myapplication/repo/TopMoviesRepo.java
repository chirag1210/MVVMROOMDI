package com.chirag.myapplication.repo;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.util.Log;

import com.chirag.myapplication.db.AppDatabase;
import com.chirag.myapplication.model.MovieModel;
import com.chirag.myapplication.model.TopMoviesModel;
import com.chirag.myapplication.model.TopMoviesRequest;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Repositor interact with app and ViewModel
 */
public class TopMoviesRepo {

    private static final String TAG = "TopMoviesRepo";

    /*
     * Global Instance of AppDatabase
     * */
    private AppDatabase appDatabase;

    /*
     * MediatorLiveData to observe the Database (Single Source of Truth)
     * */
    private MediatorLiveData<List<MovieModel>> mediatorLiveData;

    /*
     * Constructor
     * */
    public TopMoviesRepo(AppDatabase appDatabase)
    {
        this.appDatabase = appDatabase;

        /*
         * Init the Global Instance of MediatorLiveData
         * */
        mediatorLiveData = new MediatorLiveData<>();
        /*
         * Add Database as a Source to MediatorLiveData (Single Source of Truth)
         * */
        mediatorLiveData.addSource(appDatabase.getTopmoviesDao().readCachedMoviesData(), new Observer<List<MovieModel>>()
        {
            @Override
            public void onChanged(List<MovieModel> movieModels)
            {
                if (movieModels != null)
                {
                    mediatorLiveData.postValue(movieModels);
                }
            }
        });
    }

    public MediatorLiveData<List<MovieModel>> getMediatorLiveData() {
        return mediatorLiveData;
    }

    /**
     * call moview api
     */
    public void requestMoviesAPI() {
        /*
         * OkHttp Logging Interceptor
         * */
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl("https://api.myjson.com/");

        Retrofit retrofit = retrofitBuilder.build();

        TopMoviesRequest topMoviesRequest = retrofit.create(TopMoviesRequest.class);
        Call<TopMoviesModel> request = topMoviesRequest.getMoviesData();
        request.enqueue(new Callback<TopMoviesModel>() {
            @Override
            public void onResponse(@NonNull Call<TopMoviesModel> call, @NonNull Response<TopMoviesModel> response) {
                TopMoviesModel responseBody = response.body();

                List<MovieModel> moviesList = responseBody.getMovieModelList();
                //Toast.makeText(mContext,"Movies size "+moviesList.size(),Toast.LENGTH_LONG).show();
                long[] results = appDatabase.getTopmoviesDao().inserMoviesData(moviesList);
                for (long singleResult : results)
                {
                    Log.i(TAG, String.format("Inserted with Row ID %d", singleResult));
                }
            }

            @Override
            public void onFailure(@NonNull Call<TopMoviesModel> call, @NonNull Throwable throwable) {
                Log.e(TAG, "Failed to fetch Movies List", throwable);
            }
        });
    }
}
