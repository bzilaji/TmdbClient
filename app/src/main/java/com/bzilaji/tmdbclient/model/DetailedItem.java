package com.bzilaji.tmdbclient.model;


import java.io.Serializable;

//Todo:move to parcelable
public interface DetailedItem extends PreviewMediaItem, Serializable {

    String getWebPage();

    String getImdbId();

}
