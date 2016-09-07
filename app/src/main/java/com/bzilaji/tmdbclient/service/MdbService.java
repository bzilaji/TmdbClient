package com.bzilaji.tmdbclient.service;


import android.database.Observable;

import com.bzilaji.tmdbclient.BuildConfig;
import com.bzilaji.tmdbclient.model.Genres;
import com.bzilaji.tmdbclient.model.MovieCompact;
import com.bzilaji.tmdbclient.model.MovieComplete;
import com.bzilaji.tmdbclient.model.Person;
import com.bzilaji.tmdbclient.model.Result;
import com.bzilaji.tmdbclient.model.TvCompact;
import com.bzilaji.tmdbclient.model.TvComplete;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MdbService {

    @GET("discover/movie/?api_key=" + BuildConfig.TMDB_API_KEY + "&sort_by=popularity.desc")
    Call<Result<MovieCompact>> getTopMovies();


    @GET("person/popular?api_key=" + BuildConfig.TMDB_API_KEY + "&sort_by=popularity.desc")
    Call<Result<Person>> getTopPeople();


    @GET("discover/tv/?api_key=" + BuildConfig.TMDB_API_KEY + "&sort_by=popularity.desc")
    Call<Result<TvCompact>> getTopTv();

    @GET("genre/movie/list?api_key=" + BuildConfig.TMDB_API_KEY)
    public Call<Genres> getGenresForMovies();

    @GET("genre/tv/list?api_key=" + BuildConfig.TMDB_API_KEY)
    public Call<Genres> getGenresForTV();

    @GET("movie/{id}?api_key=" + BuildConfig.TMDB_API_KEY)
    public Call<MovieComplete> getDetailedMovie(@Path("id") int id);

    @GET("tv/{id}?api_key=" + BuildConfig.TMDB_API_KEY)
    public Call<TvComplete> getDetailedTv(@Path("id") int id);


}
