package com.bzilaji.tmdbclient.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Genres {


    @SerializedName("genres")
    @Expose
    private List<Genre> genres = new ArrayList<Genre>();


    public List<Genre> getGenres() {
        return genres;
    }

    public class Genre {

        @SerializedName("id")
        @Expose
        private Integer id;

        @SerializedName("name")
        @Expose
        private String name;

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

}
