package com.bzilaji.tmdbclient.fragment;


import android.content.Intent;

import com.bzilaji.tmdbclient.InfoActivityBase;
import com.bzilaji.tmdbclient.MovieInfoActivity;
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
        Intent intent = new Intent(getContext(), MovieInfoActivity.class);
        intent.putExtra(InfoActivityBase.ITEM_ID, model.getId());
        startActivity(intent);
    }

    @Override
    protected Call createPreviewCall() {
        return callFactory.createCallTopMovies();
    }
}
