package com.chirag.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chirag.myapplication.R;
import com.chirag.myapplication.model.MovieModel;

import java.util.List;

/**
 * Bind data to recycleview
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    /**
     * movies list
     */
    private List<MovieModel> moviesList;

    public MoviesAdapter(List<MovieModel> moviesList) {
        this.moviesList = moviesList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            year = (TextView) view.findViewById(R.id.release);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MovieModel movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.year.setText(movie.getReleased());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    /**
     * Refresh recycleview database
     * @param newMoviesList pass updated movies list
     */
    public void refreshRecyclerView(List<MovieModel> newMoviesList)
    {
        if (this.moviesList != null)
        {
            this.moviesList.clear();
            this.moviesList.addAll(newMoviesList);
        }
        else
        {
            this.moviesList = newMoviesList;
        }

        notifyDataSetChanged();
    }
}