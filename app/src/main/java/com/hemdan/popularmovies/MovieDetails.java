package com.hemdan.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.hemdan.moviesapi.APIEndPoints;
import com.hemdan.moviesapi.dto.Movie;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class MovieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent mainActivityIntent = getIntent();
        Movie selectedMovie = mainActivityIntent.getParcelableExtra(MainActivity.SELECTED_MOVIE_KEY);

        TextView originalTitleTextView = findViewById(R.id.tv_original_title);
        TextView releaseDateTextView = findViewById(R.id.tv_release_date);
        TextView averageRateTextView = findViewById(R.id.tv_average_rate);
        TextView overviewTextView = findViewById(R.id.tv_overview);
        ImageView moviePosterImageView = findViewById(R.id.iv_movie_poster);

        overviewTextView.setText(selectedMovie.getOverview());
        originalTitleTextView.setText(selectedMovie.getOriginalTitle());
        releaseDateTextView.setText(selectedMovie.getReleaseDate());
        averageRateTextView.setText(String.format(Locale.US, "%2.1f/10", selectedMovie.getVoteAverage()));


        Picasso.get()
                .load(APIEndPoints.BASE_API_IMAGE_URL + selectedMovie.getPosterPath())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .fit()
                .into(moviePosterImageView);
    }
}
