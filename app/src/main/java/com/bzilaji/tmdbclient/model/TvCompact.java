package com.bzilaji.tmdbclient.model;


import com.bzilaji.tmdbclient.BuildConfig;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TvCompact extends Media implements PreviewMediaItem {

    @SerializedName("backdrop_path")
    @Expose
    public String backdropPath;


    @SerializedName("first_air_date")
    @Expose
    public String firstAirDate;

    @SerializedName("origin_country")
    @Expose
    public List<String> originCountry = new ArrayList<String>();


    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("original_name")
    @Expose
    public String originalName;


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
        return firstAirDate;
    }

    @Override
    public double getRating() {
        return voteAverage!=null?voteAverage:0;
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
        return name;
    }
}
