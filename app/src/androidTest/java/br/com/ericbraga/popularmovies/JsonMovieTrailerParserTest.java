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

import br.com.ericbraga.popularmovies.domain.MovieTrailer;
import br.com.ericbraga.popularmovies.parser.JSonMovieParserException;
import br.com.ericbraga.popularmovies.parser.JSonParser;
import br.com.ericbraga.popularmovies.parser.JsonMovieTrailerParser;
import br.com.ericbraga.popularmovies.testutils.Util;

/**
 * Created by ericbraga on 04/07/17.
 */
@RunWith(AndroidJUnit4.class)
public class JsonMovieTrailerParserTest {
    private Context ctx;

    @Before
    public void setUp() {
        ctx = InstrumentationRegistry.getContext();
    }

    @Test
    public void jsonParserShouldReturnZeroForEmptyResult() {
        List<MovieTrailer> emptyTrailerList = null;
        try {
            emptyTrailerList = getMovieTrailersFromParser("empty_trailer_result.json");
        } catch (JSonMovieParserException e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertEquals(0, emptyTrailerList.size());
    }

    @Test
    public void jsonParserShouldGetInfoFromNonEmptyResult() {
        List<MovieTrailer> oneTrailer = null;
        try {
            oneTrailer = getMovieTrailersFromParser("one_trailer_result.json");
        } catch (JSonMovieParserException e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertEquals(1, oneTrailer.size());
    }

    @Test
    public void jsonParserShouldHandleMoreThanOneResult() {
        List<MovieTrailer> trailers = null;
        try {
            trailers = getMovieTrailersFromParser("trailers_result.json");
        } catch (JSonMovieParserException e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertEquals(2, trailers.size());
    }

    @Test
    public void jsonParserShouldThrowExceptionForInvalidFormat() {
        try {
            getMovieTrailersFromParser("invalid_trailers_result.json");
            Assert.fail("Should throw an Exception");
        } catch (JSonMovieParserException e) {
            Assert.assertTrue(true);
        }
    }

    private List<MovieTrailer> getMovieTrailersFromParser(String assetName) throws JSonMovieParserException {
        JSonParser parser = null;

        try {
            parser = new JsonMovieTrailerParser(Util.readResourceAsset(ctx, assetName));
        } catch (IOException e) {
            Assert.fail("Resource not found " + assetName);
        }

        return parser.extract();
    }
}