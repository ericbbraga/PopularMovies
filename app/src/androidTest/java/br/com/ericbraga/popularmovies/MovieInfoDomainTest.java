package br.com.ericbraga.popularmovies;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.ericbraga.popularmovies.domain.MovieInfoDomain;
import br.com.ericbraga.popularmovies.network.NetWorkConnectionException;
import br.com.ericbraga.popularmovies.parser.JSonMovieParserException;

/**
 * Created by ericbraga25.
 */

@RunWith(AndroidJUnit4.class)
public class MovieInfoDomainTest {

    private Context ctx;

    @Before
    public void setUp() {
        ctx = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void movieInfoShouldReturnAtLeastOnePopularMovie() throws JSonMovieParserException, NetWorkConnectionException {
        MovieInfoDomain movieInfoDomain = new MovieInfoDomain(ctx);
        Assert.assertTrue(movieInfoDomain.getPopularMovies().size() > 1);
    }

    @Test
    public void movieInfoShouldReturnAtLeastOneTopRatedMovie() throws JSonMovieParserException, NetWorkConnectionException {
        MovieInfoDomain movieInfoDomain = new MovieInfoDomain(ctx);
        Assert.assertTrue(movieInfoDomain.getTopRatedMovies().size() > 1);
    }

}
