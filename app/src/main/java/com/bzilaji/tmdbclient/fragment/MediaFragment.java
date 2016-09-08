package com.bzilaji.tmdbclient.fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bzilaji.tmdbclient.R;
import com.bzilaji.tmdbclient.fragment.adapter.PreviewItemAdapter;
import com.bzilaji.tmdbclient.fragment.adapter.PreviewItemHolder;
import com.bzilaji.tmdbclient.model.GenreAdapter;
import com.bzilaji.tmdbclient.model.Genres;
import com.bzilaji.tmdbclient.model.PreviewableItem;
import com.bzilaji.tmdbclient.model.PreviewableMediaItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class MediaFragment extends PreviewFragment {


    private SimpleDateFormat format = new SimpleDateFormat("yyyy");
    private GenreAdapter genreAdapter;

    @Override
    protected PreviewItemAdapter createAdapter() {
        return new PreviewItemAdapter() {
            @Override
            public PreviewItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MediaItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.media_item, parent, false));
            }
        };
    }


    @Override
    protected void startDownload() {
        downloadGenre();
    }

    private void downloadGenre() {
        Call<Genres> call = createGenreCall();
        call.enqueue(new Callback<Genres>() {
            @Override
            public void onResponse(Call<Genres> call, Response<Genres> response) {
                if (response.isSuccessful()) {
                    genreAdapter = new GenreAdapter(response.body().getGenres());
                    downloadItems();
                }
            }

            @Override
            public void onFailure(Call<Genres> call, Throwable t) {
                switchState(STATE_ERROR);
            }
        });
    }

    protected abstract Call<Genres> createGenreCall();


    private class MediaItemHolder extends PreviewItemHolder {

        private TextView rating;
        private TextView genre;
        private TextView year;
        private View moreButton;
        private PreviewableMediaItem model;

        public MediaItemHolder(View itemView) {
            super(itemView);
            rating = (TextView) itemView.findViewById(R.id.rating);
            genre = (TextView) itemView.findViewById(R.id.genre);
            year = (TextView) itemView.findViewById(R.id.year);
            moreButton = itemView.findViewById(R.id.more_button);
            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemSelected(model);
                }
            });
        }

        @Override
        public void setModel(PreviewableItem model) {
            super.setModel(model);
            this.model = (PreviewableMediaItem) model;
            rating.setText(String.format("%.1f", this.model.getRating()));
            genre.setText(genreAdapter.toHumanReadable(this.model.getGenreIds()));
            try {
                year.setText(format.format(format.parse(this.model.getReleaseDate())));
            } catch (ParseException e) {
            }

        }
    }

    protected abstract void itemSelected(PreviewableMediaItem model);

}
