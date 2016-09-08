package com.bzilaji.tmdbclient;


import android.support.annotation.NonNull;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.filters.SmallTest;

import com.bzilaji.tmdbclient.model.DetailedItem;
import com.bzilaji.tmdbclient.model.GenreAdapter;
import com.bzilaji.tmdbclient.model.Genres;
import com.bzilaji.tmdbclient.model.MovieCompact;
import com.bzilaji.tmdbclient.model.MovieComplete;
import com.bzilaji.tmdbclient.model.Person;
import com.bzilaji.tmdbclient.model.PreviewItem;
import com.bzilaji.tmdbclient.model.Result;
import com.bzilaji.tmdbclient.model.TvCompact;
import com.bzilaji.tmdbclient.model.TvComplete;
import com.bzilaji.tmdbclient.service.MdbCallFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

import static junit.framework.Assert.*;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class MdbCallFactoryTest {
    private static final int PAGE_SIZE = 20;
    private static final int MOVIE_ACTION_ID = 28;
    private static final int MOVIE_ADVENTURE_ID = 12;
    private static final String MOVIE_ACTION_STRING = "Action";
    private static final String MOVIE_ADVENTURE_STRING = "Adventure";

    private static final int TV_ACTION_ADVENTURE_ID = 10759;
    private static final int TV_ANIMATION_ID = 16;
    private static final String TV_ACTION_ADVENTURE_STRING = "Action & Adventure";
    private static final String TV_ANIMATION_STRING = "Animation";

    private static final String SEPARATOR = ", ";

    private MdbCallFactory sut;

    @Before
    public void init() {
        sut = new MdbCallFactory();
    }

    @Test
    public void testTopMoviesCallback() throws IOException {
        Response<Result<MovieCompact>> response = sut.createCallTopMovies().execute();
        List<? extends PreviewItem> results = assertResult(response.body());
        assertEquals(true, results.get(0) instanceof MovieCompact);
    }

    @NonNull
    private List<? extends PreviewItem> assertResult(Result<? extends PreviewItem> result) {
        assertNotNull(result);
        List<? extends PreviewItem> results = result.getResults();
        assertTrue(results.size() == PAGE_SIZE);
        return results;
    }

    @Test
    public void testTopTvCallback() throws IOException {
        Response<Result<TvCompact>> response = sut.createCallTopTv().execute();
        List<? extends PreviewItem> results = assertResult(response.body());
        assertEquals(true, results.get(0) instanceof TvCompact);
    }

    @Test
    public void testTopPeopleCallback() throws IOException {
        Response<Result<Person>> response = sut.createCallTopPeople().execute();
        List<? extends PreviewItem> results = assertResult(response.body());
        PreviewItem previewItem = results.get(0);
        assertEquals(true, previewItem instanceof Person);
        assertFalse(previewItem.getDescription().isEmpty());
    }

    @Test
    public void testGetGenreMoviesCallback() throws IOException {
        Response<Genres> response = sut.createCallMoviesGenres().execute();
        List<Genres.Genre> genres = response.body().getGenres();
        assertNotNull(genres);
        GenreAdapter adapter = new GenreAdapter(genres);
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(MOVIE_ACTION_ID);
        ids.add(MOVIE_ADVENTURE_ID);
        assertEquals(MOVIE_ACTION_STRING + SEPARATOR + MOVIE_ADVENTURE_STRING, adapter.toHumanReadable(ids));
    }

    @Test
    public void testGetGenreTVCallbackWhenUsingWrongIds() throws IOException {
        Response<Genres> response = sut.createCallTvGenres().execute();
        List<Genres.Genre> genres = response.body().getGenres();
        assertNotNull(genres);
        GenreAdapter adapter = new GenreAdapter(genres);
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(MOVIE_ACTION_ID);
        ids.add(MOVIE_ADVENTURE_ID);
        assertEquals("", adapter.toHumanReadable(ids));
    }

    @Test
    public void testGetGenreTVCallback() throws IOException {
        Response<Genres> response = sut.createCallTvGenres().execute();
        List<Genres.Genre> genres = response.body().getGenres();
        assertNotNull(genres);
        GenreAdapter adapter = new GenreAdapter(genres);
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(TV_ACTION_ADVENTURE_ID);
        ids.add(TV_ANIMATION_ID);
        assertEquals(TV_ACTION_ADVENTURE_STRING + SEPARATOR + TV_ANIMATION_STRING, adapter.toHumanReadable(ids));
    }

    @Test
    public void testGetCompleteMovieCallback() throws IOException {
        Response<MovieComplete> response = sut.createCallForMovieInfo(297761).execute();
        assertCompleteItem(response.body(), "Suicide Squad", "tt1386697");
    }

    @Test
    public void testGetCompleteTvCallback() throws IOException {
        Response<TvComplete> response = sut.createCallForTvInfo(62560).execute();
        assertCompleteItem(response.body(), "Mr. Robot", null);
    }

    private void assertCompleteItem(DetailedItem item, String requiredTitle, String requiredImdbId) {
        assertEquals(requiredTitle, item.getTitle());
        assertEquals(requiredImdbId, item.getImdbId());
    }


}
