package com.bzilaji.tmdbclient.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {

    @SerializedName("page")
    @Expose
    public Integer page;

    @SerializedName("results")
    @Expose
    private List<T> results = new ArrayList<T>();

    public List<T> getResults() {
        return results;
    }

    @SerializedName("total_results")
    @Expose

    public Integer totalResults;

    @SerializedName("total_pages")
    @Expose

    public Integer totalPages;
}
