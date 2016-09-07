package com.bzilaji.tmdbclient.view;

public interface ToolBarSearchViewListener {

    void onSearchCleared(CharSequence old);

    void onSearchBarHomePressed();

    void onSearchTextChanged(CharSequence s);

}
