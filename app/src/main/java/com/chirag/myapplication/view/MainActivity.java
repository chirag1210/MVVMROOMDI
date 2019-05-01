package com.chirag.myapplication.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chirag.myapplication.R;
import com.chirag.myapplication.adapter.MoviesAdapter;
import com.chirag.myapplication.model.MovieModel;
import com.chirag.myapplication.viewmodel.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    /**
     * Constant tag value
     */
    private static final String TAG = "MainActivity";

    /**
     * view model instance
     */
    private MoviesViewModel viewModel;

    /**
     * recycleview instance
     */
    private RecyclerView mRecyclerView;

    /**
     * swiperefresh layout
     */
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<MovieModel> topMoviesModelsList;
    private MoviesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipe_container);

        /*
         * Attach onRefresh method to swipeRefreshLayout
         * */
        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Recycleview
         */
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        /**
         * initialse Viewmodel
         */
        viewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        viewModel.requestMovieList();

        initAdapter();
    }

    @Override
    protected void onStart() {
        super.onStart();

        viewModel.getDataObserver().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(@Nullable List<MovieModel> moviesList) {
                if(swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);

                adapter.refreshRecyclerView(moviesList);
            }
        });
    }

    @Override
    public void onRefresh() {
        viewModel.requestMovieList();
    }

    /**
     * initialise recycleview adapter
     */
    private void initAdapter()
    {
        /*
         * 1. Init List
         * */
        topMoviesModelsList = new ArrayList<>();

        /*
         * 2. Init Adapter
         * */
        adapter = new MoviesAdapter(topMoviesModelsList);

        /*
         * Set Adapter to RecylerView
         * */
        mRecyclerView.setAdapter(adapter);
    }
}
