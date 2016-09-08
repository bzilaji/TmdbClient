package com.bzilaji.tmdbclient.fragment.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bzilaji.tmdbclient.R;
import com.bzilaji.tmdbclient.model.PreviewItem;
import com.squareup.picasso.Picasso;

public class PreviewItemHolder extends RecyclerView.ViewHolder {

    protected TextView title;
    protected TextView description;
    protected ImageView imageView;

    public PreviewItemHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        description = (TextView) itemView.findViewById(R.id.details);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }

    public void setModel(PreviewItem model) {
        title.setText(model.getTitle());
        Picasso.with(title.getContext()).load(model.getImagePath())
                .placeholder(R.drawable.default_placeholder).fit().centerCrop().into(imageView);
        description.setText(model.getDescription());
    }
}
