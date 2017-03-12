package br.com.ericbraga.popularmovies.configuration;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import br.com.ericbraga.popularmovies.R;

/**
 * Created by ericbraga25.
 */

public class Configurations {

    public static final String MOVIE_PARCEABLE_EXTRA = "MOVIE_EXTRA";

    private final Context mContext;

    public Configurations(Context context) {
        mContext = context;
    }

    public String getPublicKey() throws ConfigurationException {
        InputStream is = mContext.getResources().openRawResource(R.raw.moviedb_public_key);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new ConfigurationException(e.getMessage());
        }
    }


}
