package com.bzilaji.tmdbclient;


import android.util.Log;


import com.bzilaji.tmdbclient.service.MdbCallFactory;

import retrofit2.Call;

public class MovieInfoActivity extends InfoActivityBase {


    @Override
    protected Call getCall(int id) {
        return new MdbCallFactory().createCallForMovieInfo(id);
    }
}
