package com.bzilaji.tmdbclient.model;

import com.bzilaji.tmdbclient.BuildConfig;
import com.bzilaji.tmdbclient.Utils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MovieCompact extends Media implements PreviewableMediaItem {

    @SerializedName("adult")
    @Expose
    public Boolean adult;

    @SerializedName("release_date")
    @Expose
    public String releaseDate;

    @SerializedName("original_title")
    @Expose
    public String originalTitle;

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("backdrop_path")
    @Expose
    public String backdropPath;

    @SerializedName("video")
    @Expose
    public Boolean video;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public List<Integer> getGenreIds() {
        return genreIds;
    }

    @Override
    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public double getRating() {
        return voteAverage != null ? voteAverage : 0;
    }

    @Override
    public String getImagePath() {
        return BuildConfig.IMAGE_BASE_URL + posterPath;
    }

    @Override
    public String getDescription() {
        return overview;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
