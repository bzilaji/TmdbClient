package com.bzilaji.tmdbclient.model;


import java.util.List;

public interface PreviewMediaItem extends PreviewItem {

    int getId();

    List<Integer> getGenreIds();

    String getReleaseDate();

    double getRating();
}
