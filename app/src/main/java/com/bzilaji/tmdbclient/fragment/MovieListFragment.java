package com.bzilaji.tmdbclient.fragment;


import android.content.Intent;

import com.bzilaji.tmdbclient.Henson;
import com.bzilaji.tmdbclient.model.Genres;
import com.bzilaji.tmdbclient.model.PreviewMediaItem;

import retrofit2.Call;

public class MovieListFragment extends MediaFragment {

    @Override
    protected Call<Genres> createGenreCall() {
        return callFactory.createCallMoviesGenres();
    }

    @Override
    protected void itemSelected(PreviewMediaItem model) {
        Intent intent = Henson.with(getContext()).gotoMovieInfoActivity().itemId(model.getId()).build();
        startActivity(intent);
    }

    @Override
    protected Call createPreviewCall() {
        return callFactory.createCallTopMovies();
    }
}
