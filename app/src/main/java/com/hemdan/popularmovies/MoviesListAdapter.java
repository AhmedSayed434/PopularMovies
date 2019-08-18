package com.hemdan.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hemdan.moviesapi.dto.Movie;

import java.util.ArrayList;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MovieHolder> {
    private final String TAG = "Movie Index and Title";

    private ArrayList<Movie> moviesList;
    private MovieItemClickListener movieItemClickListener;

    public MoviesListAdapter(ArrayList<Movie> moviesList, MovieItemClickListener movieItemClickListener){
        this.moviesList = moviesList;
        this.movieItemClickListener = movieItemClickListener;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View movieItem = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_movie, viewGroup,false);
        return new MovieHolder(movieItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder movieHolder, int i) {
        Movie movie = moviesList.get(i);
        Log.d(TAG, i + " " + movie.getOriginalTitle());
//        Picasso.get()
//                .load("http://image.tmdb.org/t/p/w185/" + movie.getPosterPath())
//                .into(posterImage);
        Glide.with(movieHolder.itemView.getContext())
                .load("http://image.tmdb.org/t/p/w185/" + movie.getPosterPath())
                .override(185,400)
                .into(movieHolder.posterImage);
        movieHolder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView posterImage;
        Movie movie;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);

            posterImage = itemView.findViewById(R.id.iv_movie);

            posterImage.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            this.movie = movie;
//            Picasso.get()
//                    .load("http://image.tmdb.org/t/p/w185/" + movie.getPosterPath())
//                    .into(posterImage);
        }

        @Override
        public void onClick(View view) {
            movieItemClickListener.onMovieClickListener(movie);
        }
    }

    public interface MovieItemClickListener{
        void onMovieClickListener(Movie movie);
    }
}
