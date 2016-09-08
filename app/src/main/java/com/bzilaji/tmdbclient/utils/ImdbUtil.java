package com.bzilaji.tmdbclient.utils;


public class ImdbUtil {

    public static final String IMDB_BASE_URL = "http://www.imdb.com/title/";

    public String getImdbUrl(String id) {
        return IMDB_BASE_URL + id;
    }
}
