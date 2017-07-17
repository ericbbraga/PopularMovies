package br.com.ericbraga.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ericbraga25.
 */

public final class MovieContract {

    private MovieContract() {}

    public static final String AUTHORITY = "br.com.ericbraga.movieprovider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_MOVIES = "movies";

    public static class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIES)
                .build();

        public static final String TABLE_NAME = "movie";
        public static final String TITLE_COLUMN = "title";
        public static final String RELEASE_DATE_COLUMN = "release_date";
        public static final String POSTER_PATH_COLUMN = "poster_path";
        public static final String SYNOPSIS_COLUMN = "synopsis";
        public static final String RATING_COLUMN = "rating";
        public static final String FAVORITE_COLUMN = "favorite";
        public static final String TYPE = "type";

        public static final String[] COLUMNS = new String[] {
                _ID,
                TITLE_COLUMN,
                RELEASE_DATE_COLUMN,
                POSTER_PATH_COLUMN,
                SYNOPSIS_COLUMN,
                RATING_COLUMN,
                FAVORITE_COLUMN,
                TYPE
        };
    }
}
