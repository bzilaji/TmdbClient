package com.bzilaji.tmdbclient.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface PreviewableMediaItem extends PreviewableItem {

    int getId();

    List<Integer> getGenreIds();

    String getReleaseDate();

    double getRating();
}
