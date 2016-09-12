package com.bzilaji.tmdbclient;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bzilaji.tmdbclient.model.DetailedItem;
import com.bzilaji.tmdbclient.utils.ImdbUtil;
import com.bzilaji.tmdbclient.utils.StartWebPageCommand;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class InfoActivityBase extends AppCompatActivity {

    private static final String KEY_ITEM = "Item";
    public static final String ITEM_ID = "ITEM_ID";
    private DetailedItem detailedItem;

    @BindView(R.id.website)
    TextView website;
    @BindView(R.id.imdbButton)
    View imdbButton;
    @BindView(R.id.rating)
    TextView rating;
    @BindView(R.id.year)
    TextView year;
    @BindView(R.id.details)
    TextView description;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.progressBar)
    View progressBar;
    @BindView(R.id.errorLayout)
    View retryContainer;
    @BindView(R.id.retry)
    View retryButton;
    @BindView(R.id.rootLayout)
    View rootLayout;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbar;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy");


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        ButterKnife.bind(this);
        initToolbar();
        downloadOrLoadSavedItem(savedInstanceState);
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void downloadOrLoadSavedItem(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            detailedItem = (DetailedItem) savedInstanceState.getSerializable(KEY_ITEM);
        }
        if (detailedItem == null) {
            startDownload();
        } else {
            progressBar.setVisibility(View.GONE);
            retryContainer.setVisibility(View.GONE);
            setItem(detailedItem);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(KEY_ITEM, detailedItem);
        super.onSaveInstanceState(outState);
    }

    @OnClick(R.id.imdbButton)
    protected void showImdbPage() {
        String url = new ImdbUtil().getImdbUrl(detailedItem.getImdbId());
        new StartWebPageCommand(url, this).execute();
    }

    @OnClick(R.id.retry)
    protected void startDownload() {
        rootLayout.setVisibility(View.GONE);
        retryContainer.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        Call<DetailedItem> call = getCall(getIntent().getExtras().getInt(ITEM_ID));
        call.enqueue(new Callback<DetailedItem>() {
            @Override
            public void onResponse(final Call<DetailedItem> call, final Response<DetailedItem> response) {
                setItem(response.body());
                progressBar.setVisibility(View.GONE);
                rootLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<DetailedItem> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                retryContainer.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setItem(DetailedItem item) {
        detailedItem = item;
        description.setText(item.getDescription());
        rating.setText(String.format("%.1f", item.getRating()));
        collapsingToolbar.setTitle(item.getTitle());
        try {
            year.setText(format.format(format.parse(item.getReleaseDate())));
        } catch (ParseException e) {
        }
        website.setText(item.getWebPage());
        imdbButton.setVisibility(!TextUtils.isEmpty(item.getImdbId()) ? View.VISIBLE : View.GONE);
        Picasso.with(this).load(item.getImagePath()).into(imageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected abstract Call getCall(int id);
}
