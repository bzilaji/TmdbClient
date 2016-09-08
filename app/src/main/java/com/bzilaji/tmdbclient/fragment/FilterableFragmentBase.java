package com.bzilaji.tmdbclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bzilaji.tmdbclient.R;
import com.bzilaji.tmdbclient.fragment.adapter.PreviewItemAdapter;
import com.bzilaji.tmdbclient.model.PreviewableItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public abstract class FilterableFragmentBase extends Fragment {

    protected PreviewItemAdapter previewItemAdapter;
    private CharSequence query;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        previewItemAdapter = createAdapter();
        setQueryInternal();
    }

    protected abstract PreviewItemAdapter createAdapter();

    public void setQuery(CharSequence query) {
        this.query = query;
        setQueryInternal();
    }

    private void setQueryInternal() {
        if (previewItemAdapter != null) {
            previewItemAdapter.getFilter().filter(query);
        }
    }
}
