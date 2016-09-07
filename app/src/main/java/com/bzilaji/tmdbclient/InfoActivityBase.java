package com.bzilaji.tmdbclient;


import android.os.Bundle;
import android.os.PersistableBundle;
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
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class InfoActivityBase extends AppCompatActivity {

    private static final String KEY_ITEM = "Item";
    public static final String ITEM_ID = "ITEM_ID";
    private DetailedItem detailedItem;
    private TextView website;
    private View imdbButton;
    private TextView rating;
    private TextView genre;
    private TextView year;
    private TextView description;
    private ImageView imageView;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy");
    private CollapsingToolbarLayout collapsingToolbar;
    private View progressBar;
    private View retryContainer;
    private View retryButton;
    private View rootLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        rating = (TextView) findViewById(R.id.rating);
        genre = (TextView) findViewById(R.id.genre);
        year = (TextView) findViewById(R.id.year);
        description = (TextView) findViewById(R.id.details);
        imageView = (ImageView) findViewById(R.id.imageView);
        website = (TextView) findViewById(R.id.website);
        progressBar = findViewById(R.id.progressBar);
        imdbButton = findViewById(R.id.imdbButton);
        imdbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImdbPage();
            }
        });
        retryContainer = findViewById(R.id.errorLayout);
        retryButton = findViewById(R.id.retry);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDownload();
            }
        });
        rootLayout = findViewById(R.id.rootLayout);
        downloadOrLoadSavedItem(savedInstanceState);
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

    private void showImdbPage() {

    }

    private void startDownload() {
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
        Log.d("Bencike", "item set:" + item.getTitle()+" ,"+item.getWebPage());
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
