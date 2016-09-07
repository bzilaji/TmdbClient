package com.bzilaji.tmdbclient.fragment;


import android.content.Intent;
import android.util.Log;

import com.bzilaji.tmdbclient.InfoActivityBase;
import com.bzilaji.tmdbclient.MovieInfoActivity;
import com.bzilaji.tmdbclient.TvInfoActivity;
import com.bzilaji.tmdbclient.model.Genres;
import com.bzilaji.tmdbclient.model.PreviewableMediaItem;

import retrofit2.Call;

public class TvListFragment extends MediaFragment {

    @Override
    protected Call<Genres> createGenreCall() {
        return callFactory.createCallTvGenres();
    }

    @Override
    protected void itemSelected(PreviewableMediaItem model) {
        Intent intent = new Intent(getContext(), TvInfoActivity.class);
        intent.putExtra(InfoActivityBase.ITEM_ID, model.getId());
        startActivity(intent);
    }

    @Override
    protected Call createPreviewCall() {
        return callFactory.createCallTopTv();
    }
}
