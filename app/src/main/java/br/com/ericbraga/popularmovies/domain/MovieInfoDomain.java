package br.com.ericbraga.popularmovies.domain;

import android.content.Context;
import android.net.Uri;

import java.util.List;

import br.com.ericbraga.popularmovies.configuration.Configurations;
import br.com.ericbraga.popularmovies.network.NetWorkConnectionException;
import br.com.ericbraga.popularmovies.network.NetworkConnection;
import br.com.ericbraga.popularmovies.parser.JSonMovieParser;
import br.com.ericbraga.popularmovies.parser.JSonMovieParserException;

/**
 * Created by ericbraga25.
 */

public class MovieInfoDomain {
    private static final String MOVIEDB_API_URL = "http://api.themoviedb.org/3/";

    private String POPULAR_MOVIE_END_POINT = "movie/popular";

    private String TOP_RATED_END_POINT = "movie/top_rated";

    private String QUERY_API_KEY_PARAM = "api_key";

    private final Context mContext;

    public MovieInfoDomain(Context context) {
        mContext = context;
    }

    public List<MovieInfo> getPopularMovies() throws NetWorkConnectionException, JSonMovieParserException {
        return getMoviesFromMovieDB(POPULAR_MOVIE_END_POINT);
    }

    public List<MovieInfo> getTopRatedMovies() throws NetWorkConnectionException, JSonMovieParserException {
        return getMoviesFromMovieDB(TOP_RATED_END_POINT);
    }

    private List<MovieInfo> getMoviesFromMovieDB(String movieTypePath) throws NetWorkConnectionException, JSonMovieParserException {
        Uri uri = Uri.parse(MOVIEDB_API_URL).buildUpon()
                .appendEncodedPath(movieTypePath)
                .appendQueryParameter(QUERY_API_KEY_PARAM, getPublicApiKey())
                .build();

        NetworkConnection networkConnection = new NetworkConnection(mContext, uri);
        String response = networkConnection.getResponseFromUri();

        JSonMovieParser parser = new JSonMovieParser(response);
        return parser.extractMovies();
    }

    private String getPublicApiKey() {
        return new Configurations(mContext).getPublicKey();
    }
}
