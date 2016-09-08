package com.bzilaji.tmdbclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.bzilaji.tmdbclient.fragment.adapter.PreviewItemAdapter;


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
