package com.hemdan.moviesapi;

import android.text.TextUtils;
import android.util.Pair;

import com.hemdan.moviesapi.dto.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class APIEndPoints {
    private static final String BASE_API_URL = "https://api.themoviedb.org/3/movie";
    private static final String API_KEY_PARAM = "api_key";
    //TODO Add your own API KEY
    private static final String API_KEY = "";
    private static final String LANGUAGE_PARAM = "language";
    private static final String LANGUAGE = "en-US";
    private static final String PAGE_PARAM = "page";
    private static final String PAGE = "1";
    private static final String MOST_POPULAR_PATH = "/popular";
    private static final String TOP_RATED_PATH = "/top_rated";

    private static final String RESULTS_KEY = "results";
    private static final String VOTE_AVERAGE_KEY = "vote_average";
    private static final String OVERVIEW_KEY = "overview";
    private static final String RELEASE_DATE_KEY = "release_date";
    private static final String ORIGINAL_TITLE_KEY = "original_title";
    private static final String POSTER_PATH_KEY = "poster_path";


    public static final String BASE_API_IMAGE_URL = "https://api.themoviedb.org/3/movie";

    public static ArrayList<Movie> getMostPopularMoviesList(){
        String result = null;

        try {
            result = NetworkUtils.getResponseFromHttpUrl(buildURL(MOST_POPULAR_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!TextUtils.isEmpty(result)){
            return parseMovieList(result);
        } else {
            return new ArrayList<>();
        }
    }

    public static ArrayList<Movie> getTopRatedMoviesList(){
        String result = null;

        try {
            result = NetworkUtils.getResponseFromHttpUrl(buildURL(TOP_RATED_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!TextUtils.isEmpty(result)){
            return parseMovieList(result);
        } else {
            return new ArrayList<>();
        }
    }

    private static URL buildURL(String path){
        List<Pair<String, String>> queryParams = new ArrayList<>();
        queryParams.add(new Pair<>(API_KEY_PARAM, API_KEY));
        queryParams.add(new Pair<>(LANGUAGE_PARAM, LANGUAGE));
        queryParams.add(new Pair<>(PAGE_PARAM, PAGE));

        String fullUrl = BASE_API_URL + path;

        return NetworkUtils.buildUrl(fullUrl, queryParams);
    }

    private static ArrayList<Movie> parseMovieList(String result) {
        ArrayList<Movie> movieList = new ArrayList<>();
        try {
            JSONObject responseObject = new JSONObject(result);
            JSONArray resultsArray = responseObject.getJSONArray(RESULTS_KEY);
            for (int i = 0; i < resultsArray.length(); i++){
                Movie movie = parseMovieObject(resultsArray.get(i).toString());
                if(movie != null) {
                    movieList.add(movie);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieList;
    }

    private static Movie parseMovieObject(String movieStringObject) {
        Movie movie = null;
        try {
            JSONObject movieObject = new JSONObject(movieStringObject);
             movie = new Movie((float)movieObject.getDouble(VOTE_AVERAGE_KEY),
                    movieObject.getString(OVERVIEW_KEY),
                    movieObject.getString(RELEASE_DATE_KEY),
                    movieObject.getString(ORIGINAL_TITLE_KEY),
                    movieObject.getString(POSTER_PATH_KEY)
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movie;
    }
}
