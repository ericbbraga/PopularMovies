package br.com.ericbraga.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.ericbraga.popularmovies.network.NetWorkConnectionException;
import br.com.ericbraga.popularmovies.network.NetworkConnection;

/**
 * Created by ericbraga25.
 */

@RunWith(AndroidJUnit4.class)
public class NetworkTest {

    private Context ctx;

    @Before
    public void setUp() {
        ctx = InstrumentationRegistry.getContext();
    }

    @Test
    public void googleUrlShouldReturnSomeResult() {
        Uri googleUri = Uri.parse("http://www.google.com").buildUpon().build();
        NetworkConnection connection = new NetworkConnection(ctx, googleUri);
        try {
            String responseFromUri = connection.getResponseFromUri();
            Assert.assertNotNull(responseFromUri);
            Assert.assertTrue(responseFromUri.length() > 0);

        } catch (NetWorkConnectionException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void invalidUrlShouldThrowAnException() {
        Uri googleUri = Uri.parse("http://www.invalidurlthrowexception.com").buildUpon().build();
        NetworkConnection connection = new NetworkConnection(ctx, googleUri);
        try {
            connection.getResponseFromUri();
            Assert.fail("Invalid Url should throw an exception");

        } catch (NetWorkConnectionException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void urlWithParamShouldReturnSomeResult() {
        Uri googleUri = Uri.parse("http://www.google.com/").buildUpon()
                .appendQueryParameter("q", "android&*").build();

        NetworkConnection connection = new NetworkConnection(ctx, googleUri);
        try {
            String responseFromUri = connection.getResponseFromUri();
            Assert.assertNotNull(responseFromUri);
            Assert.assertTrue(responseFromUri.length() > 0);

        } catch (NetWorkConnectionException e) {
            Assert.fail(e.getMessage());

        }
    }

    @Test
    public void urlWithSubPathShouldReturnSomeResult() {

        Uri googleUri = Uri.parse("https://github.com/").buildUpon()
                .appendPath("ericbbraga")
                .appendPath("PopularMovies")
                .build();

        NetworkConnection connection = new NetworkConnection(ctx, googleUri);
        try {
            String responseFromUri = connection.getResponseFromUri();
            Assert.assertNotNull(responseFromUri);
            Assert.assertTrue(responseFromUri.length() > 0);

        } catch (NetWorkConnectionException e) {
            Assert.fail(e.getMessage());

        }

    }
}
