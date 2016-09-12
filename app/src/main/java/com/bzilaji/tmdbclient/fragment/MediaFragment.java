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
import com.bzilaji.tmdbclient.model.PreviewItem;
import com.bzilaji.tmdbclient.model.PreviewMediaItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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


    class MediaItemHolder extends PreviewItemHolder {

        @BindView(R.id.rating)
        TextView rating;
        @BindView(R.id.genre)
        TextView genre;
        @BindView(R.id.year)
        TextView year;
        @BindView(R.id.more_button)
        View moreButton;

        private PreviewMediaItem model;

        public MediaItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemSelected(model);
                }
            });
        }

        @Override
        public void setModel(PreviewItem model) {
            super.setModel(model);
            this.model = (PreviewMediaItem) model;
            rating.setText(String.format("%.1f", this.model.getRating()));
            genre.setText(genreAdapter.toHumanReadable(this.model.getGenreIds()));
            try {
                year.setText(format.format(format.parse(this.model.getReleaseDate())));
            } catch (ParseException e) {
            }

        }
    }

    protected abstract void itemSelected(PreviewMediaItem model);

}
