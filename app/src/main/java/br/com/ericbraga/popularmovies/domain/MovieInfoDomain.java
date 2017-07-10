package br.com.ericbraga.popularmovies.domain;

import android.content.Context;
import android.net.Uri;

import java.util.List;

import br.com.ericbraga.popularmovies.configuration.Configurations;
import br.com.ericbraga.popularmovies.network.NetWorkConnectionException;
import br.com.ericbraga.popularmovies.network.NetworkConnection;
import br.com.ericbraga.popularmovies.parser.JSonMovieParser;
import br.com.ericbraga.popularmovies.parser.JSonMovieParserException;
import br.com.ericbraga.popularmovies.parser.JSonParser;
import br.com.ericbraga.popularmovies.parser.JsonMovieTrailerParser;

/**
 * Created by ericbraga25.
 */

public class MovieInfoDomain {
    private static final String MOVIEDB_API_URL = "http://api.themoviedb.org/3/";

    private static final String POPULAR_MOVIE_END_POINT = "movie/popular";

    private static final String TOP_RATED_END_POINT = "movie/top_rated";

    private static final String MOVIE_ID_PARAM = "{id}";

    private static final String TRAILER_END_POINT = "movie/" + MOVIE_ID_PARAM + "/videos";

    private static final String REVIEWS_END_POINT = "movie/" + MOVIE_ID_PARAM + "reviews";

    private static final String QUERY_API_KEY_PARAM = "api_key";

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

        JSonParser parser = new JSonMovieParser(response);
        List<MovieInfo> movies = parser.extract();

        for (MovieInfo movie : movies) {
            List<MovieTrailer> trailers = getTrailersFrom(movie);
            movie.addTrailer(trailers);
        }

        return movies;
    }

    private List<MovieTrailer> getTrailersFrom(MovieInfo movie) throws NetWorkConnectionException, JSonMovieParserException {

        String id = Integer.toString(movie.getId());
        String formatedTrailerURL = TRAILER_END_POINT.replace(MOVIE_ID_PARAM, id);

        Uri uri = Uri.parse(MOVIEDB_API_URL).buildUpon()
                .appendEncodedPath(formatedTrailerURL)
                .appendQueryParameter(QUERY_API_KEY_PARAM, getPublicApiKey())
                .build();

        NetworkConnection networkConnection = new NetworkConnection(mContext, uri);
        String response = networkConnection.getResponseFromUri();

        JSonParser parser = new JsonMovieTrailerParser(response);
        return parser.extract();
    }

    private String getPublicApiKey() {
        return new Configurations(mContext).getPublicKey();
    }
}
