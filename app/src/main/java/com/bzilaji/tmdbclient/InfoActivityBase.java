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
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class InfoActivityBase extends AppCompatActivity {

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
        imdbButton = findViewById(R.id.imdbButton);
        imdbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImdbPage();
            }
        });
        startDownload();
    }

    private void showImdbPage() {
    }

    private void startDownload() {
        int anInt = getIntent().getExtras().getInt(ITEM_ID);
        Log.d("Bencike", "sanya:" + anInt);
        Call<DetailedItem> call = getCall(anInt);
        call.enqueue(new Callback<DetailedItem>() {
            @Override
            public void onResponse(Call<DetailedItem> call, Response<DetailedItem> response) {
                setItem(response.body());
            }

            @Override
            public void onFailure(Call<DetailedItem> call, Throwable t) {
                Log.e("Bencike", "Error", t);
            }
        });
    }

    private void setItem(DetailedItem item) {
        detailedItem = item;
        Picasso.with(this).load(item.getImagePath())
                .placeholder(R.drawable.default_placeholder).fit().centerCrop().into(imageView);
        description.setText(item.getDescription());
        //setTitle
        rating.setText(String.format("%.1f", item.getRating()));
        collapsingToolbar.setTitle(item.getTitle());
        try {
            year.setText(format.format(format.parse(item.getReleaseDate())));
        } catch (ParseException e) {
        }
        website.setText(item.getWebPage());
        imdbButton.setVisibility(!TextUtils.isEmpty(item.getImdbId()) ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected abstract Call getCall(int id);
}
