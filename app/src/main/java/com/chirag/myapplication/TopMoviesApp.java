package com.chirag.myapplication;

import android.app.Application;

import com.chirag.myapplication.db.AppDatabase;
import com.chirag.myapplication.repo.TopMoviesRepo;

/**
 * Application class
 */
public class TopMoviesApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * Provide repo instance
     *
     * @return TopMoviesRepo instance
     */
    public TopMoviesRepo provideRepo()
    {
        return new TopMoviesRepo(provideAppDatabase());
    }

    /**
     * Provide app database instance
     *
     * @return AppDatabase instance
     */
    private AppDatabase provideAppDatabase()
    {
        return AppDatabase.getInstance(getBaseContext());
    }
}
