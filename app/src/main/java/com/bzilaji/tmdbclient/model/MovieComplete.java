package com.bzilaji.tmdbclient.model;


import com.google.gson.annotations.SerializedName;

import java.util.Collection;


public class MovieComplete extends MovieCompact implements DetailedItem {


    @SerializedName("budget")
    private long budget;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("imdb_id")
    private String imdbID;

    @SerializedName("runtime")
    private int runtime;

    @SerializedName("tagline")
    private String tagline;

    @SerializedName("status")
    private String status;


    @Override
    public String getWebPage() {
        return homepage;
    }

    @Override
    public String getImdbId() {
        return imdbID;
    }
}
