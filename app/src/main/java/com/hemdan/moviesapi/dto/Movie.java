package com.hemdan.moviesapi.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private Float voteAverage;
    private String overview;
    private String releaseDate;
    private String originalTitle;
    private String posterPath;

    public Movie(Float voteAverage, String overview, String releaseDate, String originalTitle, String posterPath) {
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
    }

    private Movie(Parcel parcel){
        voteAverage = parcel.readFloat();
        overview = parcel.readString();
        releaseDate = parcel.readString();
        originalTitle = parcel.readString();
        posterPath = parcel.readString();
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }


    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(overview);
        parcel.writeString(originalTitle);
        parcel.writeString(posterPath);
        parcel.writeString(releaseDate);
        parcel.writeFloat(voteAverage);
    }

    public final Parcelable.Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };
}
