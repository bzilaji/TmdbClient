package com.bzilaji.tmdbclient.fragment;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bzilaji.tmdbclient.R;

import retrofit2.Call;

public class PeopleListFragment extends PreviewFragment{
    @Override
    protected Call createPreviewCall() {
        return callFactory.createCallTopPeople();
    }

    @Override
    protected PreviewItemAdapter createAdapter() {
        return new PreviewItemAdapter() {
            @Override
            public PreviewItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new PreviewItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item, parent, false));
            }
        };
    }
}
