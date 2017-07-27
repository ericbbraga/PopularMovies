package br.com.ericbraga.popularmovies.configuration;

import br.com.ericbraga.popularmovies.BuildConfig;

/**
 * Created by ericbraga25.
 */

public class Configurations {

    private static Configurations mInstance = new Configurations();

    private Configurations() {
    }

    public static Configurations getInstance() {
        return mInstance;
    }

    public String getPublicKey() throws ConfigurationException {
        return BuildConfig.THE_MOVIE_DB_API_TOKEN;
    }

}
