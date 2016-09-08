package com.bzilaji.tmdbclient.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bzilaji.tmdbclient.R;
import com.bzilaji.tmdbclient.model.PreviewItem;
import com.bzilaji.tmdbclient.model.Result;
import com.bzilaji.tmdbclient.service.MdbCallFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class PreviewFragment extends FilterableFragmentBase {
    protected MdbCallFactory callFactory;
    private View progressBar;
    private RecyclerView recycleView;
    private View retryButton;
    private View retryContainer;
    protected static final int STATE_IDLE = 0;
    protected static final int STATE_DOWNLOADING = 1;
    protected static final int STATE_ERROR = 2;
    private int currentState = STATE_IDLE;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callFactory = new MdbCallFactory();
        startDownload();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        progressBar = null;
        recycleView.setAdapter(null);
        recycleView = null;
    }

    protected void startDownload() {
        downloadItems();
    }

    protected void downloadItems() {
        Call<Result<PreviewItem>> call = createPreviewCall();
        switchState(STATE_DOWNLOADING);
        call.enqueue(new Callback<Result<PreviewItem>>() {
            @Override
            public void onResponse(Call<Result<PreviewItem>> call, Response<Result<PreviewItem>> response) {
                previewItemAdapter.setItems(response.body().getResults());
                switchState(STATE_IDLE);
            }

            @Override
            public void onFailure(Call<Result<PreviewItem>> call, Throwable t) {
                switchState(STATE_ERROR);
            }
        });
    }

    protected abstract Call<Result<PreviewItem>> createPreviewCall();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progressBar);
        recycleView = (RecyclerView) view.findViewById(R.id.recycleView);
        recycleView.setAdapter(previewItemAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        toogleProgressbarState(currentState == STATE_DOWNLOADING);
        retryButton = view.findViewById(R.id.retry);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDownload();
            }
        });
        retryContainer = view.findViewById(R.id.retryContainer);
        toogleErrorviewState(currentState == STATE_ERROR);
    }


    protected void switchState(int state) {
        this.currentState = state;
        toogleProgressbarState(state == STATE_DOWNLOADING);
        toogleErrorviewState(state == STATE_ERROR);
    }

    private void toogleProgressbarState(boolean downloading) {
        if (progressBar != null) {
            progressBar.setVisibility(downloading ? View.VISIBLE : View.GONE);
        }
    }

    private void toogleErrorviewState(boolean error) {
        if (retryContainer != null) {
            retryContainer.setVisibility(error ? View.VISIBLE : View.GONE);
        }

    }
}
