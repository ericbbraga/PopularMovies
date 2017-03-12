package br.com.ericbraga.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import br.com.ericbraga.popularmovies.domain.MovieInfo;
import br.com.ericbraga.popularmovies.parser.JSonMovieParserException;
import br.com.ericbraga.popularmovies.parser.JSonMovieParser;

/**
 * Created by ericbraga25.
 */
@RunWith(AndroidJUnit4.class)
public class JSonMovieParserUnitTest {

    private Context ctx;

    @Before
    public void setUp() {
        ctx = InstrumentationRegistry.getContext();
    }

    @Test
    public void jsonParserShouldReturnZeroForEmptyResult()  {
        JSonMovieParser parser = null;
        try {
            parser = new JSonMovieParser( readResourceAsset(ctx, "empty_result.json") );
        } catch (IOException e) {
            Assert.fail("Resource not found empty_result.json");
        }

        List<MovieInfo> movies = null;
        try {
            movies = parser.extractMovies();
        } catch (JSonMovieParserException e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertEquals(0, movies.size());
    }

    @Test
    public void jsonParserShouldGetInfoFromNonEmptyResult() {
        JSonMovieParser parser = null;
        try {
            parser = new JSonMovieParser( readResourceAsset(ctx, "one_result.json") );
        } catch (IOException e) {
            Assert.fail("Resource not found one_result.json");
        }

        List<MovieInfo> movies = null;
        try {
            movies = parser.extractMovies();
        } catch (JSonMovieParserException e) {
            Assert.fail(e.getMessage());
        }

        Assert.assertEquals(1, movies.size());

        MovieInfo movieInfo = movies.get(0);
        Assert.assertEquals("Sing", movieInfo.getTitle());
        Assert.assertEquals(6.7, movieInfo.getRating());
        Assert.assertEquals("2016-12-02", movieInfo.getReleaseDate());
    }

    @Test
    public void jsonParserShouldThrowAnExceptionForInvalidExpectedFormat() {
        JSonMovieParser parser = null;
        try {
            parser = new JSonMovieParser( readResourceAsset(ctx, "invalid.json") );
        } catch (IOException e) {
            Assert.fail("Resource not found invalid.json");
        }

        try {
            parser.extractMovies();
            Assert.fail("Should throw an exception");
        } catch (JSonMovieParserException e) {
            Assert.assertTrue(true);
        }
    }

    @NonNull
    private String readResourceAsset(Context ctx, String assetName) throws IOException {
        InputStream is = ctx.getResources().getAssets().open(assetName);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;

        while ( (line = bufferedReader.readLine()) != null ) {
            sb.append(line);
        }
        return sb.toString();
    }

}
