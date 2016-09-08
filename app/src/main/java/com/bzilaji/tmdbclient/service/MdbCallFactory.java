package com.bzilaji.tmdbclient.service;


import com.bzilaji.tmdbclient.BuildConfig;
import com.bzilaji.tmdbclient.model.Genres;
import com.bzilaji.tmdbclient.model.MovieCompact;
import com.bzilaji.tmdbclient.model.MovieComplete;
import com.bzilaji.tmdbclient.model.Person;
import com.bzilaji.tmdbclient.model.Result;
import com.bzilaji.tmdbclient.model.TvCompact;
import com.bzilaji.tmdbclient.model.TvComplete;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MdbCallFactory {

    private final MdbService service;

    public MdbCallFactory() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.TMDB_BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(MdbService.class);
    }

    public Call<Result<MovieCompact>> createCallTopMovies() {
        return getService().getTopMovies();
    }

    public Call<Result<TvCompact>> createCallTopTv() {
        return getService().getTopTv();
    }

    public Call<Result<Person>> createCallTopPeople() {
        return getService().getTopPeople();
    }

    public Call<Genres> createCallMoviesGenres() {
        return getService().getGenresForMovies();
    }

    public Call<Genres> createCallTvGenres() {
        return getService().getGenresForTV();
    }

    public Call<MovieComplete> createCallForMovieInfo(int id) {
        return getService().getDetailedMovie(id);
    }

    public Call<TvComplete> createCallForTvInfo(int id) {
        return getService().getDetailedTv(id);
    }


    private MdbService getService() {
        return service;
    }

}
