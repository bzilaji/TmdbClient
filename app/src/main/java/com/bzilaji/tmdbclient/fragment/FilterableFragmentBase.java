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

    protected abstract class PreviewItemAdapter extends RecyclerView.Adapter<PreviewItemHolder> implements Filterable {

        private CharSequence constraint;
        private List<PreviewableItem> list;
        private List<PreviewableItem> filteredlist;

        public PreviewItemAdapter() {
            list = Collections.emptyList();
            filteredlist = Collections.emptyList();
        }


        @Override
        public void onBindViewHolder(PreviewItemHolder holder, int position) {
            holder.setModel(filteredlist.get(position));
        }


        public void setItems(List<PreviewableItem> items) {
            list = items != null ? new ArrayList<>(items) : Collections.<PreviewableItem>emptyList();
            filteredlist = list;
            getFilter().filter(constraint);
        }

        @Override
        public int getItemCount() {
            return filteredlist.size();
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();
                    List<PreviewableItem> temp = new ArrayList(list);
                    List<PreviewableItem> result = new ArrayList<>();
                    for (PreviewableItem item : temp) {
                        if (TextUtils.isEmpty(constraint) || accept(constraint.toString(), item.getTitle())) {
                            result.add(item);
                        }
                    }
                    results.count = result.size();
                    results.values = result;
                    return results;
                }

                private boolean accept(String searchTerm, String item) {
                    return item != null && item.toLowerCase().contains(searchTerm.toString().toLowerCase());
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    PreviewItemAdapter.this.constraint = constraint;
                    filteredlist = (List<PreviewableItem>) results.values;
                    notifyDataSetChanged();
                }
            };
        }
    }

    protected class PreviewItemHolder extends RecyclerView.ViewHolder {

        protected TextView title;
        protected TextView description;
        protected ImageView imageView;

        public PreviewItemHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.details);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }

        public void setModel(PreviewableItem model) {
            title.setText(model.getTitle());
            Picasso.with(getView().getContext()).load(model.getImagePath())
                    .placeholder(R.drawable.default_placeholder).fit().centerCrop().into(imageView);
            description.setText(model.getDescription());
        }
    }
}
