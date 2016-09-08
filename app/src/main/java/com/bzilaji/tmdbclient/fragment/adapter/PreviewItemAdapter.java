package com.bzilaji.tmdbclient.fragment.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Filter;
import android.widget.Filterable;

import com.bzilaji.tmdbclient.model.PreviewItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class PreviewItemAdapter extends RecyclerView.Adapter<PreviewItemHolder> implements Filterable {

    private CharSequence constraint;
    private List<PreviewItem> list;
    private List<PreviewItem> filteredlist;

    public PreviewItemAdapter() {
        list = Collections.emptyList();
        filteredlist = Collections.emptyList();
    }


    @Override
    public void onBindViewHolder(PreviewItemHolder holder, int position) {
        holder.setModel(filteredlist.get(position));
    }


    public void setItems(List<PreviewItem> items) {
        list = items != null ? new ArrayList<>(items) : Collections.<PreviewItem>emptyList();
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
                List<PreviewItem> temp = new ArrayList(list);
                List<PreviewItem> result = new ArrayList<>();
                for (PreviewItem item : temp) {
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
                filteredlist = (List<PreviewItem>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
