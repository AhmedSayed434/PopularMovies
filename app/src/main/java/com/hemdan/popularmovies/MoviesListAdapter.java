package com.hemdan.popularmovies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hemdan.moviesapi.APIEndPoints;
import com.hemdan.moviesapi.NetworkUtils;
import com.hemdan.moviesapi.dto.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MovieHolder> {

    private ArrayList<Movie> moviesList;
    private MovieItemClickListener movieItemClickListener;

    MoviesListAdapter(ArrayList<Movie> moviesList, MovieItemClickListener movieItemClickListener){
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
        String TAG = "Movie Index and Title";
        Log.d(TAG, i + " " + movie.getOriginalTitle());
        Picasso.get()
                .load(APIEndPoints.BASE_API_IMAGE_URL + movie.getPosterPath())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .fit()
                .into(movieHolder.posterImage);
        movieHolder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return (null == moviesList)? 0 : moviesList.size();
    }



    public class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView posterImage;
        Movie movie;

        MovieHolder(@NonNull View itemView) {
            super(itemView);

            posterImage = itemView.findViewById(R.id.iv_movie);

            posterImage.setOnClickListener(this);
        }

        void bind(Movie movie) {
            this.movie = movie;
        }

        @Override
        public void onClick(View view) {
            movieItemClickListener.onMovieClickListener(movie);
        }
    }

    public interface MovieItemClickListener{
        void onMovieClickListener(Movie movie);
    }

    void setMoviesList(ArrayList<Movie> moviesList){
        this.moviesList = moviesList;
        notifyDataSetChanged();
    }
}
