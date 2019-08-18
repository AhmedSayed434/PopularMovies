package com.hemdan.popularmovies;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hemdan.moviesapi.APIEndPoints;
import com.hemdan.moviesapi.NetworkUtils;
import com.hemdan.moviesapi.dto.Movie;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviesListAdapter.MovieItemClickListener {
    private final String TOP_RATED_LIST_KEY = "TOP_RATED_LIST_KEY";
    private final String MOST_POPULAR_LIST_KEY = "MOST_POPULAR_LIST_KEY";
    private final int WIDTH_DP = 140;

    private ArrayList<Movie> topRatedMoviesList;
    private ArrayList<Movie> mostPopularMoviesList;

    private RecyclerView recyclerView;
    private ProgressBar loadingData;

    private MoviesListAdapter moviesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_movies_list);
//        loadingData = findViewById(R.id.pb_loading_data);

        if(savedInstanceState != null){
            topRatedMoviesList = savedInstanceState.getParcelableArrayList(TOP_RATED_LIST_KEY);
            mostPopularMoviesList = savedInstanceState.getParcelableArrayList(MOST_POPULAR_LIST_KEY);
            createAdapterAndShowData();
        } else {
            loadData();
        }

    }

    private void createAdapterAndShowData() {
//        loadingData.setVisibility(View.INVISIBLE);
//        recyclerView.setVisibility(View.VISIBLE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, calculateNoOfColumns(MainActivity.this, WIDTH_DP));
        moviesListAdapter = new MoviesListAdapter(mostPopularMoviesList, MainActivity.this);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(moviesListAdapter);
    }

    private void loadData() {
        new ConnectionChecker().execute();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(TOP_RATED_LIST_KEY, topRatedMoviesList);
        outState.putParcelableArrayList(MOST_POPULAR_LIST_KEY, mostPopularMoviesList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.main_menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.sort_by){
            if(item.getTitle().toString().equals(getString(R.string.most_popular))){
                item.setTitle(R.string.top_rated);
                moviesListAdapter = new MoviesListAdapter(mostPopularMoviesList, MainActivity.this);
                recyclerView.setAdapter(moviesListAdapter);
                return true;
            } else {
                item.setTitle(R.string.most_popular);
                moviesListAdapter = new MoviesListAdapter(topRatedMoviesList, MainActivity.this);
                recyclerView.setAdapter(moviesListAdapter);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieClickListener(Movie movie) {
        Toast.makeText(MainActivity.this, movie.getOriginalTitle(), Toast.LENGTH_SHORT).show();
    }

    class DataLoaderFromAPI extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            topRatedMoviesList = APIEndPoints.getTopRatedMoviesList();
            mostPopularMoviesList = APIEndPoints.getMostPopularMoviesList();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if((topRatedMoviesList != null && !topRatedMoviesList.isEmpty()) &&
                    (mostPopularMoviesList != null && !mostPopularMoviesList.isEmpty())){
                createAdapterAndShowData();
            } else {
                showErrorLoadingDataDialog();
            }
        }
    }

    class ConnectionChecker extends AsyncTask<Void, Void, Boolean>{


        @Override
        protected Boolean doInBackground(Void... voids) {
            return NetworkUtils.isOnline();
        }

        @Override
        protected void onPostExecute(Boolean isConnected) {
            super.onPostExecute(isConnected);
            if(!isConnected){
                showErrorLoadingDataDialog();
            } else {
                new DataLoaderFromAPI().execute();
            }
        }
    }

    public void showErrorLoadingDataDialog(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        alertBuilder.setMessage(R.string.connection_error_message);
        alertBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.this.finish();
            }
        });
        alertBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                loadData();
            }
        });
        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    public static int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidthdp=180
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (screenWidthDp / columnWidthDp + 0.5); // +0.5 for correct rounding to int.
        return noOfColumns;
    }
}
