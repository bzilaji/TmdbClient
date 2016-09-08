package com.bzilaji.tmdbclient;


import com.bzilaji.tmdbclient.model.GenreAdapter;
import com.bzilaji.tmdbclient.model.Genres;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class GenreAdapterTest {

    private static final int GENRE_1_ID = 1;
    private static final int GENRE_2_ID = 2;
    private static final int GENRE_3_ID = 3;
    private static final int GENRE_4_ID = 4;
    private static final String GENRE_1_NAME = "GENRE_1";
    private static final String GENRE_2_NAME = "GENRE_2";
    private static final String GENRE_3_NAME = "GENRE_3";
    private static final String GENRE_4_NAME = "GENRE_4";

    GenreAdapter sut;

    @Before
    public void setUp() {
        List<Genres.Genre> genres = createTestList();
        sut = new GenreAdapter(genres);
    }

    private List<Genres.Genre> createTestList() {
        List<Genres.Genre> genres = new ArrayList<>(4);
        genres.add(stubGenre(GENRE_1_ID, GENRE_1_NAME));
        genres.add(stubGenre(GENRE_2_ID, GENRE_2_NAME));
        genres.add(stubGenre(GENRE_3_ID, GENRE_3_NAME));
        genres.add(stubGenre(GENRE_4_ID, GENRE_4_NAME));
        return genres;
    }

    private Genres.Genre stubGenre(int id, String name) {
        Genres.Genre genre = Mockito.mock(Genres.Genre.class);
        when(genre.getId()).thenReturn(id);
        when(genre.getName()).thenReturn(name);
        return genre;
    }

    @Test
    public void testToHumanReadableShouldReturnACommaSeparatedString() {
        String result = sut.toHumanReadable(toList(GENRE_1_ID, GENRE_2_ID, GENRE_3_ID));
        assertEquals(GENRE_1_NAME + ", " + GENRE_2_NAME + ", " + GENRE_3_NAME, result);
    }

    @Test
    public void testToHumanReadableWhenSecondValueInvalidShouldReturnOneItem() {
        String result = sut.toHumanReadable(toList(GENRE_1_ID, 45));
        assertEquals(GENRE_1_NAME, result);
    }

    @Test
    public void testToHumanReadableWheIdsInvalidShouldReturnEmptyString() {
        String result = sut.toHumanReadable(toList(345, 45));
        assertEquals("", result);
    }

    @Test
    public void testToHumanReadableWhenListIsNullShouldReturnEmptyString() {
        String result = new GenreAdapter(null).toHumanReadable(toList(GENRE_1_ID, GENRE_2_ID));
        assertEquals("", result);
    }

    private static List<Integer> toList(Integer... ids) {
        return Arrays.asList(ids);
    }

}
