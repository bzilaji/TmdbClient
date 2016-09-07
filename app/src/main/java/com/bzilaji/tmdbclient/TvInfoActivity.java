package com.bzilaji.tmdbclient;


import com.bzilaji.tmdbclient.service.MdbCallFactory;

import retrofit2.Call;

public class TvInfoActivity extends InfoActivityBase {


    @Override
    protected Call getCall(int id) {
        return new MdbCallFactory().createCallForTvInfo(id);
    }
}
