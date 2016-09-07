package com.bzilaji.tmdbclient.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvComplete extends TvCompact implements DetailedItem {


    @SerializedName("episode_run_time")
    @Expose
    private List<Integer> episodeRunTime;

    @SerializedName("homepage")
    @Expose
    private String homepage;

    @SerializedName("in_production")
    @Expose
    private boolean inProduction;

    @SerializedName("languages")
    @Expose
    private List<String> languages;

    @SerializedName("last_air_date")
    @Expose
    private String lastAirDate;

    @SerializedName("number_of_episodes")
    @Expose
    private int numberOfEpisodes;

    @SerializedName("number_of_seasons")
    @Expose
    private int numberOfSeasons;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("type")
    @Expose
    private String type;

    @Override
    public String getWebPage() {
        return homepage;
    }

    @Override
    public String getImdbId() {
        return null;
    }
}
