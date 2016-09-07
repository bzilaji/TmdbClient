package com.bzilaji.tmdbclient.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Media {

    @SerializedName("poster_path")
    @Expose
    protected String posterPath;

    @SerializedName("popularity")
    @Expose
    protected Double popularity;

    @SerializedName("id")
    @Expose
    protected int id;

    @SerializedName("overview")
    @Expose
    protected String overview;

    @SerializedName("original_language")
    @Expose
    protected String originalLanguage;

    @SerializedName("genre_ids")
    @Expose
    protected List<Integer> genreIds = new ArrayList<Integer>();

    @SerializedName("vote_count")
    @Expose
    protected Integer voteCount;

    @SerializedName("vote_average")
    @Expose
    protected Double voteAverage;

}
