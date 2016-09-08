package com.bzilaji.tmdbclient.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GenreAdapter {

    private Map<Integer, Genres.Genre> map;

    public GenreAdapter(List<Genres.Genre> genres) {
        if (genres != null) {
            map = new HashMap<>(genres.size());
            for (Genres.Genre genre : genres) {
                map.put(genre.getId(), genre);
            }
        } else {
            map = Collections.emptyMap();
        }
    }

    public String toHumanReadable(List<Integer> ids) {
        List<String> items = new ArrayList<>(ids.size());
        for (int id : ids) {
            if (map.containsKey(id)) {
                items.add(map.get(id).getName());
            }
        }
        return getCommaSeparatedMovieTitles(items);
    }

    private String getCommaSeparatedMovieTitles(List<String> items) {
        StringBuilder result = new StringBuilder();
        if (items != null) {
            for (String item : items) {
                result.append(item);
                result.append(", ");
            }
        }
        return result.length() > 0 ? result.substring(0, result.length() - 2) : "";
    }

}
