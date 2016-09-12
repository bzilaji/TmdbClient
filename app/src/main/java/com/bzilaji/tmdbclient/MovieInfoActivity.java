package com.bzilaji.tmdbclient;


import android.util.Log;


import com.bzilaji.tmdbclient.service.MdbCallFactory;
import com.f2prateek.dart.HensonNavigable;

import retrofit2.Call;

@HensonNavigable
public class MovieInfoActivity extends InfoActivityBase {


    @Override
    protected Call getCall(int id) {
        return new MdbCallFactory().createCallForMovieInfo(id);
    }
}
