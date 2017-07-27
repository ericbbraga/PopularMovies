package br.com.ericbraga.popularmovies.domain;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.ericbraga.popularmovies.configuration.Configurations;
import br.com.ericbraga.popularmovies.data.MovieContract;
import br.com.ericbraga.popularmovies.network.NetWorkConnectionException;
import br.com.ericbraga.popularmovies.network.NetworkConnection;
import br.com.ericbraga.popularmovies.network.NetworkUtils;
import br.com.ericbraga.popularmovies.parser.JSonMovieParser;
import br.com.ericbraga.popularmovies.parser.JSonMovieParserException;
import br.com.ericbraga.popularmovies.parser.JSonParser;
import br.com.ericbraga.popularmovies.parser.JsonMovieReviewParser;
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
    private static final String REVIEWS_END_POINT = "movie/" + MOVIE_ID_PARAM + "/reviews";
    private static final String QUERY_API_KEY_PARAM = "api_key";

    private final Context mContext;

    public MovieInfoDomain(Context context) {
        mContext = context;
    }

    public List<MovieInfo> getPopularMovies() throws NetWorkConnectionException, JSonMovieParserException {
        return getMoviesFromMovieDB(POPULAR_MOVIE_END_POINT, MovieType.POPULAR_MOVIE);
    }

    public List<MovieInfo> getTopRatedMovies() throws NetWorkConnectionException, JSonMovieParserException {
        return getMoviesFromMovieDB(TOP_RATED_END_POINT, MovieType.TOP_MOVIE);
    }

    public List<MovieInfo> getFavoriteMovies() {
        List<MovieInfo> movies = new ArrayList<>();

        ContentResolver contentResolver = mContext.getContentResolver();
        String selection = String.format("%s=?", MovieContract.MovieEntry.FAVORITE_COLUMN);
        String[] selectionArgs = new String[]{Integer.toString(1)};
        Cursor cursor = contentResolver.query(
                MovieContract.MovieEntry.CONTENT_URI,
                MovieContract.MovieEntry.COLUMNS,
                selection,
                selectionArgs,
                MovieContract.MovieEntry._ID);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                movies.add(parseCursorToMovie(cursor));
            } while (cursor.moveToNext());
        }

        Collections.sort(movies);
        return movies;
    }

    private List<MovieInfo> getMoviesFromMovieDB(String movieTypeURL, @MovieType int type) throws NetWorkConnectionException, JSonMovieParserException {
        List<MovieInfo> moviesFromDB = listAllMovies(type);

        if (NetworkUtils.isDeviceConnectedToInternet(mContext)) {
            JSonParser<MovieInfo> parser = new JSonMovieParser(type);
            List<MovieInfo> movies = getFromURL(movieTypeURL, parser);

            for (MovieInfo movie : movies) {
                if (!movieExistsIntoLocalDatabase(movie, moviesFromDB)) {
                    insertIntoProvider(movie);
                }
            }
        }

        return listAllMovies(type);
    }

    private void insertIntoProvider(MovieInfo movie) {
        ContentResolver contentResolver = mContext.getContentResolver();
        ContentValues values = createContentValuesFor(movie);
        contentResolver.insert(MovieContract.MovieEntry.CONTENT_URI, values);
    }

    private boolean movieExistsIntoLocalDatabase(MovieInfo movieInfo, List<MovieInfo> movies) {
        return movies.contains(movieInfo);
    }

    private List<MovieInfo> listAllMovies(@MovieType int type) {
        List<MovieInfo> movies = new ArrayList<>();

        ContentResolver contentResolver = mContext.getContentResolver();
        String selection = String.format("%s=?", MovieContract.MovieEntry.TYPE);
        String[] selectionArgs = new String[]{Integer.toString(type)};
        String sortOrder = String.format("%s ASC",
                MovieContract.MovieEntry._ID);

        Cursor cursor = contentResolver.query(
                MovieContract.MovieEntry.CONTENT_URI,
                MovieContract.MovieEntry.COLUMNS,
                selection,
                selectionArgs,
                sortOrder);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                movies.add(parseCursorToMovie(cursor));
            } while (cursor.moveToNext());
        }

        return movies;
    }

    private ContentValues createContentValuesFor(MovieInfo movie) {
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry._ID, movie.getId());
        values.put(MovieContract.MovieEntry.TITLE_COLUMN, movie.getTitle());
        values.put(MovieContract.MovieEntry.POSTER_PATH_COLUMN, movie.getPosterPath());
        values.put(MovieContract.MovieEntry.RATING_COLUMN, movie.getRating());
        values.put(MovieContract.MovieEntry.RELEASE_DATE_COLUMN, movie.getReleaseDate());
        values.put(MovieContract.MovieEntry.SYNOPSIS_COLUMN, movie.getPlotSynopsis());
        values.put(MovieContract.MovieEntry.FAVORITE_COLUMN, movie.isFavorite());
        values.put(MovieContract.MovieEntry.TYPE, movie.getType());
        return values;
    }

    private MovieInfo parseCursorToMovie(Cursor cursor) {
        long id = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry._ID));
        String title = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.TITLE_COLUMN));
        String releaseDate = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.RELEASE_DATE_COLUMN));
        String posterPath = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.POSTER_PATH_COLUMN));
        String synopsis = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.SYNOPSIS_COLUMN));
        double rating = cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieEntry.RATING_COLUMN));
        boolean favorite = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.FAVORITE_COLUMN)) != 0;
        @MovieType int type = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.TYPE));

        return new MovieInfo(id, title, releaseDate, posterPath, synopsis, rating, favorite, type);
    }

    public List<MovieTrailer> getTrailersFrom(MovieInfo movie) throws NetWorkConnectionException, JSonMovieParserException {
        String id = Long.toString(movie.getId());
        String formattedTrailerURL = TRAILER_END_POINT.replace(MOVIE_ID_PARAM, id);

        JSonParser<MovieTrailer> parser = new JsonMovieTrailerParser();
        return getFromURL(formattedTrailerURL, parser);
    }

    public List<MovieReview> getReviewsFrom(MovieInfo movie) throws NetWorkConnectionException, JSonMovieParserException {
        String id = Long.toString(movie.getId());
        String formattedReviewURL = REVIEWS_END_POINT.replace(MOVIE_ID_PARAM, id);

        JSonParser<MovieReview> parser = new JsonMovieReviewParser();
        return getFromURL(formattedReviewURL, parser);
    }

    private <T> List<T> getFromURL(String formattedReviewURL, JSonParser<T> parser) throws NetWorkConnectionException, JSonMovieParserException {
        Uri uri = Uri.parse(MOVIEDB_API_URL).buildUpon()
                .appendEncodedPath(formattedReviewURL)
                .appendQueryParameter(QUERY_API_KEY_PARAM, getPublicApiKey())
                .build();

        NetworkConnection networkConnection = new NetworkConnection(mContext, uri);
        String response = networkConnection.getResponseFromUri();

        return parser.extract(response);
    }

    private String getPublicApiKey() {
        return Configurations.getInstance().getPublicKey();
    }
}
