package com.bzilaji.tmdbclient.model;


import com.bzilaji.tmdbclient.BuildConfig;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Person implements PreviewableItem {

    @SerializedName("profile_path")
    @Expose
    private String profilePath;

    @SerializedName("adult")
    @Expose
    private Boolean adult;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("known_for")
    @Expose
    private List<KnownFor> knownFor = new ArrayList<KnownFor>();

    @SerializedName("name")
    @Expose
    private String name;

    @Override
    public String getImagePath() {
        return BuildConfig.IMAGE_BASE_URL + profilePath;
    }

    @Override
    public String getDescription() {
        //Todo: make it lazy
        return getCommaSeparatedMovieTitles();
    }

    private String getCommaSeparatedMovieTitles() {
        StringBuilder result = new StringBuilder();
        if (knownFor != null) {
            for (KnownFor item : knownFor) {
                result.append(item.title);
                result.append(", ");
            }
        }
        return result.length() > 0 ? result.substring(0, result.length() - 2) : "";
    }

    @Override
    public String getTitle() {
        return name;
    }


    public class KnownFor extends Media {

        @SerializedName("adult")
        @Expose
        private Boolean adult;

        @SerializedName("release_date")
        @Expose
        private String releaseDate;

        @SerializedName("original_title")
        @Expose
        private String originalTitle;


        @SerializedName("media_type")
        @Expose
        private String mediaType;

        @SerializedName("title")
        @Expose
        private String title;

        @SerializedName("backdrop_path")
        @Expose
        private String backdropPath;

        @SerializedName("video")
        @Expose
        private Boolean video;

    }

}
