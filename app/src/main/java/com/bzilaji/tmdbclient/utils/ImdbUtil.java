package com.bzilaji.tmdbclient.utils;


public class ImdbUtil {

    public String getImdbUrl(String id) {
        return "http://www.imdb.com/title/" + id;
    }
}
