package com.bzilaji.tmdbclient.fragment.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bzilaji.tmdbclient.R;
import com.bzilaji.tmdbclient.model.PreviewItem;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreviewItemHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.title)
    protected TextView title;
    @BindView(R.id.details)
    protected TextView description;
    @BindView(R.id.imageView)
    protected ImageView imageView;

    public PreviewItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setModel(PreviewItem model) {
        title.setText(model.getTitle());
        Picasso.with(title.getContext()).load(model.getImagePath())
                .placeholder(R.drawable.default_placeholder).fit().centerCrop().into(imageView);
        description.setText(model.getDescription());
    }
}
