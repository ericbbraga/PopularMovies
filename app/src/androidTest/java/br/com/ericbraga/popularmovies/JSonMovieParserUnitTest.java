package br.com.ericbraga.popularmovies;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import br.com.ericbraga.popularmovies.domain.MovieInfo;
import br.com.ericbraga.popularmovies.parser.JSonMovieParser;
import br.com.ericbraga.popularmovies.parser.JSonMovieParserException;
import br.com.ericbraga.popularmovies.parser.JSonParser;
import br.com.ericbraga.popularmovies.testutils.Util;

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
        JSonParser parser = null;
        try {
            parser = new JSonMovieParser( Util.readResourceAsset(ctx, "empty_result.json") );
        } catch (IOException e) {
            Assert.fail("Resource not found empty_result.json");
        }

        List<MovieInfo> movies = null;
        try {
            movies = parser.extract();
        } catch (JSonMovieParserException e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertEquals(0, movies.size());
    }

    @Test
    public void jsonParserShouldGetInfoFromNonEmptyResult() {
        JSonParser parser = null;
        try {
            parser = new JSonMovieParser( Util.readResourceAsset(ctx, "one_result.json") );
        } catch (IOException e) {
            Assert.fail("Resource not found one_result.json");
        }

        List<MovieInfo> movies = null;
        try {
            movies = parser.extract();
        } catch (JSonMovieParserException e) {
            Assert.fail(e.getMessage());
        }

        Assert.assertEquals(1, movies.size());

        MovieInfo movieInfo = movies.get(0);
        Assert.assertEquals("Sing", movieInfo.getTitle());
        Assert.assertEquals(6.7, movieInfo.getRating());
        Assert.assertEquals("2016", movieInfo.getReleaseDate());
    }

    @Test
    public void jsonParserShouldThrowAnExceptionForInvalidExpectedFormat() {
        JSonParser parser = null;
        try {
            parser = new JSonMovieParser( Util.readResourceAsset(ctx, "invalid.json") );
        } catch (IOException e) {
            Assert.fail("Resource not found invalid.json");
        }

        try {
            parser.extract();
            Assert.fail("Should throw an exception");
        } catch (JSonMovieParserException e) {
            Assert.assertTrue(true);
        }
    }
}
